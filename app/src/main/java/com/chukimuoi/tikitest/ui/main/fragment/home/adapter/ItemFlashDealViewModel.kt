package com.chukimuoi.tikitest.ui.main.fragment.home.adapter

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.chukimuoi.tikitest.model.FlashDeal
import com.chukimuoi.tikitest.ui.base.BaseViewModel
import com.chukimuoi.tikitest.utils.extensions.formatPriceVND

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/6/20.
 */
class ItemFlashDealViewModel (application: Application) : BaseViewModel(application) {

    private val image = MutableLiveData<String>()
    private val price = MutableLiveData<String>()
    private val discount = MutableLiveData<String>()
    private val stateOrder = MutableLiveData<String>()

    private val context: Context by lazy {
        application
    }

    fun bind(data: FlashDeal.Data) {
        image.value    = data.product.thumbnailUrl
        price.value    = data.product.price.formatPriceVND()
        discount.value = data.product.getPercentDiscount()
        stateOrder.value = data.progress.getOrdered(context)
    }

    fun getImage() = image
    fun getPrice() = price
    fun getDiscount() = discount
    fun getStateOrder() = stateOrder
}