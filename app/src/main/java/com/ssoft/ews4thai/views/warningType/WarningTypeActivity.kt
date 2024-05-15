package com.ssoft.ews4thai.views.warningType

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssoft.common.BaseActivity
import com.ssoft.common.BaseFragment
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ActivityWarningTypeBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.views.WarningDescActivity
import com.ssoft.ews4thai.views.main.DashbordUi
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.map.TypeEventMap
import org.koin.android.viewmodel.ext.android.viewModel
import org.parceler.Parcels

class WarningTypeActivity : BaseActivity() ,HandleClickListener{


    private val viewModel:WarningTypeViewModel by viewModel()

    private lateinit var adapters: WarningTypeAdapter
    private lateinit var binding: ActivityWarningTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityWarningTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val status = intent.getIntExtra("status",1)




        val type = when(status){
            1 -> TypeEventMap.T1
            2 -> TypeEventMap.T2
            3 -> TypeEventMap.T3
            -1 -> TypeEventMap.T
            else -> TypeEventMap.T9
        }
        viewModel.getWarning(type)

        initView()
        initObserv()

    }

    fun initView(){


//        Log.e("dd","${data.size}")
        adapters = WarningTypeAdapter(this!!,this)
//        adapters.items = data
        binding.rcWorning.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = adapters

        }


    }

    fun initObserv(){



        viewModel.dataResp.observe(this, Observer { state: MapsDataUi ->
            when (state) {
                is MapsDataUi.onSuccess -> {

                    adapters.setItem(state.data.data?:ArrayList())

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


    override fun onItemClick(view: View, position: Int, action: Int) {

        LogUtil.showLogError("ddd json","${adapters.items?.size.toString()}")
        LogUtil.showLogError("ddd json-","${adapters.items!!.get(0).toString()}")

//        val arr = ArrayList<WarningStation>()
//        arr.add(adapters.items!!.get(0))

        startActivity(
            Intent(this, WarningDescActivity::class.java)
                .putExtra("data",adapters.items)
                .putExtra("index", position)
        )


    }


}