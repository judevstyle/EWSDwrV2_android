package com.ssoft.ews4thai.data


data class CallbackRespone<T>(
    val `data`: T,
    val statusCode: Int,
    val message:String
)