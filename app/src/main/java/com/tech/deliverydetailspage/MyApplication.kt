package com.tech.deliverydetailspage

import android.app.Application
import com.tech.deliverydetailspage.core.di.component.ApplicationComponent
import com.tech.deliverydetailspage.core.di.component.DaggerApplicationComponent
import com.tech.deliverydetailspage.core.di.module.ApplicationModule
import com.tech.deliverydetailspage.core.di.module.NetworkModule
import com.tech.deliverydetailspage.core.di.module.SharedPreferenceModule
import com.tech.deliverydetailspage.core.platform.libraries.logger.AppLogger

class MyApplication: Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule)
            .sharedPreferenceModule(SharedPreferenceModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initializeLogger()
        injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLogger() {
        AppLogger.plantTree()
    }

}