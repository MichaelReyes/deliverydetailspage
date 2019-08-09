package com.tech.deliverydetailspage.core.data.delivery

import android.util.Log
import com.google.gson.Gson
import com.squareup.haha.guava.collect.Iterators.transform
import com.tech.deliverydetailspage.core.data.AppDatabase
import com.tech.deliverydetailspage.core.exception.Failure
import com.tech.deliverydetailspage.core.functional.Either
import com.tech.deliverydetailspage.core.network.api.DeliveryService
import com.tech.deliverydetailspage.core.service.ApiClient
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import om.icon.ecommerce.core.platform.network.NetworkHandler
import retrofit2.HttpException
import javax.inject.Inject

interface DeliveryRepository {
    fun getDeliveryList(offset: Int, limit: Int): Either<Failure, List<Delivery>>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val deliveryService: DeliveryService,
        private val database: AppDatabase,
        private val apiClient: ApiClient
    ) : DeliveryRepository {

        override fun getDeliveryList(offset: Int, limit: Int): Either<Failure, List<Delivery>> {
            return when(networkHandler.isConnected){
                true -> {
                    var deliveryList = mutableListOf<Delivery>()
                    var locationList = mutableListOf<Location>()

                    apiClient.request(
                        deliveryService.getDeliveryList(offset, limit),
                        {
                            it.map { deliveryResponse ->
                                deliveryList.add(deliveryResponse.toDelivery())
                                locationList.add(deliveryResponse.toLocation())
                            }
                            deliveryList

                        }, emptyList(), {
                            database.deliveryDataAccess().deletAllDeliveryItems()
                            database.deliveryDataAccess().insertAll(*it.toTypedArray())
                            database.locationDataAccess().deletAllLocations()
                            database.locationDataAccess().insertAll(*locationList.toTypedArray())
                        }

                    )
                }
                false, null -> Either.Right(database.deliveryDataAccess().getDeliveries())

            }
        }
    }
}
