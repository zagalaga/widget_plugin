import 'dart:async';

import 'package:flutter/services.dart';

/// The plugin is used for two functions
/// 1. Maintain shared preferences that are accessible via dart
/// 2. Send an update broadcast

class WidgetPlugin {
  static const MethodChannel _channel = const MethodChannel('widget_plugin');

  static Future<void> updateTextContentWidget(int widgetId, String name, String? title, String? value, String? message,
      bool? trend, bool? isUpGreen, int tapAction) async {
    await _channel.invokeMethod('updateTextContentWidget', {
      "widgetId": widgetId,
      "name": name,
      "title": title,
      "value": value,
      "trend": trend,
      "isUpGreen": isUpGreen,
      "message": message,
      "tapAction": tapAction
    });
  }

  static Future<List<int>> getAllWidgetIds() async {
    final List dynamicList = (await _channel.invokeMethod('getAllWidgetIds')) as List<dynamic>;
    return dynamicList.map<int>((element) => element as int).toList();
  }

  static Future<List<int>> getWidgetIdsForParameter(String parameterId) async {
    final List dynamicList =
    (await _channel.invokeMethod('getWidgetIdsForParameter', {"parameterId": parameterId})) as List<dynamic>;
    return dynamicList.map<int>((element) => element as int).toList();
  }

  static Future<T?> getWidgetData<T>(int widgetId, String key) {
    return _channel.invokeMethod<T>('getWidgetData', {'widgetId': widgetId, "key": key});
  }
}
