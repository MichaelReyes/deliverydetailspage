package com.tech.deliverydetailspage.features.delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tech.deliverydetailspage.R
import com.tech.deliverydetailspage.core.platform.ui.BaseActivity
import com.tech.deliverydetailspage.databinding.ActivityDeliveryBinding
import kotlinx.android.synthetic.main.activity_delivery.*

class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>() {

    override val layoutRes = R.layout.activity_delivery

    override fun onCreated(instance: Bundle?) {
        appComponent.inject(this)
        setSupportActionBar(toolbar)
    }
}
