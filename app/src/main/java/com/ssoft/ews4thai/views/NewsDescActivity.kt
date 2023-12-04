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
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.ActivityNewsDescBinding
import com.ssoft.ews4thai.share.adapter.TabAdapter
import com.ssoft.ews4thai.views.news.desc.WarnReportNewsPageFragment
import org.parceler.Parcels

class NewsDescActivity : BaseActivity() {
    lateinit var data: ArrayList<ReportModel>
    private lateinit var adapters: TabAdapter
    var index: Int = 0



    lateinit var binding:ActivityNewsDescBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityNewsDescBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()


    }


    fun initView(){

        data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra ("data", ReportModel::class.java) ?: ArrayList()

        }else{
            intent.getParcelableArrayListExtra<ReportModel>("data") ?: ArrayList()
        }
//        data = Parcels.unwrap(intent!!.getParcelableExtra("data"))
        index = intent!!.getIntExtra("index",0)

        adapters = TabAdapter(supportFragmentManager)
        adapters.apply {
            for (i in 0..data.size-1){

                addFragment(WarnReportNewsPageFragment.instance(data.get(i)), "สินค้า")

            }
        }

        binding.pager.apply {
            scrollDuration = 200
            binding.pager.adapter = adapters

        }
        binding.pager.setCurrentItem(index)
        //val tmp = data.get(index+1)

        binding.titlesTV.text = "${splitTitle("สถานี",data!!.get(index).report_title)}"
        binding.txt1TV.text = "สถานี ${splitAddr("สถานี",data!!.get(index).report_title)} ต.${splitAddr("ตำบล",data!!.get(index).report_title)} อ.${splitAddr("อำเภอ",data!!.get(index).report_title)} จ.${splitAddr("จังหวัด",data!!.get(index).report_title)}"

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

                binding.titlesTV.text = "${splitTitle("สถานี",data!!.get(position).report_title)}"
                binding.txt1TV.text = "สถานี ${splitAddr("สถานี",data!!.get(position).report_title)} ต.${splitAddr("ตำบล",data!!.get(position).report_title)} อ.${splitAddr("อำเภอ",data!!.get(position).report_title)} จ.${splitAddr("จังหวัด",data!!.get(position).report_title)}"

//                txt1TV.text = "ต.${tmp!!.tambon} อ.${tmp!!.amphoe} จ.${tmp!!.province}"

                if (position == 0 || position == data.size-1){

                    if (position == 0) {
                        binding.iconBack.visibility = View.GONE
                        binding.iconNext.visibility = View.VISIBLE
                    }else{
                        binding.iconBack.visibility = View.VISIBLE
                        binding.iconNext.visibility = View.GONE

                    }

                } else {
                    binding.iconNext.visibility = View.VISIBLE
                    binding.iconBack.visibility = View.VISIBLE

                }

            }

        })

        binding.backAction.setOnClickListener {
            val current_position = binding.pager.currentItem
            val next_position = current_position - 1

            binding.pager.setCurrentItem(next_position );
        }

        binding.nextAction.setOnClickListener {
            val current_position = binding.pager.currentItem
            val next_position = current_position + 1
            binding.pager.setCurrentItem(next_position );

        }
    }



    fun splitTitle(text:String,msg:String):String{

        return msg.split("${text}")[0]

    }

    fun splitAddr(text:String,msg:String):String{

        try {
            return (msg.split("${text}")[1]).split(" ")[1]

        }catch (ex : Exception){
            return ""
        }

    }



}