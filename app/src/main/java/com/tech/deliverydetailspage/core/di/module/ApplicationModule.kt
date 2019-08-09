package com.tech.deliverydetailspage.core.di.module

import android.content.Context
import androidx.room.Room
import com.tech.deliverydetailspage.core.data.AppDatabase
import com.tech.deliverydetailspage.core.data.delivery.DeliveryRepository
import com.tech.deliverydetailspage.core.data.delivery.LocationRepository

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "deliverydetailspage.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeliveryRepository(dataSource: DeliveryRepository.Network): DeliveryRepository = dataSource

    @Provides
    @Singleton
    fun provideLocationRepository(dataSource: LocationRepository.Network): LocationRepository = dataSource
}
