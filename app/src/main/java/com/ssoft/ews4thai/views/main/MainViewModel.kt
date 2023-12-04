package com.ssoft.ews4thai.views.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.dashbord.DashbordWarningResponse
import com.ssoft.ews4thai.data.repository.DashBordRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

//class MainViewModel(val dashBordRepository: DashBordRepository) : ViewModel() {

class MainViewModel(val dashBordRepository: DashBordRepository) : ViewModel() {


    val dataResp = MutableLiveData<DashbordUi>()


    init {
//        getDashccbord()
    }

    fun getDashbord() {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err","${throwable.message}")
            dataResp.value = DashbordUi.onError(throwable)

//            _loginData.setEventValue(LoginUiCall.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

//            val request = ChatRequest(pid,null,"...",userTo!!.uid ?: "",3)
//            _loginData.setEventValue(LoginUi.onLoading)
            dataResp.value = DashbordUi.onLoading

            val resp = dashBordRepository.dashbord()

            dataResp.value = DashbordUi.onSuccess(resp)

//            textChat.value = ""
//
            LogUtil.showLogError("userrrr", resp.toString())
//            add("https://growestate-assets.s3.ap-southeast-1.amazonaws.com/${data.data.path}")

        }


    }


}

sealed class DashbordUi{
    data class onSuccess(val data:DashbordWarningResponse):DashbordUi()
    data class onError(val data:Throwable):DashbordUi()
    object onLoading:DashbordUi()

}