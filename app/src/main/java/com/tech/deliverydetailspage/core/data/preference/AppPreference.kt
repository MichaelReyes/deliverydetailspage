package com.tech.deliverydetailspage.core.data.preference

import android.content.SharedPreferences
import javax.inject.Inject


class AppPreference @Inject
constructor(val appPreference: SharedPreferences) : BaseSharedPreference() {

    init {
        initBasePreference()
    }

    override fun initBasePreference() {
        preferences = appPreference
    }

}