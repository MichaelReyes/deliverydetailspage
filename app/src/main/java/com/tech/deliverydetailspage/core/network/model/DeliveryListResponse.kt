package com.tech.deliverydetailspage.core.network.model

import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.Location


data class DeliveryListResponse(
    var id: Int,
    var description: String,
    var imageUrl: String,
    var location: Location
) {
    data class Location(
        var lat: Double,
        var lng: Double,
        var address: String
    )

    fun toDelivery(): Delivery{
        return Delivery(id, description, imageUrl)
    }

    fun toLocation(): com.tech.deliverydetailspage.core.data.delivery.Location{
        return Location(id, location.lat, location.lng, location.address)
    }

}