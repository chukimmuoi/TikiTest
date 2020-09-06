package com.chukimuoi.tikitest.utils.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/6/20.
 */
fun Int.formatPriceVND(): String {
    val nf = NumberFormat.getNumberInstance(Locale.US)
    val formatter = nf as DecimalFormat
    formatter.applyPattern("###,###,### đ")
    return formatter.format(this)
}