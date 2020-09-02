package com.chukimuoi.tikitest.data.repository

import com.chukimuoi.tikitest.data.online.remotedatasource.TikiDataSource
import javax.inject.Inject

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
class TikiRepository
@Inject constructor(
    private val dataSource: TikiDataSource){

    fun getBannerAndQuickLink() = dataSource.getBannerAndQuickLink()

    fun getFlashDeal() = dataSource.getFlashDeal()
}