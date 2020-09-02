package com.chukimmuoi.mbase.extensions

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chukimmuoi.mbase.activity.BaseActivity
import com.chukimmuoi.mbase.fragment.BaseFragment
import me.drakeet.support.toast.ToastCompat

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-24.
 */

/**
 * Kiểm tra kết nối internet của thiết bị.
 *
 * @return giá trị boolean có kết nối(true), không có kết nối (false).
 * */
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}

/**
 * Tắt bật BroadcastReceiver.
 *
 * @param componentClass: Tên class. eg: ClassName::class.java
 * @param enable trạng thái on(true) hay off(false)
 * */
fun Context.toggleAndroidComponent(componentClass: Class<*>, enable: Boolean) {
    val componentName = ComponentName(this, componentClass)

    val newState = if (enable)
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    else
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED

    packageManager.setComponentEnabledSetting(componentName, newState, PackageManager.DONT_KILL_APP)
}

fun BaseActivity.showToast(content: String, isLongTime: Boolean) {
    runOnUiThread {
        dismissToast()

        baseToast = ToastCompat.makeText(
            this,
            content,
            if (isLongTime) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
        baseToast?.let {
            it.setGravity(Gravity.BOTTOM, 0, 0)
            it.show()
        }
    }
}

fun BaseActivity.dismissToast() {
    baseToast?.cancel()
    baseToast = null
}

fun BaseActivity.showToast(@StringRes content: Int, isLongTime: Boolean) {
    showToast(getString(content), isLongTime)
}

fun BaseFragment.showToast(content: String, isLongTime: Boolean) {
    baseActivity.showToast(content, isLongTime)
}

fun BaseFragment.showToast(@StringRes content: Int, isLongTime: Boolean) {
    baseActivity.showToast(getString(content), isLongTime)
}

fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val className = serviceClass.name
    val manager = getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
    return manager.getRunningServices(Integer.MAX_VALUE).any { className == it.service.className }
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}