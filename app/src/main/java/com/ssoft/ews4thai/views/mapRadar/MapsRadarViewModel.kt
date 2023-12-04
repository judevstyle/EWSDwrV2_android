package com.ssoft.ews4thai.views.mapRadar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import com.ssoft.ews4thai.data.repository.DashBordRepository
import com.ssoft.ews4thai.data.repository.MapRadarViewRepository
import com.ssoft.ews4thai.data.repository.MapViewRepository
import com.ssoft.ews4thai.views.main.DashbordUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class MainViewModel(val dashBordRepository: DashBordRepository) : ViewModel() {

class MapsRadarViewModel(val mapRadarViewRepository: MapRadarViewRepository) : ViewModel() {


    val dataMapsResp = MutableLiveData<MapsRadarDataUi>()

    fun getRadar(  radius: Int,
                   type: String,
                   lat: Double,
                   lng: Double) {
        LogUtil.showLogError("userrrr", "getDashbord ${lat} -- ${lng}")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err","${throwable.message}")
            dataMapsResp.value = MapsRadarDataUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

            dataMapsResp.value = MapsRadarDataUi.onLoading

            val resp = mapRadarViewRepository.warning_radar_service(radius, type, lat, lng)

            dataMapsResp.value = MapsRadarDataUi.onSuccess(resp)
        }

    }


}


sealed class MapsRadarDataUi{
    data class onSuccess(val data:List<WarningStation>):MapsRadarDataUi()
    data class onError(val data:Throwable):MapsRadarDataUi()
    object onLoading:MapsRadarDataUi()

}