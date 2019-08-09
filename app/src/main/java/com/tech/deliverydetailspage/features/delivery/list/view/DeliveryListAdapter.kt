package com.tech.deliverydetailspage.features.delivery.list.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.extension.inflate
import com.tech.deliverydetailspage.core.platform.ui.AutoUpdatableAdapter
import com.tech.deliverydetailspage.features.delivery.details.view.DeliveryDetailsFragment
import kotlinx.android.synthetic.main.item_delivery.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class DeliveryListAdapter
@Inject constructor() : RecyclerView.Adapter<DeliveryListAdapter.ViewHolder>(), AutoUpdatableAdapter {

    internal var collection: List<Delivery> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
        notifyDataSetChanged()
    }

    internal var navController: NavController? = null

    internal var clickListener: (Delivery) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_delivery))

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(delivery: Delivery) {
            itemView.ivDeliveryImage.setImageUrl(delivery.imageUrl)
            itemView.tvDescription.text = delivery.description
            itemView.setOnClickListener {
                navController?.let {
                    var args = Bundle()
                    args.putString(DeliveryDetailsFragment.ARGS_DELIVERY, Gson().toJson(delivery))
                    it.navigate(R.id.action_deliveryListFragment_to_deliveryDetailsFragment, args)
                }
            }
        }


    }


}