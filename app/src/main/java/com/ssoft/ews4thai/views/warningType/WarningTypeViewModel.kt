package com.ssoft.ews4thai.views.warningType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.repository.MapViewRepository
import com.ssoft.ews4thai.views.main.DashbordUi
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.map.TypeEventMap
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WarningTypeViewModel(val mapViewRepository: MapViewRepository) : ViewModel() {


    val dataResp = MutableLiveData<MapsDataUi>()


    fun getWarning(type: TypeEventMap) {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
            dataResp.value = MapsDataUi.onError(throwable)

//            _loginData.setEventValue(LoginUiCall.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {


            dataResp.value = MapsDataUi.onLoading


            val map: HashMap<String, String> = HashMap<String, String>()

            when (type) {
                TypeEventMap.T0 -> map.put("show", "1")
                TypeEventMap.T1 -> map.put("status", "1")
                TypeEventMap.T2 -> map.put("status", "2")
                TypeEventMap.T3 -> map.put("status", "3")
                TypeEventMap.T9 -> map.put("status", "9")
                TypeEventMap.T -> map.put("show", "2")

            }

            val resp = mapViewRepository.warning(map)

            withContext(Dispatchers.Main) {
                dataResp.value = MapsDataUi.onSuccess(resp)

                for (item in resp.data!!){
                    LogUtil.showLogError("ssdw","${item.rain_value}--${item.warn_rf_v}")
                }
            }

            LogUtil.showLogError("userrrr", resp.toString())

        }


    }




}