package com.tech.deliverydetailspage.core.di.component

import com.tech.deliverydetailspage.core.di.module.ApplicationModule
import com.tech.deliverydetailspage.MyApplication
import com.tech.deliverydetailspage.core.di.module.MiscRxModule
import com.tech.deliverydetailspage.core.di.module.NetworkModule
import com.tech.deliverydetailspage.core.di.module.SharedPreferenceModule
import com.tech.deliverydetailspage.core.viewmodel.ViewModelModule
import com.tech.deliverydetailspage.features.delivery.DeliveryActivity
import com.tech.deliverydetailspage.features.delivery.details.view.DeliveryDetailsFragment
import com.tech.deliverydetailspage.features.delivery.list.view.DeliveryListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        ApplicationModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        MiscRxModule::class,
        SharedPreferenceModule::class
    ]
)
interface ApplicationComponent {
    fun inject(application: MyApplication)

    fun inject(activity: DeliveryActivity)

    fun inject(fragment: DeliveryListFragment)
    fun inject(fragment: DeliveryDetailsFragment)
}
