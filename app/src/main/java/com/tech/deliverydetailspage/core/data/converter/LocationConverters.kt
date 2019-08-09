package com.tech.deliverydetailspage.core.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tech.deliverydetailspage.core.data.delivery.Location

class LocationConverters {

    @TypeConverter
    fun fromLocationJson(loc: Location): String{
        return Gson().toJson(loc)
    }

    @TypeConverter
    fun toLocation(jsonLocation: String): Location{
        val locationType = object : TypeToken<Location>() {}.type
        return Gson().fromJson<Location>(jsonLocation, locationType)
    }

}