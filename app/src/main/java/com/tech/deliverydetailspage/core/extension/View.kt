package com.tech.deliverydetailspage.core.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)!!

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: androidx.fragment.app.FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(
        this,
        activity
    )
    Glide.with(context.applicationContext).load(url).into(target)
}

fun View.loadResourceIntoBackgroundView(@DrawableRes resource: Int) {
    Glide.with(context.applicationContext).load(resource).into(object : SimpleTarget<Drawable>() {
        override fun onResourceReady(drawable: Drawable, transition: Transition<in Drawable>?) {
            this@loadResourceIntoBackgroundView.background = drawable
        }
    })
}

fun ImageView.loadResourceIntoImageView(@DrawableRes resource: Int) {
    Glide.with(context.applicationContext).load(resource).into(this)
}

fun SubsamplingScaleImageView.setImage(mapURL: String?, width: Int, height: Int) {
    Glide.with(context)
        .asBitmap()
        .load(mapURL!!)
        .into(object : SimpleTarget<Bitmap>(width, height) {
            override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                val savedImagePath = saveImage(resource!!, mapURL, context)
                if (savedImagePath.isNullOrEmpty()) {
                    //  notify(R.string.failure_download_error)
                } else {
                    this@setImage.setImage(ImageSource.uri(savedImagePath))
                }
            }
        })
}

private fun saveImage(image: Bitmap, mapURL: String, context: Context?): String? {
    var savedImagePath: String? = null

    val imageFileName = URL(mapURL).file.replace("/", "")
    val directory = context?.cacheDir?.absolutePath
    val storageDir =
        File("$directory/maps")
    var success = true
    if (!storageDir.exists()) {
        success = storageDir.mkdirs()
    }
    if (success) {
        val imageFile = File(storageDir, imageFileName)
        savedImagePath = imageFile.absolutePath
        try {
            val fOut = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return savedImagePath
}

private class ImageViewBaseTarget(var imageView: ImageView?, var activity: androidx.fragment.app.FragmentActivity?) :
    BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback?) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>) {
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}
