package com.ssoft.ews4thai

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.ssoft.common.BaseActivity
import com.ssoft.common.util.LogUtil
import com.ssoft.common.util.SharedPreferenceUtil
import com.ssoft.ews4thai.databinding.ActivityMainBinding
import com.ssoft.ews4thai.views.announcement.AnnouncementActivity
import com.ssoft.ews4thai.views.info.InfoActivity
import com.ssoft.ews4thai.views.main.DashbordUi
import com.ssoft.ews4thai.views.main.MainViewModel
import com.ssoft.ews4thai.views.map.MapViewActivity
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.map.MapsViewModel
import com.ssoft.ews4thai.views.map.TypeEventMap
import com.ssoft.ews4thai.views.mapRadar.MapRadarActivity
import com.ssoft.ews4thai.views.news.ReportActivity
import com.ssoft.ews4thai.views.search.SearchStationActivity
import com.ssoft.ews4thai.views.setting.SettingActivity
import com.ssoft.ews4thai.views.warningType.WarningTypeActivity
import com.taitos.nup.common.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MainActivity : BaseActivity() {


    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModelWarning: MapsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        setContentView(binding.root)
        requestNotificationWithPermissionCheck()
//        this.requestNotification()

        initView()
        viewModel.getDashbord()
        initObserv()
    }

    fun initView() {

        if (!SharedPreferenceUtil!!.getNotiState(this)) {
            FirebaseMessaging.getInstance().subscribeToTopic("ews")
                .addOnCompleteListener { task ->
//                var msg = getString(R.string.msg_subscribed)
                    if (!task.isSuccessful) {
//                    msg = getString(R.string.msg_subscribe_failed)
                    }
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }

//            val edit = spf!!.edit()
//            edit.putInt("state", 1)
//            edit.putBoolean("noti_state", true)
//            edit.commit()

        }
//        FirebaseMessaging.getInstance().subscribeToTopic("ews2")
//            .addOnCompleteListener { task ->
////                var msg = getString(R.string.msg_subscribed)
//                if (!task.isSuccessful) {
////                    msg = getString(R.string.msg_subscribe_failed)
//                }
////                Log.d(TAG, msg)
////                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            }


        binding.menuAction.setOnClickListener {

            if (binding.menuLO.visibility == View.VISIBLE) {
                binding.menuLO.visibility = View.GONE
            } else {
                binding.menuLO.visibility = View.VISIBLE
            }
        }

        binding.mapsAction.setOnClickListener {
            startActivity(Intent(this,MapViewActivity::class.java))
        }
        binding.mapsRadarAction.setOnClickListener {
            startActivity(Intent(this,MapRadarActivity::class.java))
        }
        binding.menu1.setOnClickListener {
            startActivity(Intent(this,WarningTypeActivity::class.java)
                .putExtra("status",3)
            )
        }

        binding.actionReport.setOnClickListener {
            startActivity(Intent(this,ReportActivity::class.java))

        }

        binding.actionNews.setOnClickListener {
            startActivity(Intent(this,AnnouncementActivity::class.java))

        }
        binding.actionSetting.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))

        }
        binding.menu2.setOnClickListener {
            startActivity(Intent(this,WarningTypeActivity::class.java)
                .putExtra("status",2)
            )
        }

        binding.menu3.setOnClickListener {
            startActivity(Intent(this,WarningTypeActivity::class.java)
                .putExtra("status",1)
            )
        }

        binding.menu4.setOnClickListener {
            startActivity(Intent(this,WarningTypeActivity::class.java)
                .putExtra("status",9)
            )
        }
        binding.actionSearch.setOnClickListener {
            startActivity(Intent(this,SearchStationActivity::class.java)
            )
        }
        binding.actionAbout.setOnClickListener {
            startActivity(Intent(this,InfoActivity::class.java)
            )
        }
        binding.allAction.setOnClickListener {
            startActivity(Intent(this,WarningTypeActivity::class.java)
                .putExtra("status",-1)
            )
//            viewModelWarning.getWarningData(TypeEventMap.T)



        }
    }

    fun initObserv() {


        viewModelWarning.dataAllWarningResp.observe(this,EventObserver{state: MapsDataUi ->
            when (state) {
                is MapsDataUi.onSuccess -> {



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
        viewModel.dataResp.observe(this, Observer { state: DashbordUi ->
            when (state) {
                is DashbordUi.onSuccess -> {

                    binding.apply {
                        stationCountTV.text = "${state.data.type3.count_station}"
                        countTV.text = "${state.data.type3.count_bann}"

                        stationCount2TV.text = "${state.data.type2.count_station}"
                        count2TV.text = "${state.data.type2.count_bann}"

                        stationCount3TV.text = "${state.data.type1.count_station}"
                        count3TV.text = "${state.data.type1.count_bann}"

                        stationCount4TV.text = "${state.data.type9.count_station}"
                        count4TV.text = "${state.data.type9.count_bann}"

                    }

                    hideDialog()

                }
                is DashbordUi.onError -> {
                    hideDialog()

                }
                is DashbordUi.onLoading -> {
                    showProgressDialog()
                }
            }
        })

    }


    @NeedsPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun requestNotification() {
        LogUtil.showLogError("NeedsPermission", "on")

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



}