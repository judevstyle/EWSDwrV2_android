package com.ssoft.common.helper

import com.ssoft.common.util.LogUtil
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateConvertor {


    fun convertDate_yyyy_MM_dd(date:String):String{
        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("dd/MM/yyyy")


        try {
            val getAbbreviate = input.parse(date)    // parse input
            return output.format(getAbbreviate)    // format output
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun covertTimeISOToText(dataDate: String): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = "ago"
        try {
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val format =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            val pasTime = format.parse(dataDate)
            val nowTime = Date()
            val dateDiff = nowTime.time - pasTime.time
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 30) {
                convTime = "just now"
            }

            else if (second < 60) {
                convTime = "$second sec. $suffix"
            } else if (minute < 60) {
                convTime = "$minute min. $suffix"
            } else if (hour < 24) {
                convTime = "$hour hr. $suffix"
            } else if (day >= 7) {
                convTime = if (day > 360) {
                    (day / 360).toString() + " years " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " months " + suffix
                } else {
                    (day / 7).toString() + " week. " + suffix
                }
            } else if (day < 7) {
                convTime = "$day days $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
//            Log.e("ConvTimeE", e.message)
        }
        return convTime
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



    fun convertDateTimeISO1(dateOld:String):String{
        val format =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
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


    fun convertTime(timeOld:String):String{
        val format =
            SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val output = SimpleDateFormat("HH:mm")

        try {
            val date = format.parse(timeOld)
            return output.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            LogUtil.showLogError("time","${e.message}")
        }
        return ""
    }



}