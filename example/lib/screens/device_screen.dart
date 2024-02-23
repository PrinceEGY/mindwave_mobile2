import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:flutter_mindwave_mobile2/HeadsetState.dart';
import 'package:flutter_mindwave_mobile2/flutter_mindwave_mobile2.dart';

import '../utils/snackbar.dart';

class DeviceScreen extends StatefulWidget {
  final BluetoothDevice device;

  const DeviceScreen({Key? key, required this.device}) : super(key: key);

  @override
  State<DeviceScreen> createState() => _DeviceScreenState();
}

class _DeviceScreenState extends State<DeviceScreen> {
  final FlutterMindwaveMobile2 headset = FlutterMindwaveMobile2();
  HeadsetState _headsetState = HeadsetState.DISCONNECTED;
  final bool _isConnecting = false;

  StreamSubscription? _headsetStateSubscription;
  StreamSubscription? _attentionSubscription;
  StreamSubscription? _meditationSubscription;

  int? _attentionData;
  int? _meditationData;

  bool testStream = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  bool get isWorking {
    return _headsetState == HeadsetState.WORKING;
  }

  void headsetStateListener(HeadsetState state) {
    debugPrint("HeadsetState: ${state.name}\n");
    if (state == HeadsetState.DISCONNECTED) {
      headset.disconnect();
    }
    setState(() {
      _headsetState = state;
    });
  }

  Future onConnectPressed() async {
    debugPrint(await FlutterMindwaveMobile2.instance.connect()
        ? "F:Connected Successfully"
        : "F:Couldn't connect Successfully");
  }

  Future onDisconnectPressed() async {
    debugPrint(await FlutterMindwaveMobile2.instance.disconnect()
        ? "F:Disconnected Successfully"
        : "F:Couldn't disconnect");
  }

  Future onRequestMtuPressed() async {
    try {
      await widget.device.requestMtu(512, predelay: 0);
      Snackbar.show(ABC.c, "Request Mtu: Success", success: true);
    } catch (e) {
      Snackbar.show(ABC.c, prettyException("Change Mtu Error:", e),
          success: false);
    }
  }

  Widget buildSpinner(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(14.0),
      child: AspectRatio(
        aspectRatio: 1.0,
        child: CircularProgressIndicator(
          backgroundColor: Colors.black12,
          color: Colors.black26,
        ),
      ),
    );
  }

  Widget buildConnectButton(BuildContext context) {
    return Row(children: [
      if (_isConnecting) buildSpinner(context),
      TextButton(
        onPressed: isWorking ? onDisconnectPressed : onConnectPressed,
        child: Text(
          isWorking ? "DISCONNECT" : "CONNECT",
        ),
      )
    ]);
  }

  @override
  Widget build(BuildContext context) {
    return ScaffoldMessenger(
      key: Snackbar.snackBarKeyC,
      child: Scaffold(
        appBar: AppBar(
          title: Text(widget.device.platformName),
          actions: [buildConnectButton(context)],
        ),
        body: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              Text("State: ${_headsetState.name}"),
              ElevatedButton(
                onPressed: () => _headsetStateSubscription == null
                    ? _headsetStateSubscription = FlutterMindwaveMobile2
                        .instance
                        .onStateChange(headsetStateListener)
                    : null,
                child: const Text("Headset State Listen"),
              ),
              ElevatedButton(
                onPressed: () => _headsetStateSubscription?.cancel(),
                child: const Text("Headset State Stop Listening"),
              ),
              const SizedBox(
                height: 50,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  testStream
                      ? StreamBuilder(
                          stream: FlutterMindwaveMobile2.instance
                              .onAttentionUpdate(),
                          builder: (context, snapshot) {
                            return Text(
                                snapshot.hasData ? "${snapshot.data}" : "NA");
                          })
                      : Text("testStraem OFF"),
                  ElevatedButton(
                      onPressed: () => setState(() {
                            testStream = !testStream;
                          }),
                      child: const Text("Attention Listen")),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                      onPressed: () => _meditationSubscription =
                          FlutterMindwaveMobile2.instance.onMeditationUpdate(
                              (value) =>
                                  setState(() => _meditationData = value)),
                      child: const Text("Meditation Listen")),
                  Text("$_meditationData"),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                      onPressed: () => _meditationSubscription =
                          FlutterMindwaveMobile2.instance.onRawUpdate(
                              (List<int> value) => debugPrint(
                                  "Raw is Received, Length: ${value.length}, First sample: ${value.first}, Last sample: ${value.last}")),
                      child: const Text("Raw Listen")),
                  const Text("Raw Placeholder"),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
