package com.chukimmuoi.mbase.swipe

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chukimmuoi.mbase.R

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
class BaseSwipeRefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setColorSchemeResources(
                R.color.colorSwipeBlue,
                R.color.colorSwipeGreen,
                R.color.colorSwipeOrange,
                R.color.colorSwipeRed)
    }
}