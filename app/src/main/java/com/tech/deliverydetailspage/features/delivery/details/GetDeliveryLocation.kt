package com.tech.deliverydetailspage.features.delivery.details

import android.util.Log
import com.google.gson.Gson
import com.tbl.activeshooter.core.interactor.UseCase
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.DeliveryRepository
import com.tech.deliverydetailspage.core.data.delivery.Location
import com.tech.deliverydetailspage.core.data.delivery.LocationRepository
import com.tech.deliverydetailspage.core.exception.Failure
import com.tech.deliverydetailspage.core.functional.Either
import javax.inject.Inject

class GetDeliveryLocation
@Inject constructor(
    private val locationRepository: LocationRepository
) : UseCase<Location, GetDeliveryLocation.Params>() {

    override suspend fun run(params: Params): Either<Failure, Location> {
        return locationRepository.getDeliveryLocation(params.id)
    }

    data class Params(val id: Int)
}