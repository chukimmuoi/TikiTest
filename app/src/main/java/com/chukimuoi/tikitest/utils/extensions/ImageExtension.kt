package com.chukimuoi.tikitest.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chukimuoi.tikitest.injection.module.GlideApp

fun ImageView.loadBanner(url: String) {
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        //.placeholder(R.drawable.ic_vinsmart_red_24dp)
        //.error(R.drawable.ic_vinsmart_red_24dp)
        .into(this)
}