package com.ssoft.ews4thai.data.repository

import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse

interface DashBordRepository  {

    suspend  fun dashbord(): DashbordWarningResponse



}

class DashBordImpt(val serviceAPI: ServiceAPI):DashBordRepository{
    override suspend fun dashbord(): DashbordWarningResponse {
        return serviceAPI.dashbord()
    }


}