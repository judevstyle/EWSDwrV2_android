package com.ssoft.ews4thai.views.warningDetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssoft.common.BaseFragment
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.data.model.warning.WarningStation
import com.ssoft.ews4thai.databinding.FragmentWarningDescBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.share.adapter.WarningAdapter

class WarningDescFragment : BaseFragment() ,HandleClickListener{
    private lateinit var adapters: WarningAdapter
    private lateinit var binding: FragmentWarningDescBinding


    companion object{
        fun instance(warningStation: WarningStation): WarningDescFragment {
            val data = Bundle()
            data.putString("a","")
            data.putParcelable("data", warningStation)
            return WarningDescFragment().apply{
                arguments = data
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWarningDescBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)


        val data = arguments?.getParcelable<WarningStation>("data")

        LogUtil.showLogError("dkki","${data.toString()}")

        adapters = WarningAdapter(requireContext(),this)
//        adapters.ewsDao = data.ews
        adapters.warningStation = data
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = adapters
        }

    }

    override fun onItemClick(view: View, position: Int, action: Int) {

    }

}