package com.ssoft.ews4thai.views.news

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssoft.common.BaseActivity
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.ActivityReportBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.views.NewsDescActivity
import com.ssoft.ews4thai.views.map.MapsDataUi
import com.ssoft.ews4thai.views.mapRadar.MapsRadarViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.parceler.Parcels

class ReportActivity : BaseActivity(),HandleClickListener {

    private val viewModel: ReportWarningViewModel by viewModel()
    private lateinit var adapters: ReportAdapter

    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserv()
        viewModel.getReport()

    }


    fun initView(){

        binding.refresh.setOnRefreshListener {
            viewModel.getReport()
        }

        adapters = ReportAdapter(this,this)
        binding.rcReport.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = adapters
        }

    }

    fun initObserv(){

        viewModel.dataResp.observe(this, Observer { state: ReportDataUi ->

            when (state) {
                is ReportDataUi.onSuccess -> {

                    adapters.setItem(state.data?:ArrayList())
                    binding.refresh.isRefreshing = false

                    hideDialog()

                }
                is ReportDataUi.onError -> {
                    hideDialog()
                    binding.refresh.isRefreshing = false

                }
                is ReportDataUi.onLoading -> {
                    if (!binding.refresh.isRefreshing)
                    showProgressDialog()
                }
            }
        })


    }


    override fun onItemClick(view: View, position: Int, action: Int) {
        startActivity(
            Intent(this,NewsDescActivity::class.java)
                .putExtra("index",position)
                .putExtra("data", adapters.items)

        )

    }


}