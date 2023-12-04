package com.ssoft.ews4thai.data.repository

import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MapRadarViewRepository  {

    suspend fun warning_radar_service(
         radius: Int,
         type: String,
        lat: Double,
       lng: Double
    ): List<WarningStation>

}

class MapRadarViewImpt(val serviceAPI: ServiceAPI):MapRadarViewRepository{



    override suspend fun warning_radar_service(
        radius: Int,
        type: String,
        lat: Double,
        lng: Double
    ): List<WarningStation> {
        return serviceAPI.warning_radar_service(radius, type, lat, lng)

    }


}