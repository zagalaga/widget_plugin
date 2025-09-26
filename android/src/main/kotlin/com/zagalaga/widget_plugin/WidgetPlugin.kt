package com.zagalaga.widget_plugin

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/** WidgetPlugin */
class WidgetPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "widget_plugin")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val prefs = WidgetPluginPreferences(context)

        when (call.method) {
            "updateTextContentWidget" -> {
                prefs.updateTextContentWidget(
                        call.argument<Int>("widgetId")!!,
                        call.argument<String>("parameterId")!!,
                        call.argument<String>("name")!!,
                        call.argument("title"),
                        call.argument("value"),
                        call.argument("message"),
                        call.argument("trend"),
                        call.argument("isUpGreen"),
                        call.argument("tapAction")!!)

                updateWidget(call.argument<Int>("widgetId")!!)
                result.success(true)
            }

            "getWidgetIdsForParameter" -> {
                val ids = prefs.getWidgetIdsForParameter(call.argument<String>("parameterId")!!)
                result.success(ids)
            }

            "getAllWidgetIds" -> {
                val ids = prefs.getAllWidgetIds()
                result.success(ids)
            }

            "getWidgetData" -> {
                val widgetId = call.argument<Int>("widgetId")!!
                val key = call.argument<String>("key")!!

                val value = prefs.getWidgetData(widgetId, key)
                result.success(value)
            }

            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun updateWidget(widgetId: Int) {
        try {
            val javaClass = Class.forName("com.zagalaga.keeptrack.widget.ParameterTextGlanceWidgetReceiver")
            val intent = Intent(context, javaClass)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetId))
            context.sendBroadcast(intent)
        } catch (classException: ClassNotFoundException) {
            Log.e("WidgetPlugin", "Widget not found. $classException")
        }
    }
}
