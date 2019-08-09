package com.tech.deliverydetailspage.features.delivery.list.view

import androidx.paging.ItemKeyedDataSource
import android.content.Context
import android.util.Log
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.platform.libraries.logger.AppLogger
import com.tech.deliverydetailspage.features.delivery.list.DeliveryList
import javax.inject.Inject

class DeliveryDataSource @Inject constructor(
    private val deliveryList: DeliveryList
) : ItemKeyedDataSource<Int, Delivery>() {

    companion object {
        val TAG = DeliveryDataSource::class.java.simpleName
    }

    var pageNumber = 1
    val pageLimit = 5

    var params: LoadParams<Int>? = null
    var callback: LoadCallback<Delivery>? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Delivery>) {

        deliveryList(DeliveryList.Params(pageNumber, pageLimit)) {
            it.either({
                onCallError(
                    it.toString()
                )
                //Retry call
                loadInitial(params, callback)
            }, {
                onDeliveriesFetched(it, callback)
            })
        }
    }

    private fun onCallError(message: String) {
        Log.e(TAG, message)
    }

    private fun onDeliveriesFetched(activities: List<Delivery>, callback: LoadInitialCallback<Delivery>) {
        pageNumber+=5
        callback.onResult(activities)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Delivery>) {
        this.params = params
        this.callback = callback
        AppLogger.debug("Fetching next page: $pageNumber")

        deliveryList(DeliveryList.Params(params.key, pageLimit)) {
            it.either({
                onPaginationError(
                    it.toString()
                )
            }, {
                onMoreDeliveriesFetched(it, callback)
            })
        }

    }


    private fun onMoreDeliveriesFetched(shows: List<Delivery>, callback: LoadCallback<Delivery>) {
        pageNumber+=5
        callback.onResult(shows)
    }

    private fun onPaginationError(message: String) {
        Log.e(TAG, message)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Delivery>) {

    }

    override fun getKey(item: Delivery): Int {
        return pageNumber
    }

    fun clear() {
        pageNumber = 1
    }
}