package com.tech.deliverydetailspage.features.delivery.list.view

import androidx.paging.PagedList
import com.tech.deliverydetailspage.core.data.delivery.Delivery

data class DeliveryListView(
    /*val deliveryList: List<Delivery>*/ //For a not paginated list
    val deliveryList: PagedList<Delivery>
)