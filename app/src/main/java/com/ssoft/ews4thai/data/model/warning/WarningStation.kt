package com.ssoft.ews4thai.data.model.warning

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.parceler.Parcel
@Parcelize
data class WarningStation(
    val amphoe: String,
    val dept: String,
    val latitude: String,
    val longitude: String,
    val main_basin: String,
    val name: String,
    val pm25: String,
    val province: String,
    val rain: String,
    val rain07h: String,
    val rain120h: String,
    val rain12h: String,
    val rain144h: String,
    val rain168h: String,
    val rain24h: String,
    val rain48h: String,
    val rain72h: String,
    val rain96h: String,
    val show_status: Int,
    val soil1: String,
    val soil2: String,
    val status: String,
    val stn: String,
    val stn_cover: Int,
    val stn_cv: Int,
    val stn_date: String,
    val stn_desc: String,
    val sub_basin: String,
    val tambon: String,
    val target_point1: String,
    val temp: String,
    val warn_rf: String,
    val warn_rf_v: Double,
    val warn_type: Int,
    val warn_wl: String,
    val warn_wl_v: Double,
    val warning_type: String,
    val wl: String,
    val wl07h: String,
    val rain_value:Double
):Parcelable

