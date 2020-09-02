package com.chukimuoi.tikitest.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.chukimuoi.tikitest.TikiApplication
import com.chukimuoi.tikitest.ui.main.MainViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val injector = (application as TikiApplication).injector

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }
}