package com.ssoft.ews4thai.views.setting

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.CompoundButton
import com.google.firebase.messaging.FirebaseMessaging
import com.ssoft.common.util.SharedPreferenceUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {


    lateinit var binding:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swCustom.setChecked(!SharedPreferenceUtil.getNotiState(this))


        binding.swCustom.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

                if (p1) {
                    FirebaseMessaging.getInstance().subscribeToTopic("ews")
                        .addOnCompleteListener { task ->
//                var msg = getString(R.string.msg_subscribed)
                            if (!task.isSuccessful) {
//                    msg = getString(R.string.msg_subscribe_failed)
                            }
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                   SharedPreferenceUtil.updateNoti(this@SettingActivity,false)
                } else {
//                    edit.putBoolean("noti_state", false).commit()
                    SharedPreferenceUtil.updateNoti(this@SettingActivity,true)

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("ews");


                }
            }
        })




    }
}