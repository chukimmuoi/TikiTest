package com.chukimuoi.tikitest.ui.main.fragment.home.adapter

import android.app.Application
import com.chukimmuoi.mbase.recycle.adapter.BaseRecycleAdapter
import com.chukimuoi.tikitest.model.QuickLink
import com.chukimuoi.tikitest.ui.main.fragment.home.adapter.delegate.QuickLinkDelegate
import javax.inject.Inject

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/6/20.
 */
class QuickLinkAdapter
@Inject constructor(private val application: Application)
    : BaseRecycleAdapter() {

    fun setListener(onClick: (QuickLink.Data) -> Unit) {

        delegatesManager.addDelegate(QuickLinkDelegate(application, onClick))
    }
}