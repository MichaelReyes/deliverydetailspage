package com.tech.deliverydetailspage.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tech.deliverydetailspage.features.delivery.details.viewmodel.DeliveryDetailsViewModel
import com.tech.deliverydetailspage.features.delivery.list.viewmodel.DeliveryListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryListViewModel::class)
    abstract fun bindsDeliveryListViewModel(viewModel: DeliveryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryDetailsViewModel::class)
    abstract fun bindsDeliveryDetailsViewModel(viewModel: DeliveryDetailsViewModel): ViewModel
}