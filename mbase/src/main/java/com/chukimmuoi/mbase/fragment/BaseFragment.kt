package com.chukimmuoi.mbase.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chukimmuoi.mbase.activity.BaseActivity
import timber.log.Timber

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-10.
 */
abstract class BaseFragment : Fragment(), FragmentView {

    companion object {
        const val BUNDLE_FRAGMENT_PARENT_TAG = "bundle_fragment_parent_tag"
    }

    lateinit var baseActivity: BaseActivity

    private var mFragmentParentTag = ""

    /**
     * 1. Call when Fragment connect Activity. [onDetach]
     *
     * @param context
     */
    override fun onAttach(context: Context) {
        Timber.i("onAttach")
        super.onAttach(context)

        baseActivity = activity as BaseActivity

        injection()
    }

    /**
     * 2. Use onCreate variable not UI. [onDestroy]
     * eg: context, adapter, arrayList
     *
     * @param savedInstanceState bien luu trang thai truoc do, dung khi muon khoi phuc lai.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(BUNDLE_FRAGMENT_PARENT_TAG)?.let {
                mFragmentParentTag = it
            }
            createVariableStart(it)
        }
        savedInstanceState?.let { createVariableReload(it) }
    }

    /**
     * 3. Set layout XML. [onDestroyView]
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")
        return bindingData(inflater, container!!, savedInstanceState)
    }

    /**
     * 4. Set variable UI.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createVariableView(view, savedInstanceState)
    }

    /**
     * 5. Call when Activity complete method. [onCreate]
     *
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.i("onActivityCreated")
        super.onActivityCreated(savedInstanceState)

        createVariableNormal(savedInstanceState)
    }

    /**
     * 5. Call when fragment ready show on screen. [onStop]
     */
    override fun onStart() {
        Timber.i("onStart")
        super.onStart()
    }

    /**
     * 6. Handle resources. Multi screen. [onPause]
     */
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

    override fun onDestroyView() {
        Timber.i("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.i("onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.i("onDetach")
        super.onDetach()
    }

    abstract fun injection()

    /**
     * Noi khai bao cac bien duoc truyen tu activity hoac fragment khac theo dang arguments
     *
     * @param bundle bien nay luu cac gia tri duoc truyen co the la string, int, boolean, ...
     * */
    abstract fun createVariableStart(bundle: Bundle)

    /**
     * Noi khai bao cac bien duoc truyen tu chinh no tu onSaveInstanceState
     *
     * @param bundle bien nay luu cac gia tri duoc truyen co the la string, int, boolean, ...
     * */
    abstract fun createVariableReload(bundle: Bundle)

    /**
     * Add layout hien thi cho fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState dung de khoi phuc lai du lieu truoc do
     *
     * @return view or null
     * */
    abstract fun bindingData(inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?): View

    /**
     * Noi khai bao, xu ly cac bien lien quan den UI nhu button, edit text, text view, ...
     * */
    abstract fun createVariableView(view: View?, savedInstanceState: Bundle?)

    /**
     * Noi khai bao cac bien cac bien thong thuong de xu ly tinh toan ma khong phai UI
     *
     * Luu y: Cho phep su dung menu hay khong se duoc khai bao o day
     * */
    abstract fun createVariableNormal(savedInstanceState: Bundle?)

    override fun backToParentFragment() {
        with(baseActivity) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            overridePendingSlideFromLeftToRightFragment(fragmentTransaction)

            fragmentTransaction.remove(this@BaseFragment)

            val parentFragment = findingFragment(mFragmentParentTag, supportFragmentManager)
            parentFragment?.let {
                fragmentTransaction.show(parentFragment)
            }

            fragmentTransaction.commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }
}