# mindwave_mobile2
[![Pub Version](https://img.shields.io/pub/v/mindwave_mobile2)](https://pub.dartlang.org/packages/mindwave_mobile2)

A plugin the provides a Flutter interface for connecting with [Neurosky MindWave Mobile 2](https://store.neurosky.com/pages/mindwave) Headset.
This plugin is built over the android SDK provided from NeuroSky [Android Developer Tools 4.2](https://store.neurosky.com/products/android-developer-tools-4), utlizing both Stream SDK and AlgoSDK.

<u>**Note that:** The plugin is currently **only supports android**, feel free to **contribute** to add IOS support or any other functionalties.</u>

<u>**Note that:** The plugin is not offical or sponsored by [Neurosky](http://neurosky.com/).</u>

## Example
You can try an example utlizing this plugin.
```
cd ./example
flutter run
```
<img src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_scan_screen.jpg' width='250' height='510'/>..<img src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_disconnected.jpg' width='250' height='510'/>

<img src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_connected_raw.jpg' width='250' height='510'/>..<img src='https://github.com/PrinceEGY/mindwave_mobile2/blob/main/site/example_connected_algo.jpg' width='250' height='510'/>



## Usage
To use this plugin, add `mindwave_mobile2` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/)
```yaml
dependencies:
  ......
  mindwave_mobile_2: '^1.0.0'
```
The plugin requires the MindWave mobile 2 device ID in order to initialize, you can get this using any bluetooth package such as [FlutterBluePlus](https://pub.dev/packages/flutter_blue_plus).

### Initalizing MindWave

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
Most of the plugin functionalties are built as event streamers, every headset state or data are emitted as events.

**Note that: the plugin provides all events interfaces for both StreamSDK and AlgoSDK, there is common events between both SDK's that returns exactly the same results, you shall use any of them.**
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

## Contribution
Feel free to contribute to this plugin to add, update, suggest other features.
