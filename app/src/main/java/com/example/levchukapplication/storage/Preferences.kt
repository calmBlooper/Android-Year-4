package com.example.levchukapplication.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import java.io.File
import java.math.BigInteger

class Preferences(private val context: Context) {
    private val p: SharedPreferences = getDefaultSharedPreferences(context)


    companion object {
        private const val savedCustomStringsKeysListKey = "savedCustomStringsKeysListKey"
        private const val dbBackgroundColorKey = "dbBackgroundColorKey"
        private const val dbFontColorKey = "dbFontColorKey"
        private const val CAMERAS_DIR_V0: String = "MaksProDataForCameras"
        private const val CAMERAS_DIR_V1: String = "MaksProDataForCamerasVersion1"
        private const val CAMERAS_DIR_V2: String = "MaksProDataForCamerasVersion2"
        private const val TREE_MAP_CHILD: String = "TreeMapForCameras"
        private const val RTSP_CAMERAS_FILENAME: String = "RtspCamerasFile"
        private const val DAHUA_CAMERAS_FILENAME: String = "DahuaCamerasFile"
        private const val notificationVibrateKey = "notificationvibrate"
        private const val authTokenInfoKey = "authTokenInfoKey"
        private const val selectedPanel = "selectedPanel"
        private const val selectedGroup = "selectedGroup"
        private const val versionNameKey = "versionNameKey"
        private const val singleAppearanceKey = "singleAppearanceKey"
        private const val lastSessionKey = "lastSessionKey"
        private const val savedPanelsKey = "savedPanelsKey"
        private const val geofencePointKey = "geofencePointKey"
        private const val successfulCommandsCounterKey = "successfulCommandsCounterKey"
        private const val appLaunchesCounterKey = "appLaunchesCounterKey"
        private const val loggerWindowKey = "loggerWindowKey"
        private const val lastSavedUserInfoKey = "lastSavedUserInfoKey"
        private const val availableAccountServerKey = "availableAccountServerKey"
    }

    val hasActivation: Boolean
        get() = p.getBoolean("has_activation", false)


    val notificationVibrate: LongArray?
        get() = if (getNotificationVibrate()) longArrayOf(
            500,
            300,
            500,
            300,
            500,
            300,
            500,
            600
        ) else null

    var keepPassword: Boolean
        get() = p.getBoolean("serverkeeppassword", false)
        set(keepPassword) = p.edit().putBoolean("serverkeeppassword", keepPassword).apply()

    var login: String
        get() = p.getString("login", "") ?: ""
        set(login) = p.edit().putString("login", login).apply()

    var password: String
        get() = p.getString("password", "") ?: ""
        set(password) = p.edit().putString("password", password).apply()

    var phoneToken: String?
        get() = p.getString("phone_token", null)
        set(token) {
            if (token != null) p.edit().putString("phone_token", token).apply()
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

    private fun getSavedCustomString(key: String): String? {
        return p.getString(key, null)
    }

    fun saveCustomStringsKeysList(keys: Set<String>) {
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


    var timeStampOfPanicCommand: Long?
        get() {
            val storedValue = p.getLong("timeStampOfPanicCommand", -1)
            return if (storedValue == -1L) {
                null
            } else {
                storedValue
            }
        }
        set(value) {
            p.edit().putLong("timeStampOfPanicCommand", value ?: -1).apply()
        }

    var selectedPanelSerial: Long?
        get() {
            val value = p.getLong(selectedPanel, -1)
            return if (value == (-1).toLong()) {
                null
            } else {
                value
            }

        }
        set(value) {
            p.edit().putLong(selectedPanel, value ?: -1).apply()
        }


    var selectedGroupSerial: BigInteger
        get() {
            val default = "0"
            val value = p.getString(selectedGroup, default) ?: default
            return BigInteger(value)
        }
        set(value) {
            p.edit().putString(selectedGroup, value.toString()).apply()
        }


    private fun resetCamerasRtspStorageV0() {
        val file = File(
            context.getDir(CAMERAS_DIR_V0, Context.MODE_PRIVATE),
            TREE_MAP_CHILD
        )
        file.writeText("")
        file.delete()
    }

    private fun resetCamerasRtspStorageV1() {
        val file = File(
            context.getDir(CAMERAS_DIR_V1, Context.MODE_PRIVATE),
            TREE_MAP_CHILD
        )
        file.writeText("")
        file.delete()
    }

    private fun resetCamerasRtspStorageV2() {
        val file = File(
            context.getDir(CAMERAS_DIR_V2, Context.MODE_PRIVATE),
            RTSP_CAMERAS_FILENAME
        )
        file.writeText("")
        file.delete()
    }

    private fun resetCamerasDahuaStorageV2() {
        val file = File(
            context.getDir(CAMERAS_DIR_V2, Context.MODE_PRIVATE),
            DAHUA_CAMERAS_FILENAME
        )
        file.writeText("")
        file.delete()
    }

    private fun resetStorage() {
        resetCamerasRtspStorageV0()
        resetCamerasRtspStorageV1()
        resetCamerasRtspStorageV2()
        resetCamerasDahuaStorageV2()
    }

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        p.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        p.unregisterOnSharedPreferenceChangeListener(listener)
    }


    fun resetUserInfo() {
        p.edit().remove(lastSavedUserInfoKey).apply()
    }


    fun removePanelList() {
        p.edit().remove(savedPanelsKey).apply()
    }

    fun removePanelState(serial: Long) {
        p.edit().remove("${lastSessionKey}_$serial").apply()
    }


    fun getUserPanelArmDisarmSubscribed(userLogin: String, panelId: Long): Boolean {
        return p.getBoolean("${userLogin}_${panelId}_arm_disarm_subscribed", false)
    }

    fun setUserPanelArmDisarmSubscribed(userLogin: String, panelSerial: Long, subscribed: Boolean) {
        p.edit().putBoolean("${userLogin}_${panelSerial}_arm_disarm_subscribed", subscribed).apply()
    }

    fun disableLogger() {
        android.util.Log.i("Stuff", "Logger disabled")
        p.edit().putBoolean(loggerWindowKey, false).apply()
    }

    fun toggleLogger() {
        p.edit().putBoolean(loggerWindowKey, shouldShowLogger().not()).apply()
    }

    fun shouldShowLogger(): Boolean {
        return false
        return p.getBoolean(loggerWindowKey, false)
    }

    fun isSubscribedToNotifications(id: Int) = p.getBoolean(id.toString(), false)

    fun saveSubscriptionToNotificationById(id: Int) =
        p.edit().putBoolean(id.toString(), true).apply()

    fun setNotificationVibrate(value: Boolean) {
        p.edit().putBoolean(notificationVibrateKey, value).apply()
    }

    fun getNotificationVibrate(): Boolean {
        return try {
            p.getBoolean(notificationVibrateKey, true)
        } catch (ex: ClassCastException) {
            true
        }
    }


    fun saveTokenInfo(value: String) {
        p.edit().putString(authTokenInfoKey, value).apply()
    }

    fun getTokenInfo(): String? {
        return p.getString(authTokenInfoKey, "")
    }


    fun removeGeofenceInfo(serial: Long, userEmail: String) {
        val key = geofencePointKey + "_" + serial + "_" + userEmail
        p.edit().putString(key, null).apply()
    }


    fun getVersionName(): String? {
        return p.getString(versionNameKey, null)
    }

    fun saveVersionName(value: String) {
        p.edit().putString(versionNameKey, value).apply()
    }

    fun saveSingleAppearance(value: Boolean) {
        p.edit().putString(singleAppearanceKey, value.toString()).apply()
    }

    fun getSingleAppearance(): Boolean {
        return p.getString(singleAppearanceKey, "true") == "true"
    }

    fun isSavedSingleAppearance(): Boolean {
        return p.getString(singleAppearanceKey, null) != null
    }

    fun shouldAskForReview(): Boolean {
        val successfulCommandsCount = p.getInt(successfulCommandsCounterKey, 0)
        val appLaunchesCount = p.getInt(appLaunchesCounterKey, 0)
        return (successfulCommandsCount > 30 && appLaunchesCount > 10)
    }

    fun successfulCommandsIncrement() {
        val newValue = p.getInt(successfulCommandsCounterKey, 0) + 1
        p.edit().putInt(successfulCommandsCounterKey, newValue).apply()
        android.util.Log.i("Review", "Successful command registered")
    }

    fun appLaunchesIncrement() {
        val newValue = p.getInt(appLaunchesCounterKey, 0) + 1
        p.edit().putInt(appLaunchesCounterKey, newValue).apply()
        android.util.Log.i("Review", "App launch registered")
    }


    fun resetAppReviewCounters() {
        p.edit().putInt(successfulCommandsCounterKey, 0).apply()
        p.edit().putInt(appLaunchesCounterKey, 0).apply()
        android.util.Log.i("Review", "Review counters reset")
    }

}