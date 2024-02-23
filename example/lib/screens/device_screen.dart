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
  int? _mtuSize;
  HeadsetState _headsetState = HeadsetState.DISCONNECTED;
  final bool _isConnecting = false;

  late StreamSubscription<int> _mtuSubscription;
  late StreamSubscription _headsetStateSubscription;
  late StreamSubscription _attentionSubscription;

  @override
  void initState() {
    super.initState();
    _mtuSubscription = widget.device.mtu.listen((value) {
      _mtuSize = value;
      if (mounted) {
        setState(() {});
      }
    });
  }

  @override
  void dispose() {
    _mtuSubscription.cancel();
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

  Widget buildRemoteId(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Text('${widget.device.remoteId}'),
    );
  }

  Widget buildMtuTile(BuildContext context) {
    return ListTile(
        title: const Text('MTU Size'),
        subtitle: Text('$_mtuSize bytes'),
        trailing: IconButton(
          icon: const Icon(Icons.edit),
          onPressed: onRequestMtuPressed,
        ));
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
              Text(_headsetState.name),
              buildRemoteId(context),
              buildMtuTile(context),
              ElevatedButton(
                  onPressed: () => _attentionSubscription =
                      FlutterMindwaveMobile2.instance.onAttentionUpdate(
                          (value) => {
                                debugPrint(
                                    "Attention Received From Native:  $value\n")
                              }),
                  child: const Text("Attention Listen")),
              ElevatedButton(
                onPressed: () => _headsetStateSubscription =
                    FlutterMindwaveMobile2.instance
                        .onStateChange(headsetStateListener),
                child: const Text("HeadState Listen"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
