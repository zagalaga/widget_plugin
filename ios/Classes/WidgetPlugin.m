#import "WidgetPlugin.h"
#if __has_include(<widget_plugin/widget_plugin-Swift.h>)
#import <widget_plugin/widget_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "widget_plugin-Swift.h"
#endif

@implementation WidgetPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWidgetPlugin registerWithRegistrar:registrar];
}
@end
