package com.zagalaga.widget_plugin

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Keeps a mapping of widget ids to tracker keys in shared preferences
 */
class WidgetPluginPreferences(context: Context) {

    companion object {
        private const val KEY_PARAMETER_ID = "parameterId"

        private const val KEY_NAME = "name"
        private const val KEY_TITLE = "title"
        private const val KEY_VALUE = "value"
        const val KEY_CAN_ADD_ENTRY = "canAddEntry"

        private const val KEY_MESSAGE = "message"

        private const val WIDGETS_PREFS = "WidgetsPreferences"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(WIDGETS_PREFS, 0)

    private fun getWidgetKey(widgetId: Int, key: String): String {
        return "$widgetId.$key"
    }

    fun updateTextContentWidget(widgetId: Int, name: String, title: String, value: String, canAddEntry: Boolean) {
        prefs.edit()
                .putString(getWidgetKey(widgetId, KEY_NAME), name)
                .putString(getWidgetKey(widgetId, KEY_TITLE), title)
                .putString(getWidgetKey(widgetId, KEY_VALUE), value)
                .putBoolean(getWidgetKey(widgetId, KEY_CAN_ADD_ENTRY), canAddEntry)
                .remove(getWidgetKey(widgetId, KEY_MESSAGE))
                .apply()
    }

    fun updateMessageWidget(widgetId: Int, name: String, message: String) {
        prefs.edit()
                .putString(getWidgetKey(widgetId, KEY_NAME), name)
                .putString(getWidgetKey(widgetId, KEY_MESSAGE), message)
                .apply()
    }

    fun getWidgetIdsForParameter(parameterId: String): List<Int> {
        Log.d("WidgetPluginPreferences", "getWidgetIdsForParameter, all ${prefs.all}")
        val result = prefs.all
                .filter { (key, value) -> key.contains(KEY_PARAMETER_ID) && value == parameterId }
                .keys.map { it -> it.substring(0, it.indexOf('.')).toInt() }
        Log.d("WidgetPluginPreferences", "getWidgetIdsForParameter, result $result")
        return result
    }

    fun getWidgetData(widgetId: Int, key: String): Any? {
        return prefs.all[getWidgetKey(widgetId, key)]
    }
}