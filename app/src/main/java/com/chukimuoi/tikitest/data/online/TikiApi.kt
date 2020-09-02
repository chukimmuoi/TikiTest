package com.chukimuoi.tikitest.data.online

import com.chukimuoi.tikitest.model.Banner
import com.chukimuoi.tikitest.model.FlashDeal
import com.chukimuoi.tikitest.model.QuickLink
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
interface TikiApi {

    companion object {
        const val BASE_URL: String = "https://api.tiki.vn/"
    }

    @GET("v2/home/banners/v2")
    fun getBanner(): Observable<Banner>

    @GET("shopping/v2/widgets/quick_link")
    fun getQuickLink(): Observable<QuickLink>

    @GET("v2/widget/deals/hot")
    fun getFlashDeal(): Observable<FlashDeal>
}