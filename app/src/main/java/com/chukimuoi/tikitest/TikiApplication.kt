package com.chukimuoi.tikitest

import android.content.Context
import androidx.multidex.MultiDex
import com.chukimmuoi.mbase.MBaseApplication
import com.chukimuoi.tikitest.injection.component.DaggerViewModelInjector
import com.chukimuoi.tikitest.injection.component.ViewModelInjector
import com.chukimuoi.tikitest.injection.module.ApplicationModule
import com.chukimuoi.tikitest.injection.module.NetworkModule

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
class TikiApplication : MBaseApplication() {

    val injector: ViewModelInjector = DaggerViewModelInjector.builder()
        .applicationModule(ApplicationModule(this))
        .networkModule(NetworkModule)
        .build()


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        injector.inject(this)
    }
}