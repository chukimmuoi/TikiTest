package com.chukimmuoi.mbase

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.multidex.MultiDex
import com.chukimmuoi.mbase.extensions.getScreenType
import com.chukimmuoi.mbase.log.DebugTree
import com.chukimmuoi.mbase.log.ReleaseTree
import timber.log.Timber

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
@Keep
open class MBaseApplication: Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Setup log.
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            resources.getScreenType()
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}