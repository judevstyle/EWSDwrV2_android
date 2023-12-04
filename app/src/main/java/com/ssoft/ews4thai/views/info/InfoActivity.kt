package com.ssoft.ews4thai.views.info

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.ActivityInfoBinding
import com.ssoft.ews4thai.views.warningType.ViewHolder

class InfoActivity : AppCompatActivity() {

    lateinit var binding:ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val arrstr = arrayOf("ความเป็นมาของโครงการ","กรมทรัพยากรน้ำ ได้ดำเนินการติดตั้งระบบเตือนภัยล่วงหน้า (Early Warning) สำหรับพื้นที่เสี่ยงอุทกภัย- ดินถล่ม ในพื้นที่ลาดชันและพื้นที่ราบเชิงเขา โดยเริ่มตั้งแต่ปีงบประมาณ ๒๕๔๘ ถึงปี ๒๕๖๓ ได้ติดตั้งระบบเตือนภัยจำนวน ๑,๗๙๖ สถานี ครอบคลุมหมู่บ้านเสี่ยงภัย จำนวน ๕,๔๙๓ หมู่บ้าน เพื่อเป็นกลไกในการติดตามสถานการณ์ เฝ้าระวังและเตือนภัยที่เกิดจากน้ำท่วมฉับพลัน โดยการตรวจวัดข้อมูลปริมาณน้ำฝน และ/หรือระดับน้ำในพื้นที่หมู่บ้านที่อยู่ในข่ายเสี่ยงภัยสูงจากการเกิดน้ำท่วมฉับพลัน ซึ่งนอกจากการมีระบบตรวจวัดข้อมูลในพื้นที่แล้ว ระบบเตือนภัยล่วงหน้าจะต้องมีการพัฒนาโปรแกรมประยุกต์สำหรับการค้นคืนข้อมูลจากฐานข้อมูลตรวจวัดมาใช้วิเคราะห์สถานการณ์เตือนภัย เพื่อการพยากรณ์และเตือนภัยได้ โดยไม่ต้องป้อนค่าลงไปในโปรแกรมวิเคราะห์โดยตรง และพัฒนาระบบเตือนภัยล่วงหน้าแสดงผลผ่านอุปกรณ์เคลื่อนที่ (Mobile Application) สามารถนำข้อมูลการตรวจวัดและการเตือนภัยที่เกี่ยวข้องไปใช้ประโยชน์ ได้อย่างมีประสิทธิภาพ"
            ,"ปัจจุบันเทคโนโลยีสารสนเทศและการสื่อสารที่ก้าวหน้า และทันสมัย รวมทั้งระบบอินเตอร์เน็ตที่มีความเสถียร และอัตราการรับ-ส่งข้อมูลที่รวดเร็วยิ่งขึ้น ทำให้ง่ายต่อการเข้าถึงข้อมูลผ่านระบบอินเตอร์เน็ต จึงได้มีแนวคิดที่จะพัฒนาระบบเตือนภัยล่วงหน้าแสดงผลผ่านอุปกรณ์เคลื่อนที่ (Mobile Application) ที่ใช้ Smartphone ทั้ง ระบบปฏิบัติการ iOS และ Android เพื่อให้ง่ายต่อการเฝ้าระวัง และติดตามระบบเตือนภัยล่วงหน้า สามารถพกพาติดตัวได้สะดวก และรวดเร็วในการเข้าดูข้อมูลที่ต้องการ"
            ,"วัตถุประสงค์","เพื่อออกแบบ และพัฒนา ระบบแสดงผลข้อมูลการแจ้งเตือนภัยจากระบบเตือนภัยล่วงหน้า (Early Warning) สำหรับพื้นที่เสี่ยงอุทกภัย- ดินถล่ม ในพื้นที่ลาดชันและพื้นที่ราบเชิงเขา ผ่านอุปกรณ์เคลื่อนที่ (Mobile Application) ที่ใช้งานบน Smartphone ทั้งระบบปฏิบัติการ iOS และ Android"
            ,"ผู้พัฒนา","กรมทรัพยากรน้ำ"
        )
        var i=0
        for (data in arrstr) {

//            if (i == 0 || i == 3 || i == 5){
//                binding.expandableView.addView(ViewHolder())
//
//            } else {
//                binding.expandableView.addView(InfoView(this, "${data}"))
//            }
//            i++

//            expandableView.addView(InfoView(context, "${parser.getValue(e, "ews0${i+1}")}"))
//            for (info in feed.getInfoList()) {
            //    expandableView.addView(InfoView(context, "titleTxt"))
//            }
        }


    }
}