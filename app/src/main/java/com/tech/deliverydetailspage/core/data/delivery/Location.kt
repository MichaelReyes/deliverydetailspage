package com.tech.deliverydetailspage.core.data.delivery

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo var lat: Double,
    @ColumnInfo var lng: Double,
    @ColumnInfo var address: String
)