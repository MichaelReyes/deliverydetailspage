package com.tech.deliverydetailspage.core.platform.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.tech.deliverydetailspage.R

import java.io.File

class GlideImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    AppCompatImageView(context, attrs, defStyle) {

    fun setImageUrl(imageUrl: String?) {
        if (imageUrl == null)
            return

        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.img_placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }

    fun setImageFile(file: File) {
        Glide.with(context)
            .load(file)
            .apply(RequestOptions().placeholder(R.drawable.img_placeholder))
            .into(this)
    }

    fun setImageDrawable(drawable: Drawable, placeholder: Drawable) {
        Glide.with(context)
            .load(drawable)
            .apply(RequestOptions().placeholder(placeholder))
            .into(this)
    }

    fun setImageBitmap(bitmap: Bitmap, placeholder: Drawable) {
        Glide.with(context)
            .load(bitmap)
            .apply(RequestOptions().placeholder(placeholder))
            .into(this)
    }

}
