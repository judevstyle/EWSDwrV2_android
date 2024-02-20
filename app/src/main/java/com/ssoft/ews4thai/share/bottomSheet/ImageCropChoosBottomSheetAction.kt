package com.taitos.testpjk.helper

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssoft.ews4thai.databinding.BottomSheetLayoutPhotoBinding
import com.ssoft.ews4thai.utils.FileUtil
import com.yalantis.ucrop.UCrop
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@RuntimePermissions
class ImageCropChoosBottomSheetAction(val type: ImageRatio) : BottomSheetDialogFragment() {


    private var img_base64: String? = null
    private var providerFile: Uri? = null
    val PICK_IMAGE_REQUEST = 1002
    private var actualImage: File? = null
    private var compressedImage: File? = null

    //    private lateinit var adapters: ImageB64Adapter
    val REQUEST_CODE = 1001

    var onComplete: ((MultipartBody.Part?, Bitmap?) -> Unit)? = null

    lateinit var binding : BottomSheetLayoutPhotoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLayoutPhotoBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bottomsheet()


    }


    fun bottomsheet() {


        binding.menuBottomSheetCamera.setOnClickListener {
            openCameraWithPermissionCheck()

        }


        binding.menuBottomSheetImg.setOnClickListener {
//            openGalleryWithPermissionCheck()


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                openGalleryWithPermissionCheck()
            } else {
//                openGallery33WithPermissionCheck()
                launchPickerSingleMode()
            }




        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCamera() {

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val filePhoto = getPhotoFile("photo.jpg")

        providerFile = FileProvider.getUriForFile(
            requireContext(),
            "com.extreme.ews4thai.provider",
            filePhoto
        )
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        if (takePhotoIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_CODE)
        } else {
            Toast.makeText(
                requireContext(),
                "Camera could not open",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun openGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }

    @NeedsPermission(Manifest.permission.READ_MEDIA_IMAGES)
    fun openGallery33() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)

    }


    private fun launchPickerSingleMode() {
//        type ?: return

        try {
            startForSingleModeResult.launch(
                PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build()
            )
        } catch (ex: ActivityNotFoundException) {
//            showToast(ex.localizedMessage ?: "error")
        }
    }

    private val startForSingleModeResult =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { currentUri ->
            if (currentUri != null) {
//                showToast("Selected URI: $currentUri")
//                handlePickerResponse(currentUri)
                startCrop(currentUri!!)

                requireActivity().contentResolver.openInputStream(currentUri).use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    // imageView.setImageBitmap(bitmap)
                }
            } else {
//                showToast("No media selected")
            }
        }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->


        val requestCode = result.data?.extras?.getInt("REQUEST_CODE")

        if (result.resultCode == Activity.RESULT_OK) {


            try {
                if (requestCode == REQUEST_CODE) {


                    try {
//                        val intent = data?.data

                        startCrop(providerFile!!)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                if (requestCode == PICK_IMAGE_REQUEST) {

                    try {

                        val selectedUri = result!!.data!!.data
//                        val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), compressedImage)
//                        val body: MultipartBody.Part =
//                            MultipartBody.Part.createFormData("upload", compressedImage?.getName()?:"", reqFile)


                        startCrop(selectedUri!!)

                    } catch (e: IOException) {
//                        showToast("Failed to read picture data!")
                        e.printStackTrace()
                    }


                } else if (requestCode == UCrop.REQUEST_CROP) {

                    handleCropResult(result!!.data!!)

                }
            }catch (ex:Exception){}


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)

            if (resultCode === Activity.RESULT_OK) {

                if (requestCode == REQUEST_CODE) {


                    try {
//                        val intent = data?.data

                        startCrop(providerFile!!)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                if (requestCode == PICK_IMAGE_REQUEST) {

                    try {

                        val selectedUri = data!!.data

                        startCrop(selectedUri!!)

                    } catch (e: IOException) {
//                        showToast("Failed to read picture data!")
                        e.printStackTrace()
                    }


                } else if (requestCode == UCrop.REQUEST_CROP) {

                    handleCropResult(data!!)

                }
            }
        } catch (ex: Exception) {

        }


    }

    private fun handleCropResult(result: Intent) {


//


        val resultUri = UCrop.getOutput(result)
//        onComplete?.invoke("",result.)
//        dismiss()

//        avatarIM.setImageResource(0)
//        avatarIM.setImageURI(resultUri!!)

        try {
            actualImage = FileUtil.from(requireContext()!!, resultUri!!)?.also {

            }
        } catch (e: IOException) {
//                    showError("Failed to read picture data!")
            e.printStackTrace()
        }
//        LogUtil.showLogError("dwep", "[[[2")

        actualImage?.let { imageFile ->
            lifecycleScope.launch {
                // Default compression

                compressedImage = Compressor.compress(requireContext(), imageFile)
                val filename = ("Trim"+ SimpleDateFormat(
                    "yyyyMMdd_HHmmss", Locale
                        .getDefault()
                ).format(Date())) + ".jpg"
                val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/jpg"), compressedImage)
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("fileToUpload", filename, reqFile)


                onComplete?.invoke(
                    body,
                    BitmapFactory.decodeFile(compressedImage!!.absolutePath)
                )
                dismiss()
//                LogUtil.showLogError("dwep","[[[3")

            }
        }


    }

    private val SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage"
    private val REQUEST_SELECT_PICTURE_FOR_FRAGMENT = 0x02
    private val requestMode: Int = 1001

    private fun startCrop(uri: Uri) {
//        showToast("dlw")
        var destinationFileName: String =
            SAMPLE_CROPPED_IMAGE_NAME

        val file = File(activity?.cacheDir, destinationFileName)
        file.deleteRecursively()


        var uCrop = UCrop.of(
            uri,
            Uri.fromFile(File(activity?.cacheDir, destinationFileName))
        )

        uCrop = basisConfig(uCrop)


//        uCrop = advancedConfig(uCrop)
        if (requestMode == REQUEST_SELECT_PICTURE_FOR_FRAGMENT) {       //if build variant = fragment
//            setupFragment(uCrop)
        } else {                                                        // else start uCrop Activity
            uCrop.start(requireContext(), this)
        }
    }

    fun basisConfig(@NonNull uCrop: UCrop): UCrop {
        var data = uCrop

        when (type){
            ImageRatio.SQUARE ->{
                data = uCrop.withAspectRatio(512.toFloat(), 512.toFloat())

            }
            ImageRatio.TIMELINE ->{
                data =  uCrop
                    .withAspectRatio(3.toFloat(), 2.toFloat())
                    .withMaxResultSize(512,512)
            }

        }





        return data

    }


    fun convertImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val encodedImage: String = android.util.Base64.encodeToString(
            byteArrayOutputStream.toByteArray(),
            android.util.Base64.DEFAULT
        )
        //  Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        return encodedImage


    }


}

enum class ImageRatio {
    SQUARE ,TIMELINE
}