package com.ssoft.ews4thai.views.search

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ssoft.common.BaseFragment
import com.ssoft.common.util.LogUtil
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.FragmentSearchStationBinding
import com.ssoft.ews4thai.share.HandleClickListener
import com.ssoft.ews4thai.share.adapter.CallbackFragment
import com.ssoft.ews4thai.views.WarningDescActivity
import com.ssoft.ews4thai.views.mapRadar.MapsRadarDataUi
import com.ssoft.ews4thai.views.mapRadar.MapsRadarViewModel
import com.taitos.nup.common.EventObserver
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchStationFragment : BaseFragment(), HandleClickListener {

    private var state_closeApp: Boolean = true
    private lateinit var binding: FragmentSearchStationBinding
    private lateinit var adapters: StringAdapter

    private val viewModel: SearchViewModel by sharedViewModel()

    var state_search = false

    var action = 1
    var actionLV = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchStationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        super.onViewReady(view, savedInstanceState)
        adapters = StringAdapter(requireContext(), this)
        initView()
        initObserv()

    }

    fun initObserv() {


        viewModel.stateLoadingStation.observe(viewLifecycleOwner,EventObserver{ state: SearchDataUi ->
            when (state) {


                is SearchDataUi.onSuccessIndex -> {

                    startActivity(
                        Intent(requireContext(), WarningDescActivity::class.java)
                            .putExtra("data",ArrayList(viewModel.dataStationsTmp.value?:ArrayList()))
                            .putExtra("index", state.index)
                    )


                    hideDialog()

                }

                is SearchDataUi.onError -> {
                    hideDialog()

                }

                is SearchDataUi.onLoading -> {
                   showProgressDialog()
                }
                else->{}
            }
        })


        viewModel.stateSearchLoading.observe(viewLifecycleOwner,EventObserver{ state: SearchDataUi ->
            when (state) {
                is SearchDataUi.onSuccessSearchString -> {

                    initRecycleView(state.data)
                    hideDialog()
                    binding.progressCircular.visibility = View.GONE

                }

                is SearchDataUi.onSuccess -> {

//                    initRecycleView(state.data)
                    hideDialog()
                    binding.progressCircular.visibility = View.GONE

                }

                is SearchDataUi.onError -> {
                    hideDialog()
                    binding.progressCircular.visibility = View.GONE

                }

                is SearchDataUi.onLoading -> {
                    binding.progressCircular.visibility = View.VISIBLE
//                    showProgressDialog()
                }
                else->{}
            }
        })

        viewModel.dataMapsResp.observe(this, Observer { state: MapsRadarDataUi ->
            when (state) {
                is MapsRadarDataUi.onSuccess -> {

                    initRecycleView(viewModel.dataStationStr.value ?: ArrayList())
                    hideDialog()

                }

                is MapsRadarDataUi.onError -> {
                    hideDialog()

                }

                is MapsRadarDataUi.onLoading -> {
                    showProgressDialog()
                }
            }
        })

        viewModel.dataStationSearch.observe(viewLifecycleOwner, Observer {

            initRecycleView(it)

        })


    }

    fun initView() {


        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            adapter = adapters
        }
        binding.action1.setOnClickListener {
            // actionLV = 1
            action = 1
            state_closeApp = false

            initRecycleView(viewModel.dataRegionResp.value ?: ArrayList())

        }
        binding.action2.setOnClickListener {
            // actionLV = 1
            action = 2
            state_closeApp = false

            initRecycleView(viewModel.dataDepartResp.value ?: ArrayList())

        }

        binding!!.root.isFocusableInTouchMode = true
        binding!!.root.requestFocus()
        binding!!.root.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            Log.i(attr.tag, "keyCode: $keyCode")
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === KeyEvent.ACTION_UP) {
//                Log.e("dd", "onKey Back listener is working!!!")
//                fragmentManager.popBackStack(
//                    null,
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE
//                )
                actionLV--
//                //Log.e("dd","${countAction}")
//
//                if (state_search)
//                    countAction = -1
                when (actionLV) {

                    1 -> {
//                        toolbar.setText("ค้นหาตามจังหวัด")

//                        adapters.items = viewModel.
//                        adapters.notifyDataSetChanged()
//                        getLV2(dataLV1.get(position))
                        initRecycleView(viewModel.dataProvinces.value ?: ArrayList())

                    }

                    2 -> {
//                        toolbar.setText("ค้นหาตามชื่อ")

//                        fragmentManager!!.popBackStack()

//                        adapters.items =  dataLV2
//                        adapters.notifyDataSetChanged()
                    }

                    0 -> {
                        if (action == 1) {
//                            toolbar.setText("ค้นหาตามภาค")
                            initRecycleView(viewModel.dataRegionResp.value ?: ArrayList())

                        } else {
//                            toolbar.setText("ค้นหาตามสำนักงาน")
                            initRecycleView(viewModel.dataDepartResp.value ?: ArrayList())

                        }

                    }

                    -1 -> {
                        if (state_closeApp) {
                            activity?.finish()
                        } else {
//                            toolbar.setText("ค้นหา")
                            LogUtil.showLogError("clear","1")
                            resetView()
                        }
                        state_closeApp = true

                    }

                }
                return@OnKeyListener true
            }
            false
        })
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            var timer: CountDownTimer? = null

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0.isNullOrEmpty()) {

//                    if (state_search) {
//                        LogUtil.showLogError("clear","2")
//
//                        resetView()
//
//                    }

                } else {
                    state_search = true

                    Log.e("ssss", "${p0}d22d")
                    actionLV = 2
                    timer?.cancel()
                    timer = object : CountDownTimer(500, 800) {
                        override fun onTick(millisUntilFinished: Long) {}
                        override fun onFinish() {
                            if (!(p0.toString()).equals(""))
                                viewModel.test(p0.toString())
                            Log.e("dwkl", "dlw")
                            // afterTextChanged.invoke(editable.toString())
                        }
                    }.start()




//                    state_search = true
//                    countAction = 2
//                    dataLV3_keyStation.clear()
//                    dataLV3.clear()
//                    val list: ArrayList<StationDao> = ArrayList(
//                        realm.where(StationDao::class.java)
//                            .contains("name", "${p0!!}", Case.INSENSITIVE).findAll()
//                    )
//                    for (data in list) {
//                        dataLV3.add(data.name)
//                        dataLV3_keyStation.add(data.stn_id)
//                    }
//                    initRecycleView(dataLV3)


                }


            }
        })

        binding.searchET!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            Log.i(attr.tag, "keyCode: $keyCode")
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action === KeyEvent.ACTION_UP) {
                LogUtil.showLogError("clear","3")

                resetView()

                return@OnKeyListener true
            }
            false
        })
        var scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                binding.searchET.hideKeyboard()

            }

//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                val totalItemCount = recyclerView!!.layoutManager.itemCount
//                if (totalItemCount == lastVisibleItemPosition + 1) {
//                    Log.d("MyTAG", "Load new list")
//                    recycler.removeOnScrollListener(scrollListener)
//                }
//            }
        }
        binding.recycleView.addOnScrollListener(scrollListener)
    }


    fun initRecycleView(data: List<String>) {

        binding.recycleView.visibility = View.VISIBLE
//        action1.vi
        adapters.setItem(data)
        adapters.notifyDataSetChanged()
        binding.recycleView.scheduleLayoutAnimation()

    }

    override fun onResume() {
        super.onResume()
        hideDialog()
    }

    override fun onItemClick(view: View, position: Int, action: Int) {
        actionLV++

        when (actionLV) {

            1 -> {
                if (this.action == 1) {
                    initRecycleView(viewModel.selectRegion(position))
                } else {
                    initRecycleView(viewModel.selectDepart(position))
                }

            }

            2 -> {

                viewModel.getStation(adapters.getItem(position))

            }

            else -> {
                showProgressDialog()
                actionLV--

                if (state_search) {
                    viewModel.searchStation(binding.searchET.text.toString().trim(),position)
                }else{

                    startActivity(
                        Intent(requireContext(), WarningDescActivity::class.java)
                            .putExtra("data", ArrayList(viewModel.dataStations.value ?: ArrayList()))
                            .putExtra("index", position)
                    )
                }


            }

        }

    }


    fun resetView() {
        LogUtil.showLogError("fc","clear")
        state_search = false
        binding.searchET.hideKeyboard()
        binding.searchET.setText("")
        binding.searchET.clearFocus()
        binding!!.recycleView.visibility = View.GONE
        binding!!.action1.visibility = View.VISIBLE
        binding!!.action2.visibility = View.VISIBLE
        actionLV = 0
        binding!!.root!!.isFocusableInTouchMode = true
        binding!!.root!!.requestFocus()
    }


}