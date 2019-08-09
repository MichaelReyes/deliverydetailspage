package com.tech.deliverydetailspage.features.delivery.list

import android.util.Log
import com.google.gson.Gson
import com.tbl.activeshooter.core.interactor.UseCase
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.DeliveryRepository
import com.tech.deliverydetailspage.core.exception.Failure
import com.tech.deliverydetailspage.core.functional.Either
import javax.inject.Inject

class DeliveryList
@Inject constructor(
    private val deliveryRepository: DeliveryRepository
) : UseCase<List<Delivery>, DeliveryList.Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Delivery>> {
        return deliveryRepository.getDeliveryList(params.offset, params.limit)
    }

    data class Params(val offset: Int, val limit: Int)
}