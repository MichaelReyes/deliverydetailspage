package com.tech.deliverydetailspage.features.delivery.details.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.data.delivery.Delivery
import com.tech.deliverydetailspage.core.data.delivery.Location
import com.tech.deliverydetailspage.core.extension.failure
import com.tech.deliverydetailspage.core.extension.observe
import com.tech.deliverydetailspage.core.extension.viewModel
import com.tech.deliverydetailspage.core.platform.ui.BaseFragment
import com.tech.deliverydetailspage.databinding.FragmentDeliveryDetailsBinding
import com.tech.deliverydetailspage.features.delivery.details.viewmodel.DeliveryDetailsViewModel
import kotlinx.android.synthetic.main.item_delivery.*

class DeliveryDetailsFragment : BaseFragment<FragmentDeliveryDetailsBinding>(), OnMapReadyCallback {

    private lateinit var deliveryDetailsViewModel: DeliveryDetailsViewModel

    companion object {
        const val ARGS_DELIVERY = "_delivery"
    }

    var googleMap: GoogleMap? = null
    var delivery: Delivery? = null

    override val layoutRes = R.layout.fragment_delivery_details

    override fun onCreated(savedInstance: Bundle?) {
        appComponent.inject(this)

        baseActivity.setToolbar(true, true, "Delivery Details", false)

        deliveryDetailsViewModel = viewModel(viewModelFactory){
            failure(failure, ::handleFailure)
            observe(deliveryDetailsUi, ::handleDeliveryDetailsUi)
        }

        initializeMap()
        getArgs()

        delivery?.let{
            initializeViews()
            deliveryDetailsViewModel.getDeliveryLocation(it.id)
        }
    }

    private fun initializeViews() {
        ivDeliveryImage.setImageUrl(delivery!!.imageUrl)
        tvDescription.text = delivery!!.description
    }

    private fun getArgs() {
        arguments?.let{args ->
            if(args.containsKey(ARGS_DELIVERY))
                delivery = Gson().fromJson(args.getString(ARGS_DELIVERY), Delivery::class.java)
            else
                findNavController().navigateUp()
        }
    }

    private fun initializeMap() {
        val mapFragment = childFragmentManager!!
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun handleDeliveryDetailsUi(view: DeliveryDetailsView?){
        view?.let{deliveryDetailsView ->
            deliveryDetailsView.location?.let{ location ->
                showMarker(location)
            }
        }
    }

    private fun showMarker(location: Location){
        googleMap?.let{
            it.addMarker(MarkerOptions().position(LatLng(location.lat, location.lng)).title(delivery!!.description))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.lat, location.lng), 17f))
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        this.googleMap = p0
    }
}
