package com.tech.deliverydetailspage.core.data.delivery

import androidx.room.*
import com.tech.deliverydetailspage.core.data.converter.LocationConverters

@Entity
data class Delivery(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo var description: String,
    @ColumnInfo var imageUrl: String
)