import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_mindwave_mobile2/flutter_mindwave_mobile2.dart';
import 'package:flutter_mindwave_mobile2/flutter_mindwave_mobile2_platform_interface.dart';
import 'package:flutter_mindwave_mobile2/flutter_mindwave_mobile2_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterMindwaveMobile2Platform
    with MockPlatformInterfaceMixin
    implements FlutterMindwaveMobile2Platform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterMindwaveMobile2Platform initialPlatform = FlutterMindwaveMobile2Platform.instance;

  test('$MethodChannelFlutterMindwaveMobile2 is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterMindwaveMobile2>());
  });

  test('getPlatformVersion', () async {
    FlutterMindwaveMobile2 flutterMindwaveMobile2Plugin = FlutterMindwaveMobile2();
    MockFlutterMindwaveMobile2Platform fakePlatform = MockFlutterMindwaveMobile2Platform();
    FlutterMindwaveMobile2Platform.instance = fakePlatform;

    expect(await flutterMindwaveMobile2Plugin.getPlatformVersion(), '42');
  });
}
