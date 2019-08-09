package com.tech.deliverydetailspage.features.delivery.details.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tbl.activeshooter.core.interactor.UseCase
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.Location
import com.tech.deliverydetailspage.core.platform.ui.BaseViewModel
import com.tech.deliverydetailspage.features.delivery.details.GetDeliveryLocation
import com.tech.deliverydetailspage.features.delivery.details.view.DeliveryDetailsView
import com.tech.deliverydetailspage.features.delivery.list.DeliveryList
import com.tech.deliverydetailspage.features.delivery.list.view.DeliveryListView
import javax.inject.Inject

class DeliveryDetailsViewModel
@Inject constructor(
    private val getDeliveryLocation: GetDeliveryLocation
) : BaseViewModel() {

    var deliveryDetailsUi: MutableLiveData<DeliveryDetailsView> = MutableLiveData()

    fun getDeliveryLocation(id: Int) = getDeliveryLocation(GetDeliveryLocation.Params(id)){
        it.either(::handleFailure, ::handleDeliveryLocation)
    }

    private fun handleDeliveryLocation(location: Location?){
        this.deliveryDetailsUi.value = DeliveryDetailsView(location)
    }
}