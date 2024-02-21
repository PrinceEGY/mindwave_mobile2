import 'flutter_mindwave_mobile2_platform_interface.dart';

class FlutterMindwaveMobile2 {
  Future<String?> getPlatformVersion() {
    return FlutterMindwaveMobile2Platform.instance.getPlatformVersion();
  }
}
