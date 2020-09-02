package com.chukimuoi.tikitest.ui.main

import android.os.Bundle
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.ui.base.BaseTikiActivity
import com.chukimuoi.tikitest.utils.extensions.injectViewModel

class MainActivity : BaseTikiActivity() {

    lateinit var viewModel: MainViewModel

    override fun createLayout() {
        viewModel = injectViewModel()

        setContentView(R.layout.activity_main)
    }

    override fun createVariableStart(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {

        }
    }

    override fun createVariableView() {

    }

    override fun createVariableNormal() {

    }
}