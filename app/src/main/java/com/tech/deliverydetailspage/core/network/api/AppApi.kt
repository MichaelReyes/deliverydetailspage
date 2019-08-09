package com.tech.deliverydetailspage.core.network.api


import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.network.model.DeliveryListResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface AppApi {

    interface DeliveryApi {
        @Headers("Content-Type: application/json")
        @GET("deliveries")
        fun getDeliveryList(
            @Query("offset") offset: Int, @Query("limit") limit: Int
        ): Call<List<DeliveryListResponse>>
    }

}