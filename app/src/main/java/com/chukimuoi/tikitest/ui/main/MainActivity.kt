package com.chukimuoi.tikitest.ui.main

import android.os.Bundle
import com.chukimmuoi.mbase.extensions.onClickDebounce
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.ui.base.BaseTikiActivity
import com.chukimuoi.tikitest.ui.main.fragment.home.HomeFragment
import com.chukimuoi.tikitest.utils.extensions.injectViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseTikiActivity() {

    lateinit var viewModel: MainViewModel

    override fun createLayout() {
        viewModel = injectViewModel()

        setContentView(R.layout.activity_main)
    }

    override fun createVariableStart(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            displayHomeFragment()
        }
    }

    override fun createVariableView() {

        imageHome.onClickDebounce {
            handlerStateClickImageBottomBar(imageHome.id)
        }

        imageCategory.onClickDebounce {
            handlerStateClickImageBottomBar(imageCategory.id)
        }

        imageSearch.onClickDebounce {
            handlerStateClickImageBottomBar(imageSearch.id)
        }

        imageNotification.onClickDebounce {
            handlerStateClickImageBottomBar(imageNotification.id)
        }

        imageAccount.onClickDebounce {
            handlerStateClickImageBottomBar(imageAccount.id)
        }
    }

    override fun createVariableNormal() {

    }

    private fun handlerStateClickImageBottomBar(id: Int) {
        imageHome.isSelected         = false
        imageCategory.isSelected     = false
        imageSearch.isSelected       = false
        imageNotification.isSelected = false
        imageAccount.isSelected      = false

        when(id) {
            imageHome.id         -> imageHome.isSelected         = true
            imageCategory.id     -> imageCategory.isSelected     = true
            imageSearch.id       -> imageSearch.isSelected       = true
            imageNotification.id -> imageNotification.isSelected = true
            imageAccount.id      -> imageAccount.isSelected      = true
        }
    }

    private fun displayHomeFragment() {
        handlerStateClickImageBottomBar(imageHome.id)

        val bundle = Bundle()

        displayFragment(
            layoutContainer = R.id.layoutContainFragment,
            tag = HomeFragment::class.java.name,
            bundle = bundle,
            isSaveCache = false)
    }
}