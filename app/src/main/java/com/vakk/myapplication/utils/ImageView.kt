package com.vakk.myapplication.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(imageUri: String?, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
        .apply(RequestOptions.centerInsideTransform())
        .apply(RequestOptions.placeholderOf(placeholder))
        .apply(RequestOptions.errorOf(placeholder))
        .into(this)
}
fun ImageView.loadImage(imageUri: String?) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
        .apply(RequestOptions.centerInsideTransform())
        .into(this)
}