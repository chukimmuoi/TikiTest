package com.chukimmuoi.mbase.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chukimmuoi.mbase.fragment.BaseFragment

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2020-01-16.
 */
interface ActivityView {

    fun createVariableReload(savedInstanceState: Bundle)

    /**
     * @param layoutId
     * @param fragmentManager
     *
     * @return Fragment or null
     * */
    fun findingFragment(@IdRes layoutId: Int, fragmentManager: FragmentManager) : Fragment?

    /**
     * @param layoutId
     *
     * @return
     * */
    fun findingFragment(@IdRes layoutId: Int) : Fragment?

    /**
     * @param tag
     * @param fragmentManager
     *
     * @return Fragment or null
     * */
    fun findingFragment(tag: String, fragmentManager: FragmentManager) : Fragment?

    /**
     * @param tag
     *
     * @return
     * */
    fun findingFragment(tag: String) : Fragment?

    /**
     * Them mot fragment vao list fragment
     *
     * @param fragment
     * @param isRefresh
     *
     * @return true (add thanh cong) or false(fragment khong duoc add)
     * */
    fun addFragment(fragment: BaseFragment, isRefresh: Boolean = false) : Boolean

    /**
     * Them mot list fragment vao danh sach
     *
     * @param fragments
     * @param isRefresh
     *
     * @return Int so luong fragment duoc add thanh cong
     * */
    fun addFragment(fragments: List<BaseFragment>, isRefresh: Boolean = false) : Int

    fun displayFragment(@IdRes layoutContainer: Int, fragmentManager: FragmentManager,
        tag: String, bundle: Bundle, isSaveCache: Boolean = false)

    fun displayFragment(@IdRes layoutContainer: Int, tag: String, bundle: Bundle, isSaveCache: Boolean = false)
    /**
     * Hien thi fragment
     * <p>
     * @param layoutContainer la id cua FrameLayout chua fragment
     * @param fragmentManager bien quan ly fragment cua activity
     * @param tag ten cua fragment ClassFragment::class.java.canonicalName
     * @param tagParent ten cua fragment cha ClassFragment::class.java.canonicalName
     * @param bundle du lieu duoc truyen theo fragment (first data)
     * */
    fun displayMultiFragment(@IdRes layoutContainer: Int, fragmentManager: FragmentManager,
        tag: String, tagParent: String,
        bundle: Bundle)
    /**
     * Hien thi fragment
     * <p>
     * @param layoutContainer
     * @param tag
     * @param tagParent
     * @param bundle
     * */
    fun displayMultiFragment(@IdRes layoutContainer: Int,
        tag: String, tagParent: String,
        bundle: Bundle)

    fun clearAllFragment()

    /**
     *
     * */
    fun backStackFragment() : Boolean

    fun onBackPressedNow()

    /**
     *
     * <p>
     * @param activityOld activity cu
     * @param intent intent activityOld -> activityNew
     * @param isFinish true: Finish activity cu
     * */
    fun goToActivity(activityOld: Activity, intent: Intent, isFinish: Boolean = false)

    /**
     * Animation when change activity go ...
     * */
    fun overridePendingSlideFromRightToLeft()

    /**
     * Animation when change fragment go ...
     * */
    fun overridePendingSlideFromRightToLeftFragment(fragmentTransaction: FragmentTransaction)

    /**
     * Animation when change activity back ...
     * */
    fun overridePendingSlideFromLeftToRight()

    /**
     * Animation when change fragment back ...
     * */
    fun overridePendingSlideFromLeftToRightFragment(fragmentTransaction: FragmentTransaction)

    /**
     * Set title cho action bar.
     * */
    fun setTitleActionBar(title: CharSequence)

    /**
     * Set subtitle cho action bar.
     * */
    fun setSubtitleActionBar(subtitle: CharSequence)

    /**
     * Set icon cho action bar.
     * */
    fun setIconActionBar(icon: Int)

    /**
     * Ẩn hiện icon home trên action bar (Icon cho phep trở về thay cho nút back)
     * */
    fun displayIconHomeActionbar(isShow: Boolean = true)

    fun <T> handlerMultiUIwh960(wh960: () -> T, default: () -> T): T
    fun <T> handlerMultiUIh960Default(w960: () -> T, h960default: () -> T): T
    fun <T> handlerMultiUI(w960: () -> T, h960: () -> T, default: () -> T): T
}