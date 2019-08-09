package com.tech.deliverydetailspage.core.data.delivery

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDataAccess {

    @Query("SELECT * from Location")
    fun getLocations(): List<Location>

    @Query("SELECT * FROM Location WHERE id LIKE :id")
    fun findById(id: Int): Location

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location)

    @Insert
    fun insertAll(vararg location: Location)

    @Query("DELETE FROM Location")
    fun deletAllLocations()
}