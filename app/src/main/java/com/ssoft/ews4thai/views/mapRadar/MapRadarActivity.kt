package com.ssoft.ews4thai.views.mapRadar

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ssoft.common.BaseActivity
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ActivityMapRadarBinding
import com.ssoft.ews4thai.share.bottomSheet.WarningBottomSheetDialog
import com.ssoft.ews4thai.views.main.DashbordUi
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.map.MapsViewModel
import com.ssoft.ews4thai.views.map.TypeEventMap
import org.koin.android.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MapRadarActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {
    private  var type: String = "warn"
    private lateinit var binding: ActivityMapRadarBinding

    private val viewModel: MapsRadarViewModel by viewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mMap // Might be null if Google Play services APK is not available.
            : GoogleMap? = null
    var location: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityMapRadarBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

//        LogUtil.showLogError("map","${mapFragment}")
//
        mapFragment.getMapAsync(this)

        setContentView(binding.root)
        initView()
        initObserv()
    }

    fun initView(){
        binding.seekBar.progress = 12
        binding.seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        binding.actionType5.setOnClickListener { actionType5() }
        binding.actionType2.setOnClickListener { actionType2() }

        binding.frameLayout.setOnClickListener { finish() }

    }

    fun actionType2() {
        DrawableCompat.setTint(binding.actionType5.getBackground(), ContextCompat.getColor(this, R.color.trans));
        DrawableCompat.setTint(binding.actionType2.getBackground(), ContextCompat.getColor(this, R.color.green));
        type = "all"

        location?.let {
            viewModel.getRadar(binding.seekBar.progress,"all",location?.latitude?:0.0,location?.longitude?:0.0)

        }?: kotlin.run {
            showToast("ไม่สามารถโหลดข้อมูลได้")
        }



    }
    fun actionType5() {


//        actionType5.backgroundTintList
        DrawableCompat.setTint(binding.actionType5.getBackground(), ContextCompat.getColor(this, R.color.green));
        DrawableCompat.setTint(binding.actionType2.getBackground(), ContextCompat.getColor(this, R.color.trans));
        type = "warn"

        location?.let {
            viewModel.getRadar(binding.seekBar.progress,"warn",location?.latitude?:0.0,location?.longitude?:0.0)

        }?: kotlin.run {
            showToast("ไม่สามารถโหลดข้อมูลได้")
        }

    }

    fun initObserv(){



        viewModel.dataMapsResp.observe(this, Observer { state: MapsRadarDataUi ->
            when (state) {
                is MapsRadarDataUi.onSuccess -> {



                    mMap?.clear()
                    drawMarkerWithCircle( LatLng(location?.latitude ?: 0.0, location?.longitude?: 0.0))
                    LogUtil.showLogError("dlpe","s}")

                    val t1 = resources.getDrawable(R.drawable.ic_pin_type1,null)
                    val t2 = resources.getDrawable(R.drawable.ic_pin_type2,null)
                    val t3 = resources.getDrawable(R.drawable.ic_pin_type3,null)
                    val t9 = resources.getDrawable(R.drawable.ic_pin_type4,null)
                    val t999 = resources.getDrawable(R.drawable.pin_gray,null)



                    for (item in state.data ?: ArrayList()){
                        val pinMark = when(item.show_status){
                            1->t3
                            2->t2
                            3->t1
                            -999->t999
                            else->t9

                        }

                        LogUtil.showLogError("dlpe","${item.name}")
                        // generateSmallIcon(ds)
                        val  mk = MarkerOptions()
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_car))
                            .position(LatLng( item.latitude.toDouble(), item.longitude.toDouble()))
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_test))
                            .icon(
                                BitmapDescriptorFactory.fromBitmap(generateSmallIcon(pinMark))
                            )

                        mMap!!.addMarker(mk)?.tag = item


                    }


                    hideDialog()

                }
                is MapsRadarDataUi.onError -> {
                    hideDialog()

                }
                is MapsRadarDataUi.onLoading -> {
                    showProgressDialog()
                }
            }
        })


    }



    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun requestLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.

                Log.e("ddd", "${location!!.latitude}")
                if (location != null) {

                    this.location = LatLng(location.latitude, location.longitude)
                    viewModel.getRadar(binding.seekBar.progress,"warn",location?.latitude?:0.0,location?.longitude?:0.0)
                    drawMarkerWithCircle( LatLng(location.latitude, location.longitude))

                    mMap!!.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location!!.latitude,
                                location!!.longitude
                            ), 12.toFloat()
                        )
                    )
                }

            }

    }

    var seekBarChangeListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            // updated continuously as the user slides the thumb
            binding.textView.setText("สถานีภายใน $progress km.")
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // called when the user first touches the SeekBar
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            Log.e("kfoe","ddd ${seekBar.progress}")
            // called after the user finishes moving the SeekBar

            location?.let {
                raduan = seekBar.progress*1000
//                ConectionService.getClient.warning_radar_service(seekBar.progress,type,location?.latitude?:0.0,location?.longitude?:0.0).enqueue(CallWarningApi())
                viewModel.getRadar(binding.seekBar.progress,type,location?.latitude?:0.0,location?.longitude?:0.0)

            }?: kotlin.run {
                showToast("ไม่สามารถโหลดข้อมูลได้")
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

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0!!
        p0?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_ni));



        requestLocationWithPermissionCheck()

        p0!!.setOnMarkerClickListener(this);

//        viewModel.getWarning(TypeEventMap.T)


    }

    override fun onMarkerClick(p0: Marker): Boolean {

        val data: WarningStation = p0?.tag as WarningStation
        val bottomSheetPrixaDialog = WarningBottomSheetDialog(data)
        bottomSheetPrixaDialog.show(supportFragmentManager, "PrixaBottomSheet")


        return true
    }

    fun generateSmallIcon(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return (drawable as BitmapDrawable).bitmap
        }

        val bitmap = Bitmap.createBitmap(
            52,
            71,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap;
    }

    private var mCircle: Circle? = null
    private var mMarker: Marker? = null
    var raduan = 12000

    private fun drawMarkerWithCircle(position: LatLng) {
        LogUtil.showLogError("drawMarkerWithCircle","drawMarkerWithCircle")
        val radiusInMeters = raduan
        val strokeColor = -0x10000 //red outline
        val shadeColor = 0x44ff0000 //opaque red fill
        val circleOptions =
            CircleOptions().center(position).radius(radiusInMeters.toDouble()).fillColor(R.color.blue)
                .strokeColor(R.color.blue).strokeWidth(2f)
        mCircle = mMap?.addCircle(circleOptions)
//        val markerOptions = MarkerOptions().position(position)
//        mMarker = mMap?.addMarker(markerOptions)
    }



}