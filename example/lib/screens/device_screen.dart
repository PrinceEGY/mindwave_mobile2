import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import 'package:mindwave_mobile2/AlgoBandPower.dart';
import 'package:mindwave_mobile2/AlgoStateReason.dart';
import 'package:mindwave_mobile2/BandPower.dart';
import 'package:mindwave_mobile2/HeadsetState.dart';
import 'package:mindwave_mobile2/mindwave_mobile2.dart';
import 'package:fl_chart/fl_chart.dart';

import '../util/snackbar_popup.dart';

class DeviceScreen extends StatefulWidget {
  final BluetoothDevice device;

  const DeviceScreen({Key? key, required this.device}) : super(key: key);

  @override
  State<DeviceScreen> createState() => _DeviceScreenState();
}

class _DeviceScreenState extends State<DeviceScreen> {
  final MindwaveMobile2 headset = MindwaveMobile2();
  HeadsetState _headsetState = HeadsetState.DISCONNECTED;
  AlgoState _algoState = AlgoState.UNINTIED;
  AlgoReason _algoReason = AlgoReason.SIGNAL_QUALITY;

  late StreamSubscription<HeadsetState>? _headsetStateSubscription;
  late StreamSubscription<Map>? _algoStateReasonSubscription;

  bool streamDataListen = true;
  bool algoDataListen = true;
  bool rawChartView = true;

  @override
  void initState() {
    super.initState();
    _headsetStateSubscription = headset.onStateChange().listen((state) {
      _headsetState = state;
      if (state == HeadsetState.DISCONNECTED) {
        headset.disconnect();
      }
      if (mounted) {
        setState(() {});
      }
    });
    _algoStateReasonSubscription =
        headset.onAlgoStateReasonChange().listen((state) {
      _algoState = state['State'];
      _algoReason = state['Reason'];
      if (mounted) {
        setState(() {});
      }
    });
  }

  @override
  void dispose() {
    _headsetStateSubscription?.cancel();
    _algoStateReasonSubscription?.cancel();
    headset.disconnect();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.device.platformName),
        actions: [buildConnectButton(context)],
      ),
      body: SingleChildScrollView(
        child: Container(
          width: MediaQuery.of(context).size.width,
          padding: const EdgeInsets.all(20),
          child: Column(
            children: [
              buildStateWidget(context),
              const Divider(height: 20, color: Colors.black),
              buildSwitchStreamWidget(context),
              buildSwitchAlgoWidget(context),
              buildSwitchRawViewWidget(context),
              const Divider(height: 20, color: Colors.black),
              if (streamDataListen) buildStreamDataWidget(context),
              const SizedBox(height: 20),
              if (rawChartView) buildRawChartWidget(context),
              if (algoDataListen) ...[
                const Divider(height: 20, color: Colors.black),
                buildAlgoStateReasonHeaderWidget(context),
                const SizedBox(height: 10),
                buildAlgoStreamDataWidget(context),
              ],
            ],
          ),
        ),
      ),
    );
  }

  bool get isWorking {
    return _headsetState == HeadsetState.WORKING;
  }

  Future onConnectPressed() async {
    try {
      await MindwaveMobile2.instance.connect();
    } catch (e) {
      if (context.mounted) {
        showSnackBarPopup(
            context: context, text: e.toString(), color: Colors.red);
      }
    }
  }

  Future onDisconnectPressed() async {
    try {
      await MindwaveMobile2.instance.disconnect();
    } catch (e) {
      if (context.mounted) {
        showSnackBarPopup(
            context: context, text: e.toString(), color: Colors.red);
      }
    }
  }

  Widget buildConnectButton(BuildContext context) {
    return Row(children: [
      TextButton(
        onPressed: isWorking ? onDisconnectPressed : onConnectPressed,
        child: Text(
          isWorking ? "DISCONNECT" : "CONNECT",
        ),
      )
    ]);
  }

  Widget buildStateWidget(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        const Text(
          "MindWave State",
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        ElevatedButton(
          onPressed: null,
          style: ElevatedButton.styleFrom(
            disabledBackgroundColor: _headsetState == HeadsetState.WORKING
                ? Colors.green
                : Colors.red,
            disabledForegroundColor: Colors.white,
          ),
          child: Text(_headsetState.name),
        ),
      ],
    );
  }

  Widget buildSwitchStreamWidget(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        const Text(
          "Listen to StreamSDK Stream",
          style: TextStyle(fontSize: 18),
        ),
        Switch(
          activeTrackColor: Colors.green,
          value: streamDataListen,
          onChanged: (value) => setState(() {
            streamDataListen = value;
          }),
        )
      ],
    );
  }

  Widget buildSwitchAlgoWidget(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        const Text(
          "Listen to AlgoSDK Stream",
          style: TextStyle(fontSize: 18),
        ),
        Switch(
          activeTrackColor: Colors.green,
          value: algoDataListen,
          onChanged: (value) => setState(() {
            algoDataListen = value;
          }),
        )
      ],
    );
  }

  Widget buildSwitchRawViewWidget(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        const Text(
          "Show Raw Data View",
          style: TextStyle(fontSize: 18),
        ),
        Switch(
          activeTrackColor: Colors.green,
          value: rawChartView,
          onChanged: (value) => setState(() {
            rawChartView = value;
          }),
        )
      ],
    );
  }

  Widget buildSingleValueStreamWidget(
      BuildContext context, String title, Stream stream) {
    return StreamBuilder(
      stream: stream,
      builder: (context, snapshot) => Column(
        children: [
          Text(title, style: Theme.of(context).textTheme.bodyLarge),
          Text(snapshot.hasData ? "${snapshot.data}" : "N/A",
              style: Theme.of(context).textTheme.bodyMedium),
        ],
      ),
    );
  }

  Widget buildBandPowerStreamWidget(BuildContext context) {
    return StreamBuilder(
      stream: headset.onBandPowerUpdate(),
      builder: (context, snapshot) {
        BandPower? bandPower;
        if (snapshot.hasData) bandPower = snapshot.data;
        return Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    Text("Delta", style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.delta}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("Theta", style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.theta}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("lowAlpha",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.lowAlpha}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("highAlpha",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.highAlpha}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
              ],
            ),
            const SizedBox(
              height: 10,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    Text("lowBeta",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.delta}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("highBeta",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.theta}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("lowGamma",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.lowAlpha}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("midGamma",
                        style: Theme.of(context).textTheme.bodyLarge),
                    Text(snapshot.hasData ? "${bandPower!.highAlpha}" : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
              ],
            ),
          ],
        );
      },
    );
  }

  Widget buildStreamDataWidget(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            buildSingleValueStreamWidget(
                context, "Attention", headset.onAttentionUpdate()),
            buildSingleValueStreamWidget(
                context, "Meditation", headset.onMeditationUpdate()),
            buildSingleValueStreamWidget(
                context, "Signal Quality", headset.onSignalQualityUpdate()),
          ],
        ),
        const SizedBox(height: 10),
        buildBandPowerStreamWidget(context),
      ],
    );
  }

  Widget buildAlgoStateReasonHeaderWidget(BuildContext context) {
    return Column(
      children: [
        const Text(
          "AlgoSdk",
          style: TextStyle(fontSize: 20, fontWeight: FontWeight.w500),
        ),
        const SizedBox(height: 10),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            Column(
              children: [
                const Text(
                  "State: ",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
                ),
                Text(
                  _algoState.name,
                  style: const TextStyle(fontSize: 16),
                ),
              ],
            ),
            Column(
              children: [
                const Text(
                  "Reason: ",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w500),
                ),
                Text(
                  _algoReason.name,
                  style: const TextStyle(fontSize: 16),
                ),
              ],
            ),
          ],
        ),
      ],
    );
  }

  Widget buildAlgoBandPowerStreamWidget(BuildContext context) {
    return StreamBuilder(
      stream: headset.onAlgoBandPowerUpdate(),
      builder: (context, snapshot) {
        AlgoBandPower? algoBandPower;
        if (snapshot.hasData) algoBandPower = snapshot.data;
        return Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    Text("Delta", style: Theme.of(context).textTheme.bodyLarge),
                    Text(
                        snapshot.hasData
                            ? "${algoBandPower!.delta.toStringAsPrecision(5)} dB"
                            : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("Theta", style: Theme.of(context).textTheme.bodyLarge),
                    Text(
                        snapshot.hasData
                            ? "${algoBandPower!.theta.toStringAsPrecision(5)} dB"
                            : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("Alpha", style: Theme.of(context).textTheme.bodyLarge),
                    Text(
                        snapshot.hasData
                            ? "${algoBandPower!.alpha.toStringAsPrecision(5)} dB"
                            : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
              ],
            ),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Column(
                  children: [
                    Text("Beta", style: Theme.of(context).textTheme.bodyLarge),
                    Text(
                        snapshot.hasData
                            ? "${algoBandPower!.beta.toStringAsPrecision(5)} dB"
                            : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
                Column(
                  children: [
                    Text("Gamma", style: Theme.of(context).textTheme.bodyLarge),
                    Text(
                        snapshot.hasData
                            ? "${algoBandPower!.gamma.toStringAsPrecision(5)} dB"
                            : "N/A",
                        style: Theme.of(context).textTheme.bodyMedium),
                  ],
                ),
              ],
            ),
          ],
        );
      },
    );
  }

  Widget buildAlgoBlinkDetectWidget(BuildContext context) {
    return StreamBuilder(
      stream: headset.onBlink(),
      builder: (context, snapshot) => Column(
        children: [
          Text("Blink Detection", style: Theme.of(context).textTheme.bodyLarge),
          Text(
              snapshot.hasData
                  ? "Blink Detected with strength:${snapshot.data}"
                  : "N/A",
              style: Theme.of(context).textTheme.bodyMedium),
        ],
      ),
    );
  }

  Widget buildAlgoStreamDataWidget(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            buildSingleValueStreamWidget(
                context, "Attention", headset.onAlgoAttentionUpdate()),
            buildSingleValueStreamWidget(
                context, "Meditation", headset.onAlgoMeditationUpdate()),
            buildSingleValueStreamWidget(
                context, "Signal Quality", headset.onAlgoSignalQualityUpdate()),
          ],
        ),
        const SizedBox(height: 10),
        buildAlgoBandPowerStreamWidget(context),
        const SizedBox(height: 10),
        buildAlgoBlinkDetectWidget(context),
      ],
    );
  }

  Widget buildRawChartWidget(BuildContext context) {
    return StreamBuilder(
        stream: headset.onRawUpdate(),
        builder: (context, snapshot) {
          var spots = snapshot.hasData
              ? snapshot.data!
                  .asMap()
                  .entries
                  .map((entry) =>
                      FlSpot(entry.key.toDouble(), entry.value.toDouble()))
                  .toList()
              : List.generate(512, (index) => index)
                  .map((e) => FlSpot(e.toDouble(), 1.0))
                  .toList();
          return AspectRatio(
            aspectRatio: 2,
            child: LineChart(
              LineChartData(
                lineBarsData: [
                  LineChartBarData(
                    color: Colors.red,
                    spots: spots,
                    isCurved: false,
                    dotData: const FlDotData(
                      show: false,
                    ),
                  )
                ],
                borderData: FlBorderData(
                  border: const Border(
                    bottom: BorderSide(),
                    left: BorderSide(),
                  ),
                ),
                gridData: const FlGridData(show: false),
                titlesData: const FlTitlesData(
                  leftTitles: AxisTitles(
                      sideTitles: SideTitles(
                    showTitles: true,
                    reservedSize: 30,
                  )),
                  topTitles:
                      AxisTitles(sideTitles: SideTitles(showTitles: false)),
                  rightTitles:
                      AxisTitles(sideTitles: SideTitles(showTitles: false)),
                ),
                lineTouchData: const LineTouchData(
                  enabled: false,
                ),
              ),
            ),
          );
        });
  }
}
