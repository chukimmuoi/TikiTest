package com.chukimmuoi.mbase.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chukimmuoi.mbase.R
import com.chukimmuoi.mbase.extensions.hideKeyboard
import com.chukimmuoi.mbase.fragment.BaseFragment
import me.drakeet.support.toast.ToastCompat
import timber.log.Timber

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-10.
 */
@Keep
abstract class BaseActivity : AppCompatActivity(), ActivityView {

    private val mListFragment = mutableListOf<BaseFragment>()
    var countCacheFragment = 1 // Ap dung khi dung view pager, se co nhieu framgment.

    var baseToast: ToastCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)

        createLayout()
        createVariableStart(savedInstanceState)
        savedInstanceState?.let {
            createVariableReload(it)
        }
        createVariableView()
        createVariableNormal()
    }

    override fun onStart() {
        Timber.i("onStart")
        super.onStart()
    }

    override fun onResume() {
        Timber.i("onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.i("onStop")
        super.onStop()
    }

    override fun onRestart() {
        Timber.i("onRestart")
        super.onRestart()
    }

    override fun onDestroy() {
        Timber.i("onDestroy")
        baseToast = null

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Luu lai danh sach fragment.
        for (fragment in mListFragment) {
            if (fragment.isAdded) {
                fragment.tag?.let {
                    supportFragmentManager.putFragment(outState, it, fragment)
                }
            }
        }
    }

    /**
     * Thiet lap layout XML cho activity.
     * */
    abstract fun createLayout()

    /**
     * Noi khai bao cac bien duoc truyen tu activity hoac fragment khac
     *
     * @param savedInstanceState bien nay luu cac gia tri duoc truyen co the la string, int, boolean, ...
     * */
    abstract fun createVariableStart(savedInstanceState: Bundle?)

    /**
     * Noi nhan va khoi phuc cac bien duoc truyen tu onSaveInstanceState()
     * </p>
     * @param savedInstanceState
     * */
    override fun createVariableReload(savedInstanceState: Bundle) {

    }

    /**
     * Noi khai bao, xu ly cac bien lien quan den UI nhu button, edit text, text view, ...
     * */
    abstract fun createVariableView()

    /**
     * Noi khai bao cac bien cac bien thong thuong de xu ly tinh toan ma khong phai UI
     *
     * Luu y: Cho phep su dung menu hay khong se duoc khai bao o day
     * */
    abstract fun createVariableNormal()

    //============================================FRAGMENT========================================//
    override fun findingFragment(layoutId: Int, fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentById(layoutId)
    }

    override fun findingFragment(layoutId: Int): Fragment? {
        return findingFragment(layoutId, supportFragmentManager)
    }

    override fun findingFragment(tag: String, fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag(tag)
    }

    override fun findingFragment(tag: String): Fragment? {
        return findingFragment(tag, supportFragmentManager)
    }

    override fun addFragment(fragment: BaseFragment, isRefresh: Boolean): Boolean {
        synchronized(mListFragment) {
            if (isRefresh) mListFragment.clear()

            val fistSize = mListFragment.size

            mListFragment.add(fragment)

            val lastSize = mListFragment.size

            return lastSize > fistSize
        }
    }

    override fun addFragment(fragments: List<BaseFragment>, isRefresh: Boolean): Int {
        var output = 0

        for (fragment in fragments) {
            if (addFragment(fragment, isRefresh)) {
                output += 1
            }
        }

        return output
    }

    override fun displayFragment(
        layoutContainer: Int,
        fragmentManager: FragmentManager,
        tag: String,
        bundle: Bundle,
        isSaveCache: Boolean
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        overridePendingSlideFromRightToLeftFragment(fragmentTransaction)
        val fragment = Fragment.instantiate(this@BaseActivity, tag, bundle) as BaseFragment

        addFragment(fragment = fragment, isRefresh = true)
        fragmentTransaction.replace(layoutContainer, fragment, tag)

        if (isSaveCache) fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commitNowAllowingStateLoss()
    }

    override fun displayFragment(
        layoutContainer: Int,
        tag: String,
        bundle: Bundle,
        isSaveCache: Boolean
    ) {
        displayFragment(layoutContainer, supportFragmentManager, tag, bundle, isSaveCache)
    }

    // https://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
    // https://stackoverflow.com/questions/33237235/remove-all-fragments-from-container
    override fun displayMultiFragment(
        layoutContainer: Int,
        fragmentManager: FragmentManager,
        tag: String,
        tagParent: String,
        bundle: Bundle
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        overridePendingSlideFromRightToLeftFragment(fragmentTransaction)

        if (tagParent.isNotEmpty()) {
            findingFragment(tagParent, fragmentManager)?.let {
                bundle.putString(BaseFragment.BUNDLE_FRAGMENT_PARENT_TAG, tagParent)
                fragmentTransaction.hide(it)
            }
        }

        if (tag.isNotEmpty()) {
            val fragment = findingFragment(tag, fragmentManager)
            if (fragment != null) {
                if (fragment.isAdded) {
                    fragmentTransaction.show(fragment)
                }
            } else {
                val fragment = Fragment.instantiate(this@BaseActivity, tag, bundle) as BaseFragment
                addFragment(fragment)
                fragmentTransaction.add(layoutContainer, fragment, tag)
            }
        }

        fragmentTransaction.commit()
    }

    override fun displayMultiFragment(
        layoutContainer: Int,
        tag: String,
        tagParent: String,
        bundle: Bundle
    ) {
        displayMultiFragment(layoutContainer, supportFragmentManager, tag, tagParent, bundle)
    }

    override fun clearAllFragment() {
        //Here we are clearing back stack fragment entries
        val backStackEntry = supportFragmentManager.backStackEntryCount
        if (backStackEntry > 0) {
            for (i in 0 until backStackEntry) {
                supportFragmentManager.popBackStackImmediate()
            }
        }

        //Here we are removing all the fragment that are shown here
        if (supportFragmentManager.fragments != null && supportFragmentManager.fragments.size > 0) {
            for (i in supportFragmentManager.fragments.indices) {
                val mFragment: Fragment = supportFragmentManager.fragments[i]
                if (mFragment != null) {
                    supportFragmentManager.beginTransaction().remove(mFragment).commit()
                }
            }
        }

        mListFragment.clear()
    }

    override fun backStackFragment(): Boolean {
        val size = mListFragment.size
        if (size > 0) {
            synchronized(mListFragment) {
                mListFragment.removeAt(size - 1)?.let {
                    it.backToParentFragment()
                    return true
                }
            }
        }

        return false
    }

    override fun onBackPressed() {
        if (mListFragment.size <= countCacheFragment) {
            super.onBackPressed()
            overridePendingSlideFromLeftToRight()
        } else {
            backStackFragment()
        }
        hideKeyboard()
    }

    override fun onBackPressedNow() {
        super.onBackPressed()
        overridePendingSlideFromLeftToRight()
        hideKeyboard()
    }
    //============================================================================================//

    //=================================ANIMATION WHEN CHANGE ACTIVITY=============================//
    override fun goToActivity(activityOld: Activity, intent: Intent, isFinish: Boolean) {
        startActivity(intent).apply {
            overridePendingSlideFromRightToLeft()
            if (isFinish) activityOld.finish()
        }
    }

    override fun overridePendingSlideFromRightToLeft() {
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_to_left
        )
    }

    override fun overridePendingSlideFromRightToLeftFragment(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_from_right,
            R.anim.slide_to_left,
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }

    override fun overridePendingSlideFromLeftToRight() {
        overridePendingTransition(
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }

    override fun overridePendingSlideFromLeftToRightFragment(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_from_left,
            R.anim.slide_to_right,
            R.anim.slide_from_right,
            R.anim.slide_to_left
        )
    }

    override fun finish() {
        super.finish()
        overridePendingSlideFromRightToLeft()
    }
    //============================================================================================//

    //==========================================ACTION BAR========================================//
    override fun setTitleActionBar(title: CharSequence) {
        supportActionBar?.title = title
    }

    override fun setSubtitleActionBar(subtitle: CharSequence) {
        supportActionBar?.subtitle = subtitle
    }

    override fun setIconActionBar(icon: Int) {
        supportActionBar?.setIcon(icon)
    }

    override fun displayIconHomeActionbar(isShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        supportActionBar?.setDisplayShowHomeEnabled(isShow)
        supportActionBar?.setHomeButtonEnabled(isShow)
    }
    //============================================================================================//

    //========================================MULTI UI============================================//
    override fun <T> handlerMultiUIwh960(wh960: () -> T, default: () -> T): T {
        return handlerMultiUI(wh960, wh960, default)
    }

    override fun <T> handlerMultiUIh960Default(w960: () -> T, h960default: () -> T): T {
        return handlerMultiUI(w960, h960default, h960default)
    }

    override fun <T> handlerMultiUI(w960: () -> T, h960: () -> T, default: () -> T): T {
        return when(resources.getInteger(R.integer.type_ui_screen)) {
            0 -> default()
            1 -> h960()
            2 -> w960()
            else -> default()
        }
    }
    //============================================================================================//
}