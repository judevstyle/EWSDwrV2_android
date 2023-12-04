/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssoft.common.util

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.core.content.edit
import com.ssoft.common.R
import java.util.logging.Filter

/**
 * Returns the `location` object as a human readable string.
 */
fun Location?.toText(): String {
    return if (this != null) {
        "($latitude, $longitude)"
    } else {
        "Unknown location"
    }
}

/**
 * Provides access to SharedPreferences for location to Activities and Services.
 */


object LogUtil {

    fun showLogError(tag: String, value: String) {
        Log.e(tag, value)
    }

    fun showLogDebug(tag: String, value: String) {
        Log.d(tag, value)
    }

    fun showLogWarn(tag: String, value: String) {
        Log.w(tag, value)
    }

    fun showLogInfo(tag: String, value: String) {
        Log.i(tag, value)
    }

}

object SharedPreferenceUtil {

    const val KEY_FOREGROUND_ENABLED = "tracking_foreground_location"
    const val LAT_UPDATE = "lat_location"
    const val LNG_UPDATE = "lng_location"
    const val SHIPMENT_RUNING = "shipment_runing"
    const val REFRESH_TOKEN = "rf_token"
    const val TOKEN = "token"


    const val closeNoti = "closeNoti"


    const val USER = "user"

    const val providerSelect = "Provider"
    const val plugSelect = "Plug"
    const val brandSelect = "Brand"

    const val comming = "comming"
    const val close = "close"
    const val update = "update"

    const val pv = "private"

    const val troilet_rate = "troilets_rate"
    const val charge_rate = "charge_rate"
    const val fac_rate = "fac_rate"


    const val LOGIN_STATE = "login"
    const val SHIPMENT = "shipment_id"

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The [Context].
     */


    fun updateUser(context: Context, json: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(USER, json)
        }

    fun clearAll(context: Context){

        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit().clear().commit();


    }


    fun getUser(context: Context) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(USER, null)
//
//

    fun updateProviderSelect(context: Context, pv: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(providerSelect, pv)
        }


    fun getProviderSelect(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(providerSelect, null)


    fun updatePlugSelect(context: Context, plug: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(plugSelect, plug)
        }


    fun getPlugSelect(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(plugSelect, null)


    fun updateBrandSelect(context: Context, plug: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(brandSelect, plug)
        }


    fun getBrandSelect(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(brandSelect, null)


    fun updateSoon(context: Context, status: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(comming, status)

        }


    fun updateNoti(context: Context, status: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(closeNoti, status)

        }


    fun getNotiState(context: Context): Boolean =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getBoolean(closeNoti,false)





    fun updateClose(context: Context, status: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(close, status)

        }

    fun updateEdit(context: Context, status: Boolean) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(update, status)

        }


    fun updateTL(context: Context, number: Int) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putInt(troilet_rate, number)

        }


    fun updateCharge(context: Context, number: Int) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putInt(charge_rate, number)

        }

    fun updateFac(context: Context, number: Int) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putInt(fac_rate, number)

        }


    fun getFilter(context: Context): com.ssoft.common.filter.Filter {
        val spf = context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        val data = com.ssoft.common.filter.Filter()
        LogUtil.showLogError("dedw", "${spf.getInt(SharedPreferenceUtil.troilet_rate, 0)}")
        data.apply {

            comming = spf.getBoolean(SharedPreferenceUtil.comming, false)
            close = spf.getBoolean(SharedPreferenceUtil.close, false)
            update = spf.getBoolean(SharedPreferenceUtil.update, false)
//
            chargeRate = spf.getInt(SharedPreferenceUtil.charge_rate, 0)
            facRate = spf.getInt(SharedPreferenceUtil.fac_rate, 0)
            troiletRate = spf.getInt(SharedPreferenceUtil.troilet_rate, 0)

        }

        return data

    }


    fun updateTokenPref(context: Context, token: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(TOKEN, token)
        }


    fun getTokenPref(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(TOKEN, "")


    fun updateRefreshTokenPref(context: Context, token: String) =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).edit {
            putString(REFRESH_TOKEN, token)
        }


    fun getRefreshTokenPref(context: Context): String? =
        context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
            .getString(REFRESH_TOKEN, "")


//    fun getLoginPref(context: Context): Boolean =
//        context.getSharedPreferences(
//            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
//            .getBoolean(LOGIN_STATE, false)
//
//
//    fun updateLoginPref(context: Context,state:Boolean) =
//        context.getSharedPreferences(
//            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
//            .edit{
//                putBoolean(LOGIN_STATE, state)
//            }
//


}
