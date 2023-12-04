package com.ssoft.common

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ssoft.common.dialog.ProgressDialog

abstract class BaseActivity  : AppCompatActivity() {
//    lateinit var viewDataBinding: T


    private var mProgressDialog: ProgressDialog? = null
    lateinit var tfRegular: Typeface
    lateinit var tfLight: Typeface
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(getContentView())
////        viewDataBinding = DataBindingUtil.bind<>()
//
////        tfRegular = Typeface.createFromAsset(assets, "Kanit-Bold.ttf")
////        tfLight = Typeface.createFromAsset(assets, "Kanit-Light.ttf")
//        onViewReady(savedInstanceState, intent)
//
//    }

    @CallSuper
    protected open  fun onViewReady(
        savedInstanceState: Bundle?,
        intent: Intent?
    ) { //To be used by child activities
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
    }

    protected open fun hideKeyboard() {
        try {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) imm.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                0
            )
        } catch (e: Exception) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e)
        }
    }


    open fun noInternetConnectionAvailable() {
//        showToast(getString(R.string.noNetworkFound))
    }


    protected open fun showBackArrow() {
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setDisplayShowHomeEnabled(true)
        }
    }

    open fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)

            //            if (title != null)
//                mProgressDialog.setTitle(title);
//            mProgressDialog.setIcon(R.mipmap.ic_launcher);
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog!!.isShowing()) { //            mProgressDialog.setMessage(message);
            mProgressDialog!!.show()
        }
    }

    open fun showProgressDialogCancel() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)

            //            if (title != null)
//                mProgressDialog.setTitle(title);
//            mProgressDialog.setIcon(R.mipmap.ic_launcher);
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.setCancelable(false);
            mProgressDialog!!.alertDialog?.setCancelable(false)

        }
        if (!mProgressDialog!!.isShowing()) { //            mProgressDialog.setMessage(message);

            mProgressDialog!!.show()
        }
    }
    open fun hideDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
        }
    }

    protected open fun showAlertDialog(msg: String?) {
        val dialogBuilder =
            AlertDialog.Builder(this)
        dialogBuilder.setTitle(null)
//        dialogBuilder.setIcon(R.mipmap.ic_launcher)
        dialogBuilder.setMessage(msg)
        dialogBuilder.setPositiveButton(
            ""
//            getString(R.string.dialog_ok_btn)
        ) { dialog, which -> dialog.cancel() }
        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }


    protected open fun showToast(mToastMsg: String?) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show()
    }

//    protected abstract fun getContentView(): Int

}