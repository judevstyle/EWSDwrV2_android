package com.ssoft.ews4thai.views.search

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.ssoft.common.BaseActivity
import com.ssoft.ews4thai.R
import com.ssoft.ews4thai.databinding.ActivitySearchStationBinding
import com.ssoft.ews4thai.share.adapter.CallbackFragment
import com.ssoft.ews4thai.views.mapRadar.MapsRadarDataUi
import com.taitos.nup.common.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel

class SearchStationActivity : BaseActivity(), CallbackFragment {
    private lateinit var binding: ActivitySearchStationBinding
    private var mCallback: CallbackFragment? = null
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding = ActivitySearchStationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserv()

      //  viewModel.test("")
        viewModel.getRegion()
        mCallback = this


        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            //   .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right)
            .add(R.id.content, SearchStationFragment())
            .addToBackStack(null)
//            .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )


            .commit()
    }

    override fun onEvent(fragment: Fragment?) {

        replaceFragment(fragment!!)

    }


    fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun initObserv(){

        viewModel.stateLoading.observe(this, EventObserver { state: SearchDataUi ->
            when (state) {
                is SearchDataUi.onSuccess -> {

                    hideDialog()

                }
                is SearchDataUi.onError -> {
                    hideDialog()

                }
                is SearchDataUi.onLoading -> {
                    showProgressDialogCancel()
                }
                else ->{}
            }
        })

    }



}