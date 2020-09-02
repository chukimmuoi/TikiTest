package com.vinsmart.vinbrowserround3.data.cache

import android.content.Context
import androidx.core.content.edit
import com.chukimmuoi.mbase.extensions.Preference

class PreferenceSettings (private val context: Context) {

    companion object {

    }


    fun clear(prefName: String = Preference.PREF_NAME) {
        val prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        prefs.edit(true) { clear() }
    }
}