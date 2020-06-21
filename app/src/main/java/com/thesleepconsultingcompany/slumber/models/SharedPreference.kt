package com.thesleepconsultingcompany.slumber.models

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreference {
    object SavedSharedPreference{
        const val EMAIL = "email"
        const val BEFORESLEEP = "beforesleep"
        const val BEFOREBED = "beforebed"
        const val WAKEUPREASON = "wakeupreason"

        fun getSharedPrefernces(ctx:Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun setEmail(email:String, ctx: Context){
            val editor = getSharedPrefernces(
                ctx
            )?.edit()
            editor?.putString(EMAIL,email)
            editor?.apply()
        }
        fun getEmail(ctx: Context) = getSharedPrefernces(ctx)?.getString(EMAIL,"")

        fun setBeforeSleep(ctx: Context, beforeSleep: Set<String>){
            val editor = getSharedPrefernces(ctx)?.edit()
            editor?.putStringSet(BEFORESLEEP, beforeSleep)
            editor?.apply()
        }
        fun getBeforeSleep(ctx: Context): Set<String>?  = getSharedPrefernces(ctx)?.getStringSet(BEFORESLEEP,
            setOf("Laptop","Phone","Television","Add Activity..."))

        fun setBeforeBed(ctx: Context, beforeBed: Set<String>){
            val editor = getSharedPrefernces(ctx)?.edit()
            editor?.putStringSet(BEFOREBED, beforeBed)
            editor?.apply()
        }
        fun getBeforeBed(ctx: Context): Set<String>? = getSharedPrefernces(ctx)?.getStringSet(BEFOREBED,
            setOf("Laptop","Phone","Television","Add Activity..."))

        fun setWakeupReason(ctx: Context,wakeup: Set<String>){
            val editor = getSharedPrefernces(ctx)?.edit()
            editor?.putStringSet(WAKEUPREASON,wakeup)
            editor?.apply()
        }
        fun getWakeupReason(ctx: Context): Set<String>?  = getSharedPrefernces(ctx)?.getStringSet(WAKEUPREASON,
            setOf("Didn't WakeUp","Bad Dream","Body Pain","Tension","Snoring","Noise","Hunger","Add Reason..."))
    }
}