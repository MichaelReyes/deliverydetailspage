package com.tech.deliverydetailspage.core.extension

import android.content.SharedPreferences

fun SharedPreferences.save(key: String, value: String) {
    this.edit().putString(key, value).apply()
}

fun SharedPreferences.save(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}

fun SharedPreferences.retrieve(key: String, value: Boolean): Boolean {
    return this.getBoolean(key, value)
}

fun SharedPreferences.retrieve(key: String, value: String): String {
    return this.getString(key, value)
}

fun SharedPreferences.clear() {
    this.edit().clear().apply()
}