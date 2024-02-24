import 'dart:async';
import 'package:flutter/services.dart';

import 'algo_band_power.dart';
import 'enums/algo_state_reason.dart';
import 'band_power.dart';
import 'enums/headset_state.dart';

const String NAMESPACE = 'mindwave_mobile2';

/// A Flutter plugin for interfacing with the MindWave Mobile 2 headset.
///
/// This plugin provides functionality to initialize, connect, and interact with
/// a MindWave Mobile 2 headset through the Flutter application. It allows
/// accessing various data streams such as headset state, signal quality,
/// attention, meditation, band power, and raw EEG data, among others.
class MindwaveMobile2 {
  /// Singleton instance for accessing the MindwaveMobile2 functionality.
  static final MindwaveMobile2 instance = MindwaveMobile2._internal();

  /// Factory constructor for returning the singleton instance.
  factory MindwaveMobile2() {
    return instance;
  }

  /// Internal constructor for singleton pattern.
  MindwaveMobile2._internal();

  /// Initializes the MindWave Mobile 2 headset with the provided device ID.
  ///
  /// Returns `true` if initialization is successful, `false` otherwise.
  Future<bool> init(String deviceID) async {
    return await _connectionChannel
        .invokeMethod("init", {'deviceID': deviceID});
  }

  /// Initiates a connection to the MindWave Mobile 2 headset.
  ///
  /// Returns `true` if the connection is successful, `false` otherwise.
  Future<bool> connect() async {
    return await _connectionChannel.invokeMethod("connect");
  }

  /// Disconnects the MindWave Mobile 2 headset.
  ///
  /// Returns `true` if disconnection is successful, `false` otherwise.
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

  /// Stream for receiving updates on headset state changes.
  ///
  /// emits 'HeadsetState' value whenever headset state changes
  Stream<HeadsetState> onStateChange() {
    return _headsetStateChannel
        .receiveBroadcastStream()
        .map((value) => HeadsetState.fromValue(value));
  }

  /// Stream for receiving updates on signal quality update.
  ///
  /// emits signal quality [0-200] every second
  Stream<int> onSignalQualityUpdate() {
    return _signalQualityChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on attention update.
  ///
  /// emits attention value [0-100] every second
  Stream<int> onAttentionUpdate() {
    return _attentionChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on meditation update.
  ///
  /// emits meditation value [0-100] every second
  Stream<int> onMeditationUpdate() {
    return _meditationChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on band power update.
  ///
  /// emits 'BandPower' values every second
  Stream<BandPower> onBandPowerUpdate() {
    return _bandPowerChannel.receiveBroadcastStream().map((event) => BandPower(
        event[0], event[1], event[2], event[3], event[4], event[5], event[6]));
  }

  /// Stream for receiving updates on raw data update.
  ///
  /// emits list of size(512) containing raw data every second
  Stream<List<int>> onRawUpdate() {
    return _rawChannel
        .receiveBroadcastStream()
        .map((event) => event as List<int>);
  }

  // ****************************
  // **** Algo SDK Streams ****
  // ****************************

  /// Stream for receiving updates on AlgoSDK state update.
  ///
  /// emits 'AlgoState' and 'AlgoReason' whenever AlgoSDK state changes.
  Stream<Map> onAlgoStateReasonChange() {
    return _algoStateReasonChannel.receiveBroadcastStream().map((event) => {
          "State": AlgoState.fromValue(event['State']),
          "Reason": AlgoReason.fromValue(event['Reason'])
        });
  }

  /// Stream for receiving updates on attention update.
  ///
  /// emits attention value [0-100] every second
  Stream<int> onAlgoAttentionUpdate() {
    return _algoAttentionChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on attention update.
  ///
  /// emits Meditation value [0-100] every second
  Stream<int> onAlgoMeditationUpdate() {
    return _algoMeditationChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on band power update.
  ///
  /// emits 'AlgoBandPower' values every second
  Stream<AlgoBandPower> onAlgoBandPowerUpdate() {
    return _algoBandPowerChannel.receiveBroadcastStream().map((event) =>
        AlgoBandPower(event[0], event[1], event[2], event[3], event[4]));
  }

  /// Stream for receiving updates on signal quality update.
  ///
  /// emits signal quality [0-3] every second
  Stream<int> onAlgoSignalQualityUpdate() {
    return _algoSignalQualityChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }

  /// Stream for receiving updates on blink detection.
  ///
  /// emits blink strength whenever blink is detected.
  Stream<int> onBlink() {
    return _algoBlinkChannel
        .receiveBroadcastStream()
        .map((value) => value as int);
  }
}
