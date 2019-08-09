package com.tech.deliverydetailspage.features.delivery.list.view

import androidx.recyclerview.widget.DiffUtil
import com.tech.deliverydetailspage.core.data.delivery.Delivery


class DeliveriesDiffUtilItemCallback : DiffUtil.ItemCallback<Delivery>() {
    override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
        return oldItem.description == newItem.description
    }
}
