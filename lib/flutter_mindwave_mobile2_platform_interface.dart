import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_mindwave_mobile2_method_channel.dart';

abstract class FlutterMindwaveMobile2Platform extends PlatformInterface {
  /// Constructs a FlutterMindwaveMobile2Platform.
  FlutterMindwaveMobile2Platform() : super(token: _token);

  static final Object _token = Object();

  static FlutterMindwaveMobile2Platform _instance = MethodChannelFlutterMindwaveMobile2();

  /// The default instance of [FlutterMindwaveMobile2Platform] to use.
  ///
  /// Defaults to [MethodChannelFlutterMindwaveMobile2].
  static FlutterMindwaveMobile2Platform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterMindwaveMobile2Platform] when
  /// they register themselves.
  static set instance(FlutterMindwaveMobile2Platform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
