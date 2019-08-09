package com.tech.deliverydetailspage.core.network.api

import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.network.model.DeliveryListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DeliveryService
@Inject constructor(@Named("Backend") retrofit: Retrofit) :
    AppApi.DeliveryApi {
    private val deliveryApi by lazy { retrofit.create(AppApi.DeliveryApi::class.java) }

    override
    fun getDeliveryList(offset: Int, limit: Int): Call<List<DeliveryListResponse>> =
        deliveryApi.getDeliveryList(offset, limit)
}