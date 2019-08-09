package com.tech.deliverydetailspage.core.data.delivery

import com.tech.deliverydetailspage.core.data.AppDatabase
import com.tech.deliverydetailspage.core.exception.Failure
import com.tech.deliverydetailspage.core.functional.Either
import om.icon.ecommerce.core.platform.network.NetworkHandler
import javax.inject.Inject

interface LocationRepository {
    fun getDeliveryLocation(id: Int): Either<Failure, Location>

    class Network
    @Inject constructor(
        private val database: AppDatabase
    ) : LocationRepository {

        override fun getDeliveryLocation(id: Int): Either<Failure, Location> {
            val location = database.locationDataAccess().findById(id)

            return Either.Right(location)
        }
    }
}
