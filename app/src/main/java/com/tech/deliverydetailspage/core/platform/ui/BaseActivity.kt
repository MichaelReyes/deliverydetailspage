package com.tech.deliverydetailspage.core.platform.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.tech.deliverydetailspage.MyApplication
import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.di.component.ApplicationComponent

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var binding: V

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as MyApplication).appComponent
    }

    val loadingDialog: LoadingDialog by lazy(mode = LazyThreadSafetyMode.NONE) {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)

        onCreated(savedInstanceState)
    }

    protected abstract fun onCreated(instance: Bundle?)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog?.let {
            if (isLoading && !loadingDialog.isShowing)
                loadingDialog.show()
            else if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }

    fun showMessage(message: String, positive: Boolean) {
        var snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

        val sbView = snackbar.view
        if (!positive) {
            sbView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        } else
            sbView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))

        val textValue = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)

        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    fun goToActivity(currentActivity: Activity, mClass: Class<*>, finishCurrentActivity: Boolean) {
        val clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN
        val intent = Intent(currentActivity, mClass)
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        currentActivity.startActivity(intent)
        if (finishCurrentActivity)
            currentActivity.finish()

    }

    fun goToActivity(currentActivity: Activity, mClass: Class<*>, finishCurrentActivity: Boolean, extras: Bundle) {
        val clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN
        val intent = Intent(currentActivity, mClass)
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        intent.putExtras(extras)
        currentActivity.startActivity(intent)
        if (finishCurrentActivity)
            currentActivity.finish()

    }

    fun goToActivity(currentActivity: Activity, mClass: Class<*>, finishCurrentActivity: Boolean, extras: Bundle,
                     withResult: Boolean, requestCode: Int) {
        val clearTask = Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN
        val intent = Intent(currentActivity, mClass)
        if (clearTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        intent.putExtras(extras)
        if (!withResult)
            currentActivity.startActivity(intent)
        else
            currentActivity.startActivityForResult(intent, requestCode)

        if (finishCurrentActivity)
            currentActivity.finish()

    }

    fun setToolbar(show: Boolean, showBackButton: Boolean, title: String, showIcon: Boolean
    ) {
        val actionBar = supportActionBar

        if (actionBar != null) {
            if (show) {
                actionBar.show()

                if (showIcon) {
                    actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
                    actionBar.setCustomView(R.layout.appbar_icon)
                }else{
                    actionBar.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
                }

                actionBar.setHomeButtonEnabled(showBackButton)
                actionBar.setDisplayHomeAsUpEnabled(showBackButton)

                if(title != "") {
                    actionBar.setDisplayShowTitleEnabled(true)
                    actionBar.title = title
                }else
                    actionBar.setDisplayShowTitleEnabled(false)

            } else
                actionBar.hide()
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    fun showMessageDialog(header: String, body: String, positiveLabel: String, negativeLabel: String,
                          callback: MessageDialog.Callback) {

        var args = Bundle()
        args.putString(MessageDialog.ARGS_MESSAGE_HEADER, header)
        args.putString(MessageDialog.ARGS_MESSAGE_BODY, body)
        if (positiveLabel.isNotEmpty())
            args.putString(MessageDialog.ARGS_MESSAGE_POSITIVE_ACTION, positiveLabel)
        if (negativeLabel.isNotEmpty())
            args.putString(MessageDialog.ARGS_MESSAGE_NEGATIVE_ACTION, negativeLabel)

        MessageDialog.newInstance(args, callback).show(supportFragmentManager!!, MessageDialog::class.java.simpleName)
    }
}