package com.chukimmuoi.mbase.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-10.
 */
object DelegatesExt {
    fun <T> preference(context: Context, name: String, default: T)
            = Preference(context, name, default)
}

class Preference<T>(private val context: Context,
                    private val name: String,
                    private val default: T): ReadWriteProperty<Any?, T> {

    companion object {
        const val PREF_NAME = "cache_data_setting"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Long    -> getLong(name,    default)
            is String  -> getString(name,  default)
            is Int     -> getInt(name,     default)
            is Boolean -> getBoolean(name, default)
            is Float   -> getFloat(name,   default)
            else       -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
        @Suppress("UNCHECKED_CAST")
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putPreference(name: String, value: T) =
        prefs.edit {
            when (value) {
                is Long    -> putLong(name,    value)
                is String  -> putString(name,  value)
                is Int     -> putInt(name,     value)
                is Boolean -> putBoolean(name, value)
                is Float   -> putFloat(name,   value)
                else       -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
    }
}