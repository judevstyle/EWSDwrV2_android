package com.ssoft.ews4thai

import android.app.Application
import com.ba.phsapps.network.api.ServiceAPI
import com.ssoft.ews4thai.data.repository.DashBordImpt
import com.ssoft.ews4thai.data.repository.DashBordRepository
import com.ssoft.ews4thai.data.repository.MapRadarViewImpt
import com.ssoft.ews4thai.data.repository.MapRadarViewRepository
import com.ssoft.ews4thai.data.repository.MapViewImpt
import com.ssoft.ews4thai.data.repository.MapViewRepository
import com.ssoft.ews4thai.data.repository.ReportImpt
import com.ssoft.ews4thai.data.repository.ReportRepository
import com.ssoft.ews4thai.data.repository.SearchImpt
import com.ssoft.ews4thai.data.repository.SearchRepository
import com.ssoft.ews4thai.network.HttpBaseConnect
import com.ssoft.ews4thai.views.main.MainViewModel
import com.ssoft.ews4thai.views.map.MapsViewModel
import com.ssoft.ews4thai.views.mapRadar.MapsRadarViewModel
import com.ssoft.ews4thai.views.news.ReportWarningViewModel
import com.ssoft.ews4thai.views.search.SearchViewModel
import com.ssoft.ews4thai.views.warningType.WarningTypeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()



        initKoin()

    }

    fun initKoin() {

        startKoin {

            androidContext(this@App)

            val networkModule = module {


//                single {
//                    HttpBaseConnect().getApiService()
//                }


//                single {
//                    HttpBasicConnect().getApiService()
//                }
                single<ServiceAPI> {
                    HttpBaseConnect().build()
                }

            }


            val model = module {

                single<DashBordRepository> {
                    DashBordImpt(get())
                }

                single<MapViewRepository> {
                    MapViewImpt(get())
                }

                single<MapRadarViewRepository> {
                    MapRadarViewImpt(get())
                }

                single<ReportRepository> {
                    ReportImpt(get())
                }
                single<SearchRepository> {
                    SearchImpt(get())
                }

                single {
                    DashBordImpt(get())
                    MapViewImpt(get())
                    MapRadarViewImpt(get())
                    ReportImpt(get())
                    SearchImpt(get())


                }
                viewModel { MapsViewModel(get())  }
                viewModel { WarningTypeViewModel(get())  }
                viewModel { MainViewModel(get())  }
                viewModel { MapsRadarViewModel(get())  }
                viewModel { ReportWarningViewModel(get())  }
                viewModel { SearchViewModel(get())  }

            }
//            viewModel { NotificationViewModel(get()) }


            modules(networkModule, model)


        }


    }


}