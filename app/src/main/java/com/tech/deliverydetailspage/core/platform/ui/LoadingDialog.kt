package com.tech.deliverydetailspage.core.platform.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.tech.deliverydetailspage.R

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }

}