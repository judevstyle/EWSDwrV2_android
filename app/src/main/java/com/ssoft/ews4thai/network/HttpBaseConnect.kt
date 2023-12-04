package com.ssoft.ews4thai.network


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpBaseConnect() {

//        val BASE_URL = "http://siplatinumfruit.fortiddns.com:1080/api/"
    val BASE_URL = "https://ews.dwr.go.th/ews/api/"
//                         https://encoapi.pttgrp.com/

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

//    val okHttpClient = OkHttpClient.Builder()
//        .readTimeout(60, TimeUnit.SECONDS)
//        .connectTimeout(60, TimeUnit.SECONDS)
//        .build()
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()

    val client = OkHttpClient().newBuilder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
//        .addInterceptor(TokenExpiredInterceptor(this))
        .build()


    private fun createClientAuth(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
//        okHttpClientBuilder.addInterceptor(TokenExpiredInterceptor(this))
        return okHttpClientBuilder.build()

    }


    // :- auth connecting
    inline fun <reified T> build(): T {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(T::class.java)

    }


//    val client = OkHttpClient().newBuilder()
//        .addInterceptor(TokenExpiredInterceptor())
//        .build()

    private val retrofit = Retrofit.Builder()
        .client(createClientAuth())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //
//    fun getApiService(): TokenApi {
//        return retrofit.create(TokenApi::class.java)
//
//    }

    //


}