package com.ssoft.ews4thai.data.repository

import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ReportRepository  {

    suspend fun getReport(
    ): List<ReportModel>

}

class ReportImpt(val serviceAPI: ServiceAPI):ReportRepository{


    override suspend fun getReport(): List<ReportModel> {
        return serviceAPI.getReport()
    }


}