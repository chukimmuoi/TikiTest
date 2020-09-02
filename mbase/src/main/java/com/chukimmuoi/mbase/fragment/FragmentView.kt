package com.chukimmuoi.mbase.fragment

import android.view.KeyEvent

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2020-01-16.
 */
interface FragmentView {
    fun backToParentFragment()

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
}