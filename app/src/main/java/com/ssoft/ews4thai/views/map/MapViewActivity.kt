package com.ssoft.ews4thai.views.map

import android.Manifest
import android.R.drawable
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ssoft.common.BaseActivity
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ActivityMapViewBinding
import com.ssoft.ews4thai.share.bottomSheet.BottomSheetMapsDialog
import com.ssoft.ews4thai.share.bottomSheet.WarningBottomSheetDialog
import com.ssoft.ews4thai.views.main.DashbordUi
import org.koin.android.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MapViewActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private val viewModel:MapsViewModel by viewModel()


    private lateinit var binding: ActivityMapViewBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mMap // Might be null if Google Play services APK is not available.
            : GoogleMap? = null
    var location: LatLng? = null

    var isShow = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

//        LogUtil.showLogError("map","${mapFragment}")
//
        mapFragment.getMapAsync(this)


        setupView()
        initObserv()
    }


    fun setupView(){

        viewModel.getDashbord()
        binding.apply {


            actionBack.setOnClickListener {
                finish()
            }

            actionType1.setOnClickListener {
                viewModel.getWarning(TypeEventMap.T3)
            }
            actionType2.setOnClickListener {
                viewModel.getWarning(TypeEventMap.T2)

            }
            actionType3.setOnClickListener {
                viewModel.getWarning(TypeEventMap.T1)

            }
            actionType4.setOnClickListener {
                viewModel.getWarning(TypeEventMap.T9)

            }
            actionType5.setOnClickListener {
                viewModel.getWarning(TypeEventMap.T0)

            }

        }



    }


    fun initObserv(){


        viewModel.dataResp.observe(this, Observer { state: DashbordUi ->
            when (state) {
                is DashbordUi.onSuccess -> {

                    binding.apply {
                        countTV.text = "${state.data.type3.count_bann}"
                        count2TV.text = "${state.data.type2.count_bann}"
                        count3TV.text = "${state.data.type1.count_bann}"
                        count4TV.text = "${state.data.type9.count_bann}"

                    }

//                    hideDialog()

                }
                is DashbordUi.onError -> {
//                    hideDialog()

                }
                is DashbordUi.onLoading -> {
//                    showProgressDialog()
                }
            }
        })

        viewModel.dataMapsResp.observe(this, Observer { state: MapsDataUi ->
            when (state) {
                is MapsDataUi.onSuccess -> {


                    if (state.data.warning_station == 0 && !isShow){
                        showCountWorning()
                        isShow = true
                    }

                    mMap?.clear()
                    LogUtil.showLogError("dlpe","s}")

                    val t1 = resources.getDrawable(R.drawable.ic_pin_type1,null)
                    val t2 = resources.getDrawable(R.drawable.ic_pin_type2,null)
                    val t3 = resources.getDrawable(R.drawable.ic_pin_type3,null)
                    val t9 = resources.getDrawable(R.drawable.ic_pin_type4,null)



                    for (item in state.data.data ?: ArrayList()){
                        val pinMark = when(item.show_status){
                            1->t3
                            2->t2
                            3->t1
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
                is MapsDataUi.onError -> {
                    hideDialog()

                }
                is MapsDataUi.onLoading -> {
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

//                    this.location = LatLng(location.latitude, location.longitude)

                    mMap!!.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location!!.latitude,
                                location!!.longitude
                            ), 6.toFloat()
                        )
                    )
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
//        task = CallWarningTypeAll().execute()
//        task = CallWarning().execute()


        requestLocationWithPermissionCheck()

        p0!!.setOnMarkerClickListener(this);

        viewModel.getWarning(TypeEventMap.T)


    }

    override fun onMarkerClick(p0: Marker): Boolean {

        val data:WarningStation = p0?.tag as WarningStation
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

    fun showCountWorning(){

        AlertDialog.Builder(this)
            .setMessage("ไม่พบการเตือนภัย")
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setNegativeButton("ปิด", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }



}


