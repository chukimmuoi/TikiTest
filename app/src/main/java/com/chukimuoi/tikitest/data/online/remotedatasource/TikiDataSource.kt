package com.chukimuoi.tikitest.data.online.remotedatasource

import com.chukimuoi.tikitest.data.online.TikiApi
import com.chukimuoi.tikitest.model.Banner
import com.chukimuoi.tikitest.model.FlashDeal
import com.chukimuoi.tikitest.model.QuickLink
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
class TikiDataSource
@Inject constructor(val api: TikiApi) {

    private fun getBanner() = api.getBanner().onErrorReturn {
        return@onErrorReturn Banner()
    }

    private fun getQuickLink() = api.getQuickLink().onErrorReturn {
        return@onErrorReturn QuickLink()
    }

    fun getFlashDeal() = api.getFlashDeal().onErrorReturn {
        return@onErrorReturn FlashDeal()
    }

    fun getBannerAndQuickLink() =
        Observables.zip(getBanner(), getQuickLink()) { banners, quicklinks ->
            Pair(banners, quicklinks)
        }
}