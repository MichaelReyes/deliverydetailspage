package com.tech.deliverydetailspage.core.data.delivery

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeliveryDataAccess {

    @Query("SELECT * from Delivery")
    fun getDeliveries(): List<Delivery>

    @Query("SELECT * FROM Delivery WHERE id LIKE :id")
    fun findById(id: Int): Delivery

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(delivery: Delivery)

    @Insert
    fun insertAll(vararg delivery: Delivery)

    @Query("DELETE FROM Delivery")
    fun deletAllDeliveryItems()
}