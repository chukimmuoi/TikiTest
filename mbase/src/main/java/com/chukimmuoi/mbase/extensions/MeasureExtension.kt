package com.chukimmuoi.mbase.extensions

import android.content.res.Resources
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-24.
 */
// https://stackoverflow.com/questions/4605527/converting-pixels-to-dp#12147550

/**
 * Converts a pixel value to a density independent pixels (DPs).
 *
 * @param resources A reference to the [Resources] in the current context.
 * @return The value of {@code pixels} in DPs.
 */
fun Float.convertPixelToDp(resources: Resources) : Float {
    return this / resources.displayMetrics.density
}

/**
 * Converts a density independent pixels (DPs) value to a pixel.
 *
 * @param resources A reference to the [Resources] in the current context.
 * @return The value of {@code Dps} in pixels.
 */
fun Float.convertDpToPixel(resources: Resources) : Float {
    return this * resources.displayMetrics.density
}

/**
 * Converts a pixel value to a density independent pixels (DPs).
 *
 * @param resources A reference to the [Resources] in the current context.
 * @return The value of {@code pixels} in DPs.
 */
fun Int.convertPixelToDp(resources: Resources) : Int {
    return (this / resources.displayMetrics.density).roundToInt()
}

/**
 * Converts a density independent pixels (DPs) value to a pixel.
 *
 * @param resources A reference to the [Resources] in the current context.
 * @return The value of {@code Dps} in pixels.
 */
fun Int.convertDpToPixel(resources: Resources) : Int{
    return (this * resources.displayMetrics.density).roundToInt()
}

fun Int.getSpaceCountAndWidthInGrid(resources: Resources, percent: Float) : Triple<Int, Int, Int> {
    var output = Triple(0, 0, 0)
    try {
        val widthItem = resources.getDimensionPixelSize(this)

        val widthFullScreen = resources.displayMetrics.widthPixels
        val widthUseCalculator = widthFullScreen * percent
        val spanCount = (widthUseCalculator / widthItem).toInt()
        val space = ((widthUseCalculator - spanCount * widthItem) / (spanCount + 1) / 2).toInt()
        output = Triple(space, spanCount, widthItem)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return output
}

fun Resources.getScreenType(): String {
    val density = this.displayMetrics.density
    val densityDpi = this.displayMetrics.densityDpi
    var output = "density = $density, densityDpi = $densityDpi"

    when(density) {
        0.75F -> output = "ldpi"
        1.0F -> output = "mdpi"
        1.5F -> output = "hdpi"
        2.0F -> output = "xhdpi"
        3.0F -> output = "xxhdpi"
        4.0F -> output = "xxxhdpi"
    }
    Timber.e("Screen: $output")
    return output
}