package com.tech.deliverydetailspage.core.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MiscRxModule {
    internal val compositeDisposable: CompositeDisposable
        @Provides
        get() = CompositeDisposable()
}
