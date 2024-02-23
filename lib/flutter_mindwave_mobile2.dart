import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_mindwave_mobile2/AlgoStateReason.dart';

import 'AlgoBandPower.dart';
import 'BandPower.dart';
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
  Stream<HeadsetState> onStateChange() {
    return _headsetStateChannel
        .receiveBroadcastStream()
        .map((value) => HeadsetState.fromValue(value));
  }

  Stream<int> onSignalQualityUpdate() {
    return _signalQualityChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<int> onAttentionUpdate() {
    return _attentionChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<int> onMeditationUpdate() {
    return _meditationChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<BandPower> onBandPowerUpdate() {
    return _bandPowerChannel.receiveBroadcastStream().map((event) => BandPower(
        event[0], event[1], event[2], event[3], event[4], event[5], event[6]));
  }

  Stream<List<int>> onRawUpdate() {
    return _rawChannel
        .receiveBroadcastStream()
        .map((event) => event as List<int>);
  }

  // ****************************
  // **** Algo SDK Streams ****
  // ****************************
  Stream<Map> onAlgoStateReasonChange() {
    return _algoStateReasonChannel.receiveBroadcastStream().map((event) => {
          "State": AlgoState.fromValue(event['State']),
          "Reason": AlgoReason.fromValue(event['Reason'])
        });
  }

  Stream<int> onAlgoAttentionUpdate() {
    return _algoAttentionChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<int> onAlgoMeditationUpdate() {
    return _algoMeditationChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<AlgoBandPower> onAlgoBandPowerUpdate() {
    return _algoBandPowerChannel.receiveBroadcastStream().map((event) =>
        AlgoBandPower(event[0], event[1], event[2], event[3], event[4]));
  }

  Stream<int> onAlgoSignalQualityUpdate() {
    return _algoSignalQualityChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  Stream<int> onBlink() {
    return _algoBlinkChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }
}
