package com.ssoft.common.helper

import com.ssoft.common.util.LogUtil
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object NumberConvertor {


    fun convertNumbertoComma(number:Any):String{
        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("dd/MM/yyyy")


        if (number == null) return "-"
        val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
        formatter.applyPattern("#,###,###,###")
        val formattedString: String = formatter.format(number)


//        try {
//            val getAbbreviate = input.parse(date)    // parse input
//            return output.format(getAbbreviate)    // format output
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
        return formattedString
    }




    fun convertDateISO(dateOld:String):String{


        val format =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val output = SimpleDateFormat("dd/MM/yyyy")

        try {
            val date = format.parse(dateOld)
            return output.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            LogUtil.showLogError("time","${e.message}")
        }
        return ""

    }

    fun convertTimeISO(dateOld:String):String{
        val format =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val output = SimpleDateFormat("HH:mm")

        try {
            val date = format.parse(dateOld)
            return output.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            LogUtil.showLogError("time","${e.message}")
        }
        return ""
    }

    fun convertDateTimeISO(dateOld:String):String{
        val format =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val output = SimpleDateFormat("dd/MM/yyyy HH:mm")

        try {
            val date = format.parse(dateOld)
            return output.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            LogUtil.showLogError("time","${e.message}")
        }
        return ""
    }






}