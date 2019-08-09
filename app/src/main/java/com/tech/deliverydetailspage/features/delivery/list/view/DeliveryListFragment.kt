package com.tech.deliverydetailspage.features.delivery.list.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson

import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.extension.failure
import com.tech.deliverydetailspage.core.extension.observe
import com.tech.deliverydetailspage.core.extension.observing
import com.tech.deliverydetailspage.core.extension.viewModel
import com.tech.deliverydetailspage.core.platform.ui.BaseFragment
import com.tech.deliverydetailspage.databinding.FragmentDeliveryBinding
import com.tech.deliverydetailspage.features.delivery.list.viewmodel.DeliveryListViewModel
import kotlinx.android.synthetic.main.fragment_delivery.*
import java.util.*
import javax.inject.Inject

class DeliveryListFragment : BaseFragment<FragmentDeliveryBinding>() {

    private lateinit var deliveryListViewModel: DeliveryListViewModel

    @Inject
    lateinit var deliveryListAdapter: DeliveryListAdapter

    lateinit var deliveryPaginatedListAdapter: DeliveryPaginatedListAdapter

    var deliveries = mutableListOf<Delivery>()

    override val layoutRes: Int
        get() = R.layout.fragment_delivery

    private var isLoading: Boolean by observing(false, didSet = {
        baseActivity.showLoading(isLoading)
    })

    override fun onCreated(savedInstance: Bundle?) {
        appComponent.inject(this)

        baseActivity.setToolbar(true, false, "Things to Deliver", false)

        /* For getting not paged list
        deliveryListViewModel = viewModel(viewModelFactory) {
            failure(failure, ::handleFailure)
            observe(deliveryListUi, ::handleDeliveryListUpdates)
        }

        deliveryListViewModel.getDeliveryList(1, 100)
        */

        initializeViews()

        //For getting paged list
        deliveryListViewModel = viewModel(viewModelFactory) {}
        fetchPaginatedDeliveryList()
        observeViewModel()
    }

    fun observeViewModel(){
        deliveryListViewModel.paginatedDeliveryList.observe(this, androidx.lifecycle.Observer {
            deliveryPaginatedListAdapter.submitList(it)
            isLoading = false
        })
    }

    private fun fetchPaginatedDeliveryList(){
        isLoading = true
        deliveryListViewModel.getPaginatedDeliveryList()
    }

    private fun initializeViews() {

        deliveryPaginatedListAdapter = DeliveryPaginatedListAdapter(DeliveriesDiffUtilItemCallback())

        rvData.layoutManager = LinearLayoutManager(context)
        rvData.adapter = deliveryPaginatedListAdapter
        rvData.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvData.setHasFixedSize(true)

        deliveryPaginatedListAdapter.navController = findNavController()
    }

    private fun handleDeliveryListUpdates(view: DeliveryListView?) {
        view?.let {
            deliveries.clear()
            deliveries.addAll(it.deliveryList)
            deliveryListAdapter.collection = deliveries
        }
    }

}
