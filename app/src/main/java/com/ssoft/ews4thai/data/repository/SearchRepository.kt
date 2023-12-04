package com.ssoft.ews4thai.data.repository

import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.model.DepartData
import com.ssoft.ews4thai.data.model.RegionData
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface  SearchRepository  {

    suspend fun warningStationType(
         body:Map<String,String>
    ): WarningStationMapResponse

    suspend fun departData(
    ): List<DepartData>

    suspend fun regionData(
    ): List<RegionData>


    suspend fun autocomplete_station(
        body:Map<String,String>
    ): List<String>

}

class SearchImpt(val serviceAPI: ServiceAPI):SearchRepository{
    override suspend fun warningStationType(body: Map<String, String>): WarningStationMapResponse {
        return serviceAPI.warningStationType(body)
    }

    override suspend fun departData(): List<DepartData> {
        return serviceAPI.departData()
    }

    override suspend fun regionData(): List<RegionData> {
        return serviceAPI.regionData()
    }

    override suspend fun autocomplete_station(body: Map<String, String>): List<String> {
        return serviceAPI.autocomplete_station(body)
    }


}