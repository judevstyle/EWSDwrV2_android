package com.ssoft.ews4thai.views.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.DepartData
import com.ssoft.ews4thai.data.model.RegionData
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponse
import com.ssoft.ews4thai.data.model.warning.WarningStationMapResponseAuto
import com.ssoft.ews4thai.data.repository.SearchRepository
import com.ssoft.ews4thai.views.mapRadar.MapsRadarDataUi
import com.taitos.nup.common.MutableLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.Charset


class SearchViewModel(val searchRepository: SearchRepository) : ViewModel() {

    val dataDepartResp = MutableLiveData<List<String>>()
    val dataRegionResp = MutableLiveData<List<String>>()

    val dataRegion = MutableLiveData<List<RegionData>>()
    val dataDepart = MutableLiveData<List<DepartData>>()

    val dataProvinces = MutableLiveData<List<String>>()


    val dataStationStr = MutableLiveData<List<String>>()
    val dataStations = MutableLiveData<List<WarningStation>>()


    val stateLoading = MutableLiveEvent<SearchDataUi>()
    val dataStationSearch = MutableLiveData<List<String>>()


    val stateSearchLoading = MutableLiveEvent<SearchDataUi>()


    //stand by search station
    val dataStationsTmp = MutableLiveData<List<WarningStation>>()


    val stateLoadingStation = MutableLiveEvent<SearchDataUi>()


    init {
//        getDashccbord()
    }

    val dataMapsResp = MutableLiveData<MapsRadarDataUi>()

    fun getStation(
        txt: String,
    ) {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
            dataMapsResp.value = MapsRadarDataUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

            dataMapsResp.value = MapsRadarDataUi.onLoading

            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("txt", txt)

            val resp = searchRepository.warningStationType(map)


            val nameList: List<String> = (resp.data ?: ArrayList()).map { it.name }
            dataStationStr.value = nameList
            dataStations.value = resp.data ?: ArrayList()
            dataMapsResp.value = MapsRadarDataUi.onSuccess(resp.data ?: ArrayList())
        }

    }

    fun getDept() {
        LogUtil.showLogError("userrrr", "getDashbord")

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
//            dataResp.value = DashbordUi.onError(throwable)
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

//            dataResp.value = DashbordUi.onLoading

            val resp = searchRepository.departData()
            val nameList: List<String> = resp.map { it.name }
            dataDepart.value = resp
            dataDepartResp.value = nameList


        }

    }


    fun getRegion() {
        LogUtil.showLogError("userrrr", "getDashbord")

        //  loadStation()
        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
//            dataResp.value = DashbordUi.onError(throwable)
            stateLoading.setEventValue(SearchDataUi.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

//            dataResp.value = DashbordUi.onLoading
            stateLoading.setEventValue(SearchDataUi.onLoading)

            val resp = searchRepository.regionData()
            dataRegion.value = resp
            val nameList: List<String> = resp.map { it.full_name }
            dataRegionResp.value = nameList


            val respDepart = searchRepository.departData()
            val nameListDe: List<String> = respDepart.map { it.name }
            dataDepart.value = respDepart
            dataDepartResp.value = nameListDe



            stateLoading.setEventValue(SearchDataUi.onSuccess)

        }

    }

    fun searchStation(txt:String,index: Int) {

        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
//            dataResp.value = DashbordUi.onError(throwable)
            stateLoadingStation.setEventValue(SearchDataUi.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {
            LogUtil.showLogError("ttt", "bbbbs")

//            dataResp.value = DashbordUi.onLoading
            stateLoadingStation.setEventValue(SearchDataUi.onLoading)

            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("search", txt)

            val respStation = searchRepository.warningStationType(map)
            dataStationsTmp.value = respStation.data ?: ArrayList()

            LogUtil.showLogError("tttsss", "bbbb ${respStation.data?.size}")

            stateLoadingStation.setEventValue(SearchDataUi.onSuccessIndex(index))

        }

    }


    fun test(txt: String) {


        val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->

            LogUtil.showLogError("err", "${throwable.message}")
            stateSearchLoading.setEventValue(SearchDataUi.onSuccessSearchString(ArrayList()))

//            dataResp.value = DashbordUi.onError(throwable)
//            stateSearchLoading.setEventValue(SearchDataUi.onError(throwable))
        }

        viewModelScope.launch(coroutineExceptionHanlder) {

            LogUtil.showLogError("ttt", "${txt}hy")
            stateSearchLoading.setEventValue(SearchDataUi.onLoading)

            val map: HashMap<String, String> = HashMap<String, String>()
            map.put("txt", "${txt}")




            val respStation = searchRepository.autocomplete_station(map)
//            dataStationsTmp.value = respStation.data?:ArrayList()
            dataStationSearch.value = respStation

            stateSearchLoading.setEventValue(SearchDataUi.onSuccessSearchString(respStation))

            LogUtil.showLogError("ttt","bbbb ${respStation?.size}")


        }

    }


    fun selectDepart(position: Int): List<String> {
        val tmp = (dataDepart.value ?: ArrayList()).get(position).provinces
        dataProvinces.value = tmp
        return tmp
    }

    fun selectRegion(position: Int): List<String> {
        val tmp = (dataRegion.value ?: ArrayList()).get(position).provinces
        dataProvinces.value = tmp

        return tmp

    }


}

sealed class SearchDataUi {

    data class onSuccessSearchString(val data:List<String>) : SearchDataUi()

    object onSuccess : SearchDataUi()

    data class onSuccessIndex(val index:Int) : SearchDataUi()

    data class onError(val data: Throwable) : SearchDataUi()
    object onLoading : SearchDataUi()

}