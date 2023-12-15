package com.ssoft.ews4thai.views.announcement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.extreme.ews4thai.model.data.TambonData
import com.iseki.isekikky.network.ConectionService
import com.ssoft.common.BaseActivity
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.CallbackData
import com.ssoft.ews4thai.databinding.ActivityAnnouncementBinding
import com.taitos.testpjk.helper.ImageCropChoosBottomSheetAction
import com.taitos.testpjk.helper.ImageRatio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementActivity : BaseActivity() {

    lateinit var binding:ActivityAnnouncementBinding


    var stations: List<TambonData>? = null
    var province = ""
    var amphone = ""
    var tambon = ""
    var image = ""

    var stationData: TambonData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ConectionService.getClient.province_data().enqueue(ProvinceApi())
        initView()

    }


    fun initView() {

        binding.submitAction.setOnClickListener {

            if (province.equals("")){
                showToast("เลือกจังหวัด")
            }
            else if (amphone.equals("")){
                showToast("เลือกอำเภอ")
            }
            else if (tambon.equals("")){
                showToast("เลือกตำบล")
            }
            else if (stationData == null){
                showToast("เลือกสถานี")
            }
            else if (binding.descET.text.toString().length < 1){
                showToast("ระบุรายละเอียด")
            }
            else if (image.equals("")){
                showToast("เลือกรูปภาพ")
            }
            else{

                ConectionService.getClient.saveNews(stationData?.stn?:"",stationData?.name?:"",tambon,amphone,province,stationData?.latitude?:0.0
                    ,stationData?.longitude?:0.0,binding.descET.text.toString(),image).enqueue(AddNewspi())


            }
        }
        binding.imageNews.setOnClickListener {

            val dialog = ImageCropChoosBottomSheetAction(ImageRatio.SQUARE)

            dialog.onComplete = {img,bitmap ->

                binding.imageNews.setImageBitmap(bitmap)
                ConectionService.getClient.uploadPicture(img!!).enqueue(UploadImage())

            }
            dialog.show(supportFragmentManager,"")

        }

        binding.autoCompleteTextView.setOnItemClickListener(object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0!!.getItemAtPosition(p2) as String
                Log.e("size", "${selectedItem}")
                province = selectedItem
                ConectionService.getClient.amphoe_data(selectedItem).enqueue(AmphoneApi())


//                viewModel.slotSelect(p2)
//                showProgressDialog()
            }

        });
        binding.autoCompleteTextView1.setOnItemClickListener(object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0!!.getItemAtPosition(p2) as String
                amphone = selectedItem

                ConectionService.getClient.tambon_data(selectedItem).enqueue(TambonApi())

//                viewModel.slotSelect(p2)
//                showProgressDialog()
            }

        });


        binding.autoCompleteTextView2.setOnItemClickListener(object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0!!.getItemAtPosition(p2) as String


                tambon = selectedItem
                ConectionService.getClient.station_data(tambon).enqueue(StationApi())

//                tambon = selectedItem
//                ConectionService.getClient.tambon_data(selectedItem).enqueue(TambonApi())
//                binding.stationTV.text = "${tambons?.get(p2)?.stn} ${tambons?.get(p2)?.name}"
//                Log.e("tm", "${tambons?.get(p2)?.stn} , ${tambons?.get(p2)?.name} , ${tambons?.get(p2)?.latitude}, ${tambons?.get(p2)?.longitude}")


//                viewModel.slotSelect(p2)
//                showProgressDialog()
            }

        });

        binding.autoCompleteStation.setOnItemClickListener(object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0!!.getItemAtPosition(p2) as String
//                tambonData = tambons?.get(p2)
//                tambon = selectedItem
//                ConectionService.getClient.tambon_data(selectedItem).enqueue(TambonApi())
//                binding.stationTV.text = "${tambons?.get(p2)?.stn} ${tambons?.get(p2)?.name}"
//                Log.e("tm", "${tambons?.get(p2)?.stn} , ${tambons?.get(p2)?.name} , ${tambons?.get(p2)?.latitude}, ${tambons?.get(p2)?.longitude}")
//
                stationData = stations?.get(p2)
//                viewModel.slotSelect(p2)
//                showProgressDialog()
            }

        });



    }


    inner class ProvinceApi() : Callback<List<String>> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<List<String>>, t: Throwable) {
            showToast("err")
            Log.e("size", "${t.message}")

            hideDialog()
        }

        override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
            hideDialog()

            if (response.isSuccessful) {

                response.body()?.let {
//                    autoCompleteTextView2.setText("เลือกตำบล")
                    binding.autoCompleteTextView1.setText("เลือกอำเภอ")
                 //   binding.autoCompleteTextView2.setText("เลือกตำบล")

                    amphone = ""
                    tambon = ""
                   // binding.stationTV.text = ""

                    val adapter = ArrayAdapter(
                        this@AnnouncementActivity, R.layout.component_list_item, it
                    )
                    binding.autoCompleteTextView.setAdapter(adapter);
                    Log.e("size", "${it.size}")
                    Log.e("doe", "issii ${it.size}")

//                    taskNew = CallWarning(it).execute()

                }

            }
        }

    }


    inner class AmphoneApi() : Callback<List<String>> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<List<String>>, t: Throwable) {
            showToast("err")
            Log.e("size", "${t.message}")

            hideDialog()
        }

        override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
            hideDialog()

            if (response.isSuccessful) {

                response.body()?.let {
                    //                    autoCompleteTextView2.setText("เลือกตำบล")
                    binding.autoCompleteTextView1.setText("เลือกอำเภอ")
                    amphone = ""
                    tambon = ""
                    stationData = null
                    binding.autoCompleteTextView2.setText("เลือกตำบล")
                    binding.autoCompleteStation.setText("เลือกสถานี")

                    // binding.stationTV.text = ""
                    val adapter = ArrayAdapter(
                        this@AnnouncementActivity, R.layout.component_list_item, it
                    )
                    binding.autoCompleteTextView1.setAdapter(adapter);
                    Log.e("AmphoneApi size", "${it.size}")
                    Log.e("doe", "issii ${it.size}")

//                    taskNew = CallWarning(it).execute()

                }

            }
        }

    }


    inner class TambonApi() : Callback<List<String>> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<List<String>>, t: Throwable) {
            showToast("err")
            Log.e("size", "${t.message}")

            hideDialog()
        }

        override fun onResponse(
            call: Call<List<String>>,
            response: Response<List<String>>
        ) {
            hideDialog()

            if (response.isSuccessful) {
                tambon = ""
                binding.autoCompleteStation.setText("เลือกสถานี")
                binding.autoCompleteTextView2.setText("เลือกตำบล")
                stationData = null
                response.body()?.let {
//                    tambons = it

                    val arr = ArrayList<String>()

//                    for (data in it) {
//                        arr.add(data.tambon)
//
//                    }

                    val adapter = ArrayAdapter(
                        this@AnnouncementActivity, R.layout.component_list_item, it
                    )
                    binding.autoCompleteTextView2.setAdapter(adapter);
                    Log.e("AmphoneApi size", "${it.size}")
                    Log.e("doe", "issii ${it.size}")

//                    taskNew = CallWarning(it).execute()

                }

            }
        }

    }

    inner class StationApi() : Callback<List<TambonData>> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<List<TambonData>>, t: Throwable) {
            showToast("err")
            Log.e("size", "${t.message}")

            hideDialog()
        }

        override fun onResponse(
            call: Call<List<TambonData>>,
            response: Response<List<TambonData>>
        ) {
            hideDialog()

            if (response.isSuccessful) {

                response.body()?.let {
                    stations = it

                    val arr = ArrayList<String>()

                    for (data in it) {
                        arr.add(data.name)

                    }

                    val adapter = ArrayAdapter(
                        this@AnnouncementActivity, R.layout.component_list_item, arr
                    )
                    binding.autoCompleteStation.setAdapter(adapter);
                    Log.e("AmphoneApi size", "${it.size}")
                    Log.e("doe", "issii ${it.size}")

//                    taskNew = CallWarning(it).execute()

                }

            }
        }

    }


    inner class UploadImage() : Callback<CallbackData> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<CallbackData>, t: Throwable) {
            showToast("err")
            Log.e("size errr", "${t.message}")

            hideDialog()
        }

        override fun onResponse(
            call: Call<CallbackData>,
            response: Response<CallbackData>
        ) {
            hideDialog()
            Log.e("AmphoneApi size", "${response.isSuccessful}")

            if (response.isSuccessful) {


                response.body()?.let {
                    Log.e("AmphoneApi msg","${it.msg} ${it.success}")
                    if (it.success){

                        image = "${it.msg}"
                        Log.e("AmphoneApi msd", "${it.msg}")
                    }

                }

            }
        }

    }


    inner class AddNewspi() : Callback<CallbackData> {



        init {
            showProgressDialog()

        }

        override fun onFailure(call: Call<CallbackData>, t: Throwable) {
            showToast("err")
            Log.e("size errr", "${t.message}")

            hideDialog()
        }

        override fun onResponse(
            call: Call<CallbackData>,
            response: Response<CallbackData>
        ) {
            hideDialog()
            Log.e("AmphoneApi size", "${response.isSuccessful}")

            if (response.isSuccessful) {


                response.body()?.let {
                    Log.e("AmphoneApi msg","${it.msg} ${it.success}")
                    if (it.success) {
                        showToast("บันทึกการแจ้งข่าวเรียบร้อย")
                        finish()
                    }

                }

            }
        }

    }



}