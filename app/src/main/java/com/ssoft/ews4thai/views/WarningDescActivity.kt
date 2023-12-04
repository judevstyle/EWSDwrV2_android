package com.ssoft.ews4thai.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.ssoft.common.BaseActivity
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ActivityWarningDescBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.share.adapter.TabAdapter
import com.ssoft.ews4thai.views.warningDetail.WarningDescFragment
import org.parceler.Parcels

class WarningDescActivity : BaseActivity() , HandleClickListener {
    private lateinit var binding: ActivityWarningDescBinding


    lateinit var data: ArrayList<WarningStation>
    private lateinit var adapters: TabAdapter
    var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityWarningDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

       data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra ("data", WarningStation::class.java) ?: ArrayList()

        }else{
           intent.getParcelableArrayListExtra<WarningStation>("data") ?: ArrayList()
        }
//       val data = intent!!.getParcelableArrayListExtra("data",WarningStation::class.java)
        index = intent!!.getIntExtra("index",0)

        val state = intent!!.getBooleanExtra("isSigle",false)


        if (state){
            binding.boxHead.visibility = View.GONE
            binding.titleTV.visibility = View.VISIBLE
        }

        initView()



    }


    fun initView(){

//        data = Parcels.unwrap(intent!!.getParcelableExtra("data"))
//        index = intent!!.getIntExtra("index",0)

        adapters = TabAdapter(supportFragmentManager)
        adapters.apply {
            for (i in 0..data.size-1){

                addFragment(WarningDescFragment.instance(data.get(i)), "สินค้า")

            }
        }

        binding.pager.apply {
            scrollDuration = 400
            binding.pager.adapter = adapters

        }
        binding.pager.setCurrentItem(index)
        val tmp = data.get(index)

        binding.titlesTV.text = "${tmp!!.name}"
        binding.txt1TV.text = "ต.${tmp!!.tambon} อ.${tmp!!.amphoe} จ.${tmp!!.province}"
        binding.txt2TV.text = "หมู่บ้านครอบคลุมจำนวน ${tmp!!.stn_cover} หมู่บ้าน"
        Log.e("ddd position","${index}")
        if (index == 0 || index == data.size-1){

            if (index == 0) {
                binding.iconBack.visibility = View.GONE
                binding.iconNext.visibility = View.VISIBLE
            }else{
                binding.iconBack.visibility = View.VISIBLE
                binding.iconNext.visibility = View.GONE

            }

        }
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


            }
            override fun onPageSelected(position: Int) {
                Log.e("ddd","ddd ${position}")


                val tmp = data.get(position)

                binding.titlesTV.text = "${tmp!!.name}"
                binding.txt1TV.text = "ต.${tmp!!.tambon} อ.${tmp!!.amphoe} จ.${tmp!!.province}"
                binding.txt2TV.text = "หมู่บ้านครอบคลุมจำนวน ${tmp!!.stn_cover} หมู่บ้าน"

                if (position == 0 || position == data.size-1){

                    if (position == 0) {
                        binding.iconBack.visibility = View.GONE
                        binding. iconNext.visibility = View.VISIBLE
                    }else{
                        binding. iconBack.visibility = View.VISIBLE
                        binding. iconNext.visibility = View.GONE

                    }

                } else {
                    binding.iconNext.visibility = View.VISIBLE
                    binding. iconBack.visibility = View.VISIBLE

                }

            }

        })

        binding.backAction.setOnClickListener {
            val current_position = binding.pager.currentItem
            val next_position = current_position - 1
//            if (next_position <= 0) {
//                iconBack.visibility = View.GONE
//                iconNext.visibility = View.VISIBLE
//
//            }else {
//                iconNext.visibility = View.VISIBLE
//                iconBack.visibility = View.VISIBLE
//
//            }
            binding.pager.setCurrentItem(next_position );
        }

        binding.nextAction.setOnClickListener {
            val current_position = binding.pager.currentItem
            val next_position = current_position + 1
            binding.pager.setCurrentItem(next_position );
//            Log.e("dd","${next_position}")
//            if (next_position >= 5) {
//                iconBack.visibility = View.VISIBLE
//                iconNext.visibility = View.GONE
//            }else {
//                iconNext.visibility = View.VISIBLE
//                iconBack.visibility = View.VISIBLE
//
//            }
        }


    }

    override fun onItemClick(view: View, position: Int, action: Int) {


    }
}