package com.thesleepconsultingcompany.sleepdoctor

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.crypto.Cipher

class SharedPreference {
    object SavedSharedPreference{
        const val EMAIL = "email"

        fun getSharedPrefernces(ctx:Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun setEmail(email:String, ctx: Context){
            val editor = getSharedPrefernces(ctx)?.edit()
            editor?.putString(EMAIL,email)
            editor?.apply()
        }
        fun getEmail(ctx: Context):String? {
            return getSharedPrefernces(ctx)?.getString(EMAIL,"")
        }
    }
}