import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_mindwave_mobile2_platform_interface.dart';

/// An implementation of [FlutterMindwaveMobile2Platform] that uses method channels.
class MethodChannelFlutterMindwaveMobile2 extends FlutterMindwaveMobile2Platform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_mindwave_mobile2');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
