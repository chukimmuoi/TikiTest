package com.chukimuoi.tikitest.ui.main.fragment.home.adapter

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.chukimuoi.tikitest.model.QuickLink
import com.chukimuoi.tikitest.ui.base.BaseViewModel

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/6/20.
 */
class ItemQuickLinkViewModel(application: Application) : BaseViewModel(application) {

    private val image = MutableLiveData<String>()
    private val title = MutableLiveData<String>()

    private val context: Context by lazy {
        application
    }

    fun bind(data: QuickLink.Data) {
        image.value = data.imageUrl
        title.value = data.title
    }

    fun getImage() = image
    fun getTitle() = title
}