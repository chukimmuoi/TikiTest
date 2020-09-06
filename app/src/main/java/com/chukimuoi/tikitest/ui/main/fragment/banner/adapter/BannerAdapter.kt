package com.chukimuoi.tikitest.ui.main.fragment.banner.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chukimmuoi.mbase.fragment.pager.BaseFragmentPagerAdapter
import com.chukimuoi.tikitest.ui.main.fragment.banner.BannerPageFragment

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/5/20.
 */
class BannerAdapter (private val fragmentManager: FragmentManager,
                     private val list: List<String>)
    : BaseFragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val linkImage = list[position]
        return BannerPageFragment.newInstance(linkImage)
    }

    override fun getCount() = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        return "$position"
    }
}