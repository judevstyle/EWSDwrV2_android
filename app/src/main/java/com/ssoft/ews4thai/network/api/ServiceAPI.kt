package com.ba.phsapps.network.api

import com.extreme.ews4thai.model.data.TambonData
import com.ssoft.ews4thai.data.model.CallbackData
import com.ssoft.ews4thai.data.model.DepartData
import com.ssoft.ews4thai.data.model.RegionData
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ServiceAPI {






    @GET("api/province_data.php")
    fun province_data(
    ): Call<List<String>>


    @GET("api/amphoe_data.php")
    fun amphoe_data(
        @Query("province") province:String
    ): Call<List<String>>


    @GET("api/tambon_data.php")
    fun tambon_data(
        @Query("amphoe") amphoe:String
    ): Call<List<String>>


    @GET("api/station_data.php")
    fun station_data(
        @Query("tambon") tambon:String
    ): Call<List<TambonData>>


    @GET("api/searchStation.php")
    fun searchStation(
        @Query("text") text:String
    ): Call<List<WarningStation>>


    @FormUrlEncoded
    @POST("api/news.php")
    fun saveNews(
        @Field("stn") stn:String,
        @Field("stn_name") stn_name:String,
        @Field("tambon") tambon:String,
        @Field("amphone") amphoe:String,
        @Field("province") province:String,
        @Field("latitude") latitude:Double,
        @Field("longitude") longitude:Double,
        @Field("text_news") text_news:String,
        @Field("pic_news") pic_news:String
    ): Call<CallbackData>


    @Multipart
    @POST("upload_file_service.php")
    fun uploadPicture(
        @Part part: MultipartBody.Part
    ): Call<CallbackData>


    @GET("dashbord.php")
   suspend fun dashbord(): DashbordWarningResponse

    @GET("warning.php")
    suspend fun warning(
        @QueryMap body:Map<String,String>
    ): WarningStationMapResponse

    @GET("warning_map_data.php")
    suspend fun warning_map_data(
        @QueryMap body:Map<String,String>
    ): WarningStationMapResponse

    @GET("warning_radar_service.php")
    suspend fun warning_radar_service(
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): List<WarningStation>

    @GET("warning_reportv1.php")
    suspend fun getReport(
    ): List<ReportModel>

//    @GET("depart_data.php")
//    suspend fun departData(
//    ): List<String>

    @GET("warning_station_type.php")
    suspend fun warningStationType(
        @QueryMap body:Map<String,String>
    ): WarningStationMapResponse

    @GET("depart_data.php")
    suspend fun departData(
    ): List<DepartData>

    @GET("region_data.php")
    suspend fun regionData(
    ): List<RegionData>


    @GET("autocomplete_station.php")
    suspend fun autocomplete_station(
        @QueryMap body:Map<String,String>

    ): List<String>



}