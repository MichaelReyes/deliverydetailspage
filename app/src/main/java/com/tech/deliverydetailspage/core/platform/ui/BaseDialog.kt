package com.tech.deliverydetailspage.core.platform.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.exception.Failure

abstract class BaseDialog: DialogFragment() {

    abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        // creating the fullscreen dialog
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

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

        notify(errorMessageId); dismiss()
    }

    internal fun notify(@StringRes message: Int) =
            Toast.makeText(activity, getString(message), Toast.LENGTH_LONG).show()
}