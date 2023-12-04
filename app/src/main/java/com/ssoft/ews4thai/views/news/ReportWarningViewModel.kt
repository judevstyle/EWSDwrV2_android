package com.ssoft.ews4thai.views.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.repository.MapViewRepository
import com.ssoft.ews4thai.data.repository.ReportRepository
import com.ssoft.ews4thai.views.main.DashbordUi
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.map.TypeEventMap
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportWarningViewModel(val reportRepository: ReportRepository) : ViewModel() {


    val dataResp = MutableLiveData<ReportDataUi>()


    fun getReport() {

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err sd", "${throwable.message}")
            dataResp.value = ReportDataUi.onError(throwable)

        }

        viewModelScope.launch(coroutineExceptionHanlder) {


            dataResp.value = ReportDataUi.onLoading

            val resp = reportRepository.getReport()

            withContext(Dispatchers.Main) {
                LogUtil.showLogError("dep","ok")
                dataResp.value = ReportDataUi.onSuccess(resp)
            }

        }


    }




}

sealed class ReportDataUi{
    data class onSuccess(val data:List<ReportModel>):ReportDataUi()
    data class onError(val data:Throwable):ReportDataUi()
    object onLoading:ReportDataUi()

}