package com.tech.deliverydetailspage.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.DeliveryDataAccess
import com.tech.deliverydetailspage.core.data.delivery.Location
import com.tech.deliverydetailspage.core.data.delivery.LocationDataAccess


@Database(entities = [Delivery::class, Location::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun deliveryDataAccess(): DeliveryDataAccess

    abstract fun locationDataAccess(): LocationDataAccess

}