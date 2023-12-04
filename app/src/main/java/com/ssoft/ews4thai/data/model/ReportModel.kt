package com.ssoft.ews4thai.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ReportModel(

    val report_body: String,
    val report_date: String,
    val report_signature: String,
    val report_title: String,
    val stn_id: String,
    val warning_type: String
):Parcelable