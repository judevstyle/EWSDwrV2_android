package com.ssoft.ews4thai.views.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import com.ssoft.ews4thai.data.repository.DashBordRepository
import com.ssoft.ews4thai.data.repository.MapViewRepository
import com.ssoft.ews4thai.views.main.DashbordUi
import com.taitos.nup.common.MutableLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class MainViewModel(val dashBordRepository: DashBordRepository) : ViewModel() {

class MapsViewModel(val mapViewRepository: MapViewRepository) : ViewModel() {


    val dataResp = MutableLiveData<DashbordUi>()
    val dataMapsResp = MutableLiveData<MapsDataUi>()

    val dataAllWarningResp = MutableLiveEvent<MapsDataUi>()


    init {
//        getDashccbord()
    }

    fun getDashbord() {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err","${throwable.message}")
            dataResp.value = DashbordUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

            dataResp.value = DashbordUi.onLoading

            val resp = mapViewRepository.dashbord()

            dataResp.value = DashbordUi.onSuccess(resp)
        }

    }


    fun getWarning(type:TypeEventMap) {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err","${throwable.message}")
            dataMapsResp.value = MapsDataUi.onError(throwable)

//            _loginData.setEventValue(LoginUiCall.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {


            dataMapsResp.value = MapsDataUi.onLoading


            val map:HashMap<String,String> = HashMap<String,String>()

            when(type){
                TypeEventMap.T0->map.put("show","1")
                TypeEventMap.T1->map.put("status","1")
                TypeEventMap.T2->map.put("status","2")
                TypeEventMap.T3->map.put("status","3")
                TypeEventMap.T9->map.put("status","9")
                TypeEventMap.T->map.put("show","2")

            }

            val resp = mapViewRepository.warning(map)

            withContext(Dispatchers.Main){
                dataMapsResp.value = MapsDataUi.onSuccess(resp)

            }

            LogUtil.showLogError("userrrr", resp.toString())

        }


    }

    fun getWarningData(type:TypeEventMap) {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err","${throwable.message}")
         //   dataAllWarningResp.value = MapsDataUi.onError(throwable)
            dataAllWarningResp.setEventValue(MapsDataUi.onError(throwable))

//            _loginData.setEventValue(LoginUiCall.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {


            dataAllWarningResp.setEventValue(MapsDataUi.onLoading)


            val map:HashMap<String,String> = HashMap<String,String>()

            when(type){
                TypeEventMap.T0->map.put("show","1")
                TypeEventMap.T1->map.put("status","1")
                TypeEventMap.T2->map.put("status","2")
                TypeEventMap.T3->map.put("status","3")
                TypeEventMap.T9->map.put("status","9")
                TypeEventMap.T->map.put("show","2")

            }

            val resp = mapViewRepository.warning(map)

            withContext(Dispatchers.Main){
                dataAllWarningResp.setEventValue(MapsDataUi.onSuccess(resp))

            }

            LogUtil.showLogError("userrrr", resp.toString())

        }


    }


}


enum class TypeEventMap{

    T9,T1,T2,T3,T0,T

}


sealed class MapsDataUi{
    data class onSuccess(val data:WarningStationMapResponse):MapsDataUi()
    data class onError(val data:Throwable):MapsDataUi()
    object onLoading:MapsDataUi()

}