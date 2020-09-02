package com.chukimuoi.tikitest.injection.module

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

//@see: http://bumptech.github.io/glide/doc/configuration.html#application-options
// https://bumptech.github.io/glide/doc/configuration.html#disk-cache
@GlideModule
class AppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.VERBOSE)

        val memoryCacheSizeBytes: Long = 1024 * 1024 * 20 // 20 MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))

        val bitmapPoolSizeBytes: Long = 1024 * 1024 * 30 // 30 MB
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes))

        val diskCacheSizeBytes: Long = 1024 * 1024 * 100 // 100 MB
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes))
    }
}