package com.ssoft.ews4thai.views.news.desc

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssoft.common.BaseFragment
import com.ssoft.ews4thai.data.model.ReportModel
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.FragmentReportBinding
import com.ssoft.ews4thai.databinding.FragmentWarningDescBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.views.warningDetail.WarningDescFragment
import org.parceler.Parcels

class WarnReportNewsPageFragment() : BaseFragment(), HandleClickListener {
//    private lateinit var adapters: WarningReportAdapter
private lateinit var binding: FragmentReportBinding

    lateinit var data: ReportModel

    companion object{
        fun instance(warningStation: ReportModel): WarnReportNewsPageFragment {
            val data = Bundle()
            data.putParcelable("data", warningStation)
            return WarnReportNewsPageFragment().apply{
                arguments = data
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentReportBinding.inflate(inflater,container,false)

        return binding.root


    }


    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)


        val data = arguments?.getParcelable<ReportModel>("data")

        binding.descTV.text = "${data!!.report_body}"

    }

    override fun onItemClick(view: View, position: Int, action: Int) {

    }

}