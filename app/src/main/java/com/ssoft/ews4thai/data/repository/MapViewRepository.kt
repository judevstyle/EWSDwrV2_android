package com.ssoft.ews4thai.data.repository

import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import retrofit2.http.QueryMap

interface MapViewRepository  {

    suspend  fun dashbord(): DashbordWarningResponse

    suspend fun warning(
       body:Map<String,String>
    ): WarningStationMapResponse

}

class MapViewImpt(val serviceAPI: ServiceAPI):MapViewRepository{
    override suspend fun dashbord(): DashbordWarningResponse {
        return serviceAPI.dashbord()
    }

    override suspend fun warning(body: Map<String, String>): WarningStationMapResponse {
        return serviceAPI.warning_map_data(body)
    }


}