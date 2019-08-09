package com.tech.deliverydetailspage.features.delivery.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tbl.activeshooter.core.interactor.UseCase
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.platform.ui.BaseViewModel
import com.tech.deliverydetailspage.core.service.DataSourceFactory
import com.tech.deliverydetailspage.features.delivery.list.DeliveryList
import com.tech.deliverydetailspage.features.delivery.list.view.DeliveryDataSource
import com.tech.deliverydetailspage.features.delivery.list.view.DeliveryListView
import javax.inject.Inject

class DeliveryListViewModel
@Inject constructor(
    private val deliveryList: DeliveryList
) : BaseViewModel() {

    var paginatedDeliveryList: LiveData<PagedList<Delivery>> = MutableLiveData()

    fun getPaginatedDeliveies(): LiveData<PagedList<Delivery>> {
        return paginatedDeliveryList
    }

    var deliveryListUi: MutableLiveData<DeliveryListView> = MutableLiveData()
    lateinit var deliveryDataSourceFactory: DataSourceFactory<Int, Delivery, DeliveryDataSource>

    private fun initializeDataSource() {
        deliveryDataSourceFactory = DataSourceFactory(DeliveryDataSource(deliveryList))
    }

    //For fetching all items in list
    fun getDeliveryList(offset: Int, limit: Int) = deliveryList(DeliveryList.Params(offset, limit)){
        it.either(::handleFailure, ::handleDeliveryList )
    }

    //For fetching list with pagination
    fun getPaginatedDeliveryList(){
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .build()

        initializeDataSource()
        paginatedDeliveryList = LivePagedListBuilder(deliveryDataSourceFactory, config).build()
    }

    fun refreshPaginatedList(){
        deliveryDataSourceFactory.dataSource.invalidate()
    }

    private fun handleDeliveryList(deliveryList: List<Delivery>){
        //this.deliveryListUi.value = DeliveryListView(deliveryList)
    }
}