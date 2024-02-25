# mindwave_mobile2
[![Pub Version](https://img.shields.io/pub/v/mindwave_mobile2)](https://pub.dartlang.org/packages/mindwave_mobile2)

A plugin that provides a Flutter interface for connecting with [Neurosky MindWave Mobile 2](https://store.neurosky.com/pages/mindwave) Headset.
This plugin is built over the Android SDK provided by NeuroSky [Android Developer Tools 4.2](https://store.neurosky.com/products/android-developer-tools-4), utilizing both Stream SDK and AlgoSDK.

<u>**Note that:** The plugin is currently **only supports android**, feel free to **contribute** to add IOS support or any other functionalties.</u>

<u>**Note that:** The plugin is not offical or sponsored by [Neurosky](https://neurosky.com/).</u>

## Example
You can try an example utilizing this plugin.
```
cd ./example
flutter run
```
<img alt="scan screen" src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_scan_screen.jpg?raw=true' width='250' height='510'/>..<img alt="disconnected screen" src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_disconnected.jpg?raw=true' width='250' height='510'/>

<img alt="connected raw screen" src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_connected_raw.jpg?raw=true' width='250' height='510'/>..<img alt="connected algo screen" src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_connected_algo.jpg?raw=true' width='250' height='510'/>



## Usage
To use this plugin, add `mindwave_mobile2` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/)
```yaml
dependencies:
  ......
  mindwave_mobile_2: '^1.0.0'
```
The plugin requires the MindWave mobile 2 device ID to initialize, you can get this using any Bluetooth package such as [FlutterBluePlus](https://pub.dev/packages/flutter_blue_plus).

### Initializing MindWave

```dart
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

await FlutterBluePlus.startScan(timeout: const Duration(seconds: 15));

FlutterBluePlus.scanResults.listen((List<ScanResult> results) {
  results.forEach((result) {
    final deviceID = result.device.platformName;
    if (deviceID == "MindWave Mobile") {
      MindwaveMobile2.instance.init(deviceID);
    }
  });
});
```

### Connect & Disconnect
**Must be executed after `init`**
```dart
MindwaveMobile2.instance.connect();

MindwaveMobile2.instance.disconnect();
```

## Events Streamers
Most of the plugin functions are built as event streamers, every headset state or data is emitted as events.

**Note that: the plugin provides all events interfaces for both StreamSDK and AlgoSDK, there are common events between both SDKs that return exactly the same results, you shall use any of them.**
### - Headset State
Emits the current state of the Headset.
```dart
_headsetStateSubscription = MindwaveMobile2.instance.onStateChange().listen((state) {
  _headsetState = state;
  if (state == HeadsetState.DISCONNECTED) {
    MindwaveMobile2.instance.disconnect();
  }
  if (mounted) {
    setState(() {});
  }
});
```

### - StreamSDK events
```dart
var signalQualitySubscription = MindwaveMobile2.instance.onSignalQualityUpdate()
  .listen((int signalQuality) {
      // Handle signalQuality
  });

var attentionSubscription = MindwaveMobile2.instance.onAttentionUpdate()
  .listen((int attention) {
      // Handle attention
  });

var meditationSubscription = MindwaveMobile2.instance.onMeditationUpdate()
  .listen((int meditation) {
      // Handle meditation
  });

var bandPowerSubscription = MindwaveMobile2.instance.onBandPowerUpdate()
  .listen((BandPower bandPower) {
      // Handle bandPower
  });

var rawSubscription = MindwaveMobile2.instance.onRawUpdate()
  .listen((List<int> rawData) {
      // Handle rawData
  });
```

### - AlgoSDK events
```dart
var algoStateReasonSubscription = MindwaveMobile2.instance.onAlgoStateReasonChange()
  .listen((Map stateReason) {
      // Handle stateReason
      // The returned map is of the form
      // {"State": AlgoState, "Reason": AlgoReason}
  });

var algoAttentionSubscription = MindwaveMobile2.instance.onAlgoAttentionUpdate()
  .listen((int attention) {
      // Handle attention
  });

var algoMeditationSubscription = MindwaveMobile2.instance.onAlgoMeditationUpdate()
  .listen((int meditation) {
      // Handle meditation
  });

var algoBandPowerSubscription = MindwaveMobile2.instance.onAlgoBandPowerUpdate()
  .listen((AlgoBandPower algoBandPower) {
      // Handle algoBandPower
  });

var algoSignalQualitySubscription = MindwaveMobile2.instance.onAlgoSignalQualityUpdate()
  .listen((int signalQuality) {
      // Handle signalQuality
  });

var algoBlinkSubscription = MindwaveMobile2.instance.onBlink()
  .listen((int blinkStrength) {
      // Handle blinkStrength
  });
```

## Credits
This project includes parts of code that implements and wraps native Stream and Algo SDKs from [NeuroSyncCore](https://github.com/Neuro-Sync/NeuroSyncCore/tree/main/app/src/main/java/headset) created by [Mohamed Medhat](https://github.com/MOHAMMED1MEDHAT). We are grateful for their work and the impact it has had on this plugin.


## Contribution
Feel free to contribute to this plugin to add, update, and suggest other features.
