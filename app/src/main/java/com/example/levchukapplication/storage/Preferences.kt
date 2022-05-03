package com.example.levchukapplication.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.preference.PreferenceManager.getDefaultSharedPreferences

class Preferences(context: Context) {
    private val p: SharedPreferences = getDefaultSharedPreferences(context)


    companion object {
        private const val savedCustomStringsKeysListKey = "savedCustomStringsKeysListKey"
        private const val dbBackgroundColorKey = "dbBackgroundColorKey"
        private const val dbFontColorKey = "dbFontColorKey"
    }


    private fun getSavedCustomStringsKeysList(): Set<String> {
        return p.getStringSet(savedCustomStringsKeysListKey, setOf()) ?: setOf()
    }

    fun getSavedCustomStringsMap(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        getSavedCustomStringsKeysList().forEach { key ->
            val value = p.getString(key, "") ?: ""
            result[key] = value
        }
        return result
    }


    private fun saveCustomStringsKeysList(keys: Set<String>) {
        p.edit().putStringSet(savedCustomStringsKeysListKey, keys).apply()
    }

    fun saveCustomString(key: String, value: String) {
        val keys = getSavedCustomStringsKeysList()
        saveCustomStringsKeysList(keys.plus(key))
        p.edit().putString(key, value).apply()
    }

    fun deleteSavedCustomString(key: String) {
        val keys = getSavedCustomStringsKeysList()
        saveCustomStringsKeysList(keys.minus(key))
        p.edit().putString(key, null).apply()
    }

    fun saveDbBackgroundColor(color: Color) {
        p.edit().putInt(dbBackgroundColorKey, color.toArgb()).apply()
    }

    fun saveDbFontColor(color: Color) {
        p.edit().putInt(dbFontColorKey, color.toArgb()).apply()
    }

    fun getDbBackgroundColor(defaultColor: Color): Color {
        val savedColorARGB = p.getInt(dbBackgroundColorKey, -1)
        return if (savedColorARGB == -1) defaultColor else Color(savedColorARGB)
    }

    fun getDbFontColor(defaultColor: Color): Color {
        val savedColorARGB = p.getInt(dbFontColorKey, -1)
        return if (savedColorARGB == -1) defaultColor else Color(savedColorARGB)
    }


}