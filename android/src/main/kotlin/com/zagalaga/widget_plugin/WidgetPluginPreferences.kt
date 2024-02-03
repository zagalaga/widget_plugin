package com.zagalaga.widget_plugin

import android.content.Context
import android.content.SharedPreferences

/**
 * Keeps a mapping of widget ids to tracker keys in shared preferences
 */
class WidgetPluginPreferences(context: Context) {

    companion object {
        private const val KEY_PARAMETER_ID = "parameterId"

        private const val KEY_NAME = "name"
        private const val KEY_TITLE = "title"
        private const val KEY_VALUE = "value"
        private const val KEY_TREND = "trend"
        private const val KEY_IS_UP_GREEN = "isUpGreen"
        private const val KEY_TAP_ACTION = "tapAction"
        private const val KEY_MESSAGE = "message"

        private const val WIDGETS_PREFS = "WidgetsPreferences"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(WIDGETS_PREFS, 0)

    private fun getWidgetKey(widgetId: Int, key: String): String {
        return "$widgetId.$key"
    }

    fun updateTextContentWidget(
            widgetId: Int,
            name: String,
            title: String?,
            value: String?,
            message: String?,
            trend: Boolean?,
            isUpGreen: Boolean?,
            tapAction: Int) {
        prefs.edit()
                .putString(getWidgetKey(widgetId, KEY_NAME), name)
                .putString(getWidgetKey(widgetId, KEY_TITLE), title)
                .putString(getWidgetKey(widgetId, KEY_VALUE), value)
                .putString(getWidgetKey(widgetId, KEY_MESSAGE), message)
                .putInt(getWidgetKey(widgetId, KEY_TAP_ACTION), tapAction)
                .apply()

        if (trend == null) {
            prefs.edit().remove(getWidgetKey(widgetId, KEY_TREND)).apply()
        } else {
            prefs.edit().putBoolean(getWidgetKey(widgetId, KEY_TREND), trend).apply()
        }

        if (isUpGreen == null) {
            prefs.edit().remove(getWidgetKey(widgetId, KEY_IS_UP_GREEN)).apply()
        } else {
            prefs.edit().putBoolean(getWidgetKey(widgetId, KEY_IS_UP_GREEN), isUpGreen).apply()
        }
    }

    fun getAllWidgetIds(): List<Int> {
        return prefs.all
                .filter { (key, _) -> key.contains(KEY_PARAMETER_ID) }
                .keys.map { it.substring(0, it.indexOf('.')).toInt() }
    }

    fun getWidgetIdsForParameter(parameterId: String): List<Int> {
        return prefs.all
                .filter { (key, value) -> key.contains(KEY_PARAMETER_ID) && value == parameterId }
                .keys.map { it.substring(0, it.indexOf('.')).toInt() }
    }

    fun getWidgetData(widgetId: Int, key: String): Any? {
        return prefs.all[getWidgetKey(widgetId, key)]
    }
}