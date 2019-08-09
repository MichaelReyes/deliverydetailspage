package com.tech.deliverydetailspage.core.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideAppSharedPreference(): SharedPreferences {
        return context.getSharedPreferences("AppPreference", Context.MODE_PRIVATE)
    }

}