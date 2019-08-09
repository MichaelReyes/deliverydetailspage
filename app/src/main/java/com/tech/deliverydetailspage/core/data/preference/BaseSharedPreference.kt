package com.tech.deliverydetailspage.core.data.preference

import android.content.SharedPreferences

abstract class BaseSharedPreference {

    lateinit var preferences: SharedPreferences

    protected abstract fun initBasePreference()

    fun putData(key: String, data: Any) {

        preferences?.let {pref ->
            when (data) {
                is String -> pref.edit().putString(key, data).apply()
                is Int -> pref.edit().putInt(key, data).apply()
                is Boolean -> pref.edit().putBoolean(key, data).apply()
                is Float -> pref.edit().putFloat(key, data).apply()
            }
        }

    }

    fun getStringData(key: String): String {
        return preferences!!.getString(key, "")
    }

    fun getIntData(key: String): Int {
        return preferences!!.getInt(key, 0)
    }

    fun getBooleanData(key: String): Boolean {
        return preferences!!.getBoolean(key, false)
    }

    fun getFloatData(key: String): Float {
        return preferences!!.getFloat(key, 0f)
    }

    fun clear() {
        preferences!!.edit().clear().commit()
    }
}