package com.tech.deliverydetailspage.core.platform.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tech.deliverydetailspage.MyApplication
import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.data.preference.AppPreference
import com.tech.deliverydetailspage.core.di.component.ApplicationComponent
import com.tech.deliverydetailspage.core.exception.Failure
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding>: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appPreference: AppPreference

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var baseView: View

    protected lateinit var binding: V

    val baseActivity: BaseActivity<*> by lazy (mode = LazyThreadSafetyMode.NONE){
        activity as BaseActivity<*>
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as MyApplication).appComponent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        baseView = binding.root
        return baseView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
    }

    protected abstract fun onCreated(savedInstance: Bundle?)

    @CallSuper
    protected open fun handleFailure(failure: Failure?) {
        var errorMessageId = R.string.failure_generic_error

        when (failure) {
            is Failure.NetworkConnection -> {
                errorMessageId = R.string.failure_network_connection
            }
            is Failure.ServerError -> {
                errorMessageId = R.string.failure_server_error
            }
            is Failure.GenericError -> {
                errorMessageId = R.string.failure_generic_error
            }
            is Failure.RequestError -> {
                errorMessageId = R.string.failure_request_error
            }
            is Failure.UnauthorizedError -> {
                errorMessageId = R.string.failure_unauthorized_error
            }
        }

        showMessage(getString(errorMessageId), false)
    }

    protected fun showMessage(message: String, positive: Boolean){
        activity?.let {
            (it as BaseActivity<*>).showMessage(message, false)
        }
    }
}