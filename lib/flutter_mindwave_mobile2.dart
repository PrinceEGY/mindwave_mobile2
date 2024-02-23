import 'dart:async';

import 'package:flutter/services.dart';

import 'HeadsetState.dart';

const String NAMESPACE = 'flutter_mindwave_mobile2';

class FlutterMindwaveMobile2 {
  static final FlutterMindwaveMobile2 instance =
      FlutterMindwaveMobile2._internal();
  factory FlutterMindwaveMobile2() {
    return instance;
  }
  FlutterMindwaveMobile2._internal();

  Future<bool> init(String deviceID) async {
    return await _connectionChannel
        .invokeMethod("init", {'deviceID': deviceID});
  }

  Future<bool> connect() async {
    return await _connectionChannel.invokeMethod("connect");
  }

  Future<bool> disconnect() async {
    return await _connectionChannel.invokeMethod("disconnect");
  }

  final MethodChannel _connectionChannel =
      const MethodChannel("$NAMESPACE/ConnectionChannel");
  final EventChannel _headsetStateChannel =
      const EventChannel("$NAMESPACE/HeadsetState");
  final EventChannel _signalQualityChannel =
      const EventChannel("$NAMESPACE/SignalQuality");
  final EventChannel _attentionChannel =
      const EventChannel("$NAMESPACE/Attention");
  final EventChannel _meditationChannel =
      const EventChannel("$NAMESPACE/Meditation");
  final EventChannel _bandPowerChannel =
      const EventChannel("$NAMESPACE/BandPower");
  final EventChannel _rawChannel = const EventChannel("$NAMESPACE/RAW");

  final EventChannel _algoStateReasonChannel =
      const EventChannel("$NAMESPACE/AlgoStateReason");
  final EventChannel _algoSignalQualityChannel =
      const EventChannel("$NAMESPACE/AlgoSignalQuality");
  final EventChannel _algoAttentionChannel =
      const EventChannel("$NAMESPACE/AlgoAttention");
  final EventChannel _algoMeditationChannel =
      const EventChannel("$NAMESPACE/AlgoMeditation");
  final EventChannel _algoBandPowerChannel =
      const EventChannel("$NAMESPACE/AlgoBandPower");
  final EventChannel _algoBlinkChannel = const EventChannel("$NAMESPACE/Blink");

  // ****************************
  // **** Stream SDK Streams ****
  // ****************************
  StreamSubscription<HeadsetState> onStateChange(listenStream) {
    return _headsetStateChannel
        .receiveBroadcastStream()
        .map((value) => HeadsetState.fromValue(value))
        .listen(listenStream);
  }

  StreamSubscription<dynamic> onSignalQualityUpdate(listenStream) {
    return _signalQualityChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onAttentionUpdate(listenStream) {
    return _attentionChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onMeditationUpdate(listenStream) {
    return _meditationChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onBandPowerUpdate(listenStream) {
    return _bandPowerChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onRawUpdate(listenStream) {
    return _rawChannel.receiveBroadcastStream().listen(listenStream);
  }

  // ****************************
  // **** Algo SDK Streams ****
  // ****************************
  StreamSubscription<dynamic> onAlgoStateReasonChange(listenStream) {
    return _algoStateReasonChannel
        .receiveBroadcastStream()
        .listen(listenStream);
  }

  StreamSubscription<dynamic> onAlgoAttentionUpdate(listenStream) {
    return _algoAttentionChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onAlgoMeditationUpdate(listenStream) {
    return _algoMeditationChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onAlgoBandPowerUpdate(listenStream) {
    return _algoBandPowerChannel.receiveBroadcastStream().listen(listenStream);
  }

  StreamSubscription<dynamic> onAlgoSignalQualityUpdate(listenStream) {
    return _algoSignalQualityChannel
        .receiveBroadcastStream()
        .listen(listenStream);
  }

  StreamSubscription<dynamic> onBlink(listenStream) {
    return _algoBlinkChannel.receiveBroadcastStream().listen(listenStream);
  }
}
