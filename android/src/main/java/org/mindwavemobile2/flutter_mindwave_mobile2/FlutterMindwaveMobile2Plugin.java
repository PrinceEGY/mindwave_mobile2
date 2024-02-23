package org.mindwavemobile2.flutter_mindwave_mobile2;

import android.content.Context;
import android.bluetooth.BluetoothManager;
import androidx.annotation.NonNull;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import headset.MindWaveMobile2;
import headset.events.headsetStateChange.HeadsetStateChangeEvent;
import headset.events.headsetStateChange.IHeadsetStateChangeEventListener;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoAttention.IAlgoAttentionEventListener;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerData;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBandPower.IAlgoBandPowerEventListener;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoBlink.IAlgoBlinkEventListener;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoMeditation.IAlgoMeditationEventListener;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoSignalQuality.IAlgoSignalQualityEventListener;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;
import headset.events.nskAlgo.algoStateChange.IAlgoStateChangeEventListener;
import headset.events.stream.streamAttention.IStreamAttentionEventListener;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamEEG.IStreamEEGDataEventListener;
import headset.events.stream.streamEEG.StreamEEGData;
import headset.events.stream.streamEEG.StreamEEGDataEvent;
import headset.events.stream.streamMeditation.IStreamMeditationEventListener;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugin.common.EventChannel.StreamHandler;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import java.util.HashMap;


public class FlutterMindwaveMobile2Plugin implements FlutterPlugin {

  private static final String NAMESPACE = "flutter_mindwave_mobile2";
  private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
  private MindWaveMobile2 headset;
  private BluetoothManager _BluetoothManager;
  private MethodChannel _connectionChannel;
  private EventChannel _headsetStateChannel;
  private EventChannel _signalQualityChannel;
  private EventChannel _attentionChannel;
  private EventChannel _meditationChannel;
  private EventChannel _bandPowerChannel;
  private EventChannel _rawChannel;

  private EventChannel _algoStateReasonChannel;
  private EventChannel _algoSignalQualityChannel;
  private EventChannel _algoAttentionChannel;
  private EventChannel _algoMeditationChannel;
  private EventChannel _algoBandPowerChannel;
  private EventChannel _algoBlinkChannel;

  IHeadsetStateChangeEventListener headsetStateEventListener;
  IStreamAttentionEventListener attentionEventListener;
  IStreamMeditationEventListener meditationEventListener;
  IStreamEEGDataEventListener bandPowerEventListener;
  IStreamRawDataEventListener rawEventListener;

  IAlgoStateChangeEventListener algoStateReasonEventListener;
  IAlgoSignalQualityEventListener algoSignalQualityEventListener;
  IAlgoAttentionEventListener algoAttentionEventListener;
  IAlgoMeditationEventListener algoMeditationEventListener;
  IAlgoBandPowerEventListener algoBandPowerEventListener;
  IAlgoBlinkEventListener algoBlinkEventListener;

  private final MethodCallHandler connectionChannelHandler = createConnectionChannelHandler();
  private final StreamHandler headsetStateChannelHandler = createHeadsetStateChannelHandler();
  private final StreamHandler signalQualityChannelHandler = createSignalQualityChannelHandler();
  private final StreamHandler attentionChannelHandler = createAttentionChannelHandler();
  private final StreamHandler meditationChannelHandler = createMeditationChannelHandler();
  private final StreamHandler bandPowerChannelHandler = createBandPowerChannelHandler();
  private final StreamHandler rawChannelHandler = createRawChannelHandler();

  private final StreamHandler algoStateReasonChannelHandler = createAlgoStateReasonChannelHandler();
  private final StreamHandler algoSignalQualityHandler = createAlgoSignalQualityHandler();
  private final StreamHandler algoAttentionHandler = createAlgoAttentionHandler();
  private final StreamHandler algoMeditationHandler = createAlgoMeditationHandler();
  private final StreamHandler algoBandPowerHandler = createAlgoBandPowerHandler();
  private final StreamHandler algoBlinkHandler = createAlgoBlinkHandler();

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    _BluetoothManager = (BluetoothManager) flutterPluginBinding.getApplicationContext()
        .getSystemService(Context.BLUETOOTH_SERVICE);
    _connectionChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/ConnectionChannel");
    _connectionChannel.setMethodCallHandler(connectionChannelHandler);
    _headsetStateChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/HeadsetState");
    _headsetStateChannel.setStreamHandler(headsetStateChannelHandler);
    _signalQualityChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/SignalQuality");
    _signalQualityChannel.setStreamHandler(attentionChannelHandler);
    _rawChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/RAW");
    _rawChannel.setStreamHandler(rawChannelHandler);
    _attentionChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/Attention");
    _attentionChannel.setStreamHandler(attentionChannelHandler);
    _meditationChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/Meditation");
    _meditationChannel.setStreamHandler(meditationChannelHandler);
    _bandPowerChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/BandPower");
    _bandPowerChannel.setStreamHandler(bandPowerChannelHandler);
    _algoStateReasonChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/AlgoStateReason");
    _algoStateReasonChannel.setStreamHandler(algoStateReasonChannelHandler);
    _algoSignalQualityChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/AlgoSignalQuality");
    _algoSignalQualityChannel.setStreamHandler(algoSignalQualityHandler);
    _algoAttentionChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/AlgoAttention");
    _algoAttentionChannel.setStreamHandler(algoAttentionHandler);
    _algoMeditationChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/AlgoMeditation");
    _algoMeditationChannel.setStreamHandler(algoMeditationHandler);
    _algoBandPowerChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/AlgoBandPower");
    _algoBandPowerChannel.setStreamHandler(algoBandPowerHandler);
    _algoBlinkChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(),
        NAMESPACE + "/Blink");
    _algoBlinkChannel.setStreamHandler(algoBlinkHandler);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    if (headset != null) {
      headset.disconnect();
    }
    _headsetStateChannel.setStreamHandler(null);
    _connectionChannel.setMethodCallHandler(null);
    _attentionChannel.setStreamHandler(null);
    _meditationChannel.setStreamHandler(null);
    _bandPowerChannel.setStreamHandler(null);
    _rawChannel.setStreamHandler(null);
    _algoStateReasonChannel.setStreamHandler(null);
    _algoSignalQualityChannel.setStreamHandler(null);
    _algoAttentionChannel.setStreamHandler(null);
    _algoMeditationChannel.setStreamHandler(null);
    _algoBandPowerChannel.setStreamHandler(null);
    _algoBlinkChannel.setStreamHandler(null);
  }

  private StreamHandler createHeadsetStateChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        headsetStateEventListener = new IHeadsetStateChangeEventListener() {
          @Override
          public void onHeadsetStateChange(HeadsetStateChangeEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getState().getValue()));
          }
        };
        Log.i("Native", "HeadsetState Listener Added");
        headset.addEventListener(headsetStateEventListener);
      }

      @Override
      public void onCancel(Object o) {
        Log.i("Native", "HeadsetState Listener Removed");
        headset.removeEventListener(headsetStateEventListener);
      }
    };
  }

  private StreamHandler createSignalQualityChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        // TODO:
      }

      @Override
      public void onCancel(Object o) {
        // TODO:
      }
    };
  }

  private StreamHandler createAttentionChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        attentionEventListener = new IStreamAttentionEventListener() {
          @Override
          public void onAttentionUpdate(StreamAttentionEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getAttentionData().attention()));
          }
        };
        Log.i("Native", "Attention Listener Added");
        headset.addEventListener(attentionEventListener);
      }

      @Override
      public void onCancel(Object o) {
        Log.i("Native", "Attention Listener Removed");
        headset.removeEventListener(attentionEventListener);
      }
    };
  }

  private StreamHandler createMeditationChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        meditationEventListener = new IStreamMeditationEventListener() {
          @Override
          public void onMeditationUpdate(StreamMeditationEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getMeditationData().meditation()));
          }
        };
        headset.addEventListener(meditationEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(meditationEventListener);
      }
    };
  }

  private StreamHandler createBandPowerChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        bandPowerEventListener = new IStreamEEGDataEventListener() {
          @Override
          public void onEEGDataUpdate(StreamEEGDataEvent event) {
            StreamEEGData data = event.getEEGData();
            int[] EEGData = {data.delta(), data.theta(), data.lowAlpha(), data.highAlpha(),
                data.lowAlpha(), data.highBeta(), data.lowGamma(), data.midGamma()};
            uiThreadHandler.post(() -> eventSink.success(EEGData));
          }

        };
        headset.addEventListener(bandPowerEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(bandPowerEventListener);
      }
    };
  }

  private StreamHandler createRawChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        rawEventListener = new IStreamRawDataEventListener() {
          @Override
          public void onRawDataUpdate(StreamRawDataEvent event) {
            short[] rawData = event.getRawData().rawData();
            // Flutter Channels doesn't support 'short' datatype, hence need to be casted
            int[] castedData = new int[rawData.length];
            for (int i = 0; i < rawData.length; i++) {
              castedData[i] = rawData[i];
            }
            uiThreadHandler.post(() -> eventSink.success(castedData));
          }
        };
        headset.addEventListener(rawEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(rawEventListener);
      }
    };
  }

  private StreamHandler createAlgoStateReasonChannelHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoStateReasonEventListener = new IAlgoStateChangeEventListener() {
          @Override
          public void onAlgoStateChange(AlgoStateChangeEvent event) {
            HashMap<String, Integer> stateReason = new HashMap<>() {{
              put("State", event.getState().getValue());
              put("Reason", event.getReason().getValue());
            }};
            uiThreadHandler.post(() -> eventSink.success(stateReason));
          }
        };
        headset.addEventListener(algoStateReasonEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoStateReasonEventListener);
      }
    };
  }

  private StreamHandler createAlgoAttentionHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoAttentionEventListener = new IAlgoAttentionEventListener() {
          @Override
          public void onAttentionUpdate(AlgoAttentionEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getAttentionData().attention()));
          }
        };
        headset.addEventListener(algoAttentionEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoAttentionEventListener);
      }
    };
  }

  private StreamHandler createAlgoMeditationHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoMeditationEventListener = new IAlgoMeditationEventListener() {
          @Override
          public void onMeditationUpdate(AlgoMeditationEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getMeditationData().meditation()));
          }
        };
        headset.addEventListener(algoMeditationEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoMeditationEventListener);
      }
    };
  }

  private StreamHandler createAlgoSignalQualityHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoSignalQualityEventListener = new IAlgoSignalQualityEventListener() {
          @Override
          public void onSignalQualityUpdate(AlgoSignalQualityEvent event) {
            uiThreadHandler.post(
                () -> eventSink.success(event.getSignalQualityData().qualityLevel()));
          }
        };
        headset.addEventListener(algoSignalQualityEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoSignalQualityEventListener);
      }
    };
  }

  private StreamHandler createAlgoBandPowerHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoBandPowerEventListener = new IAlgoBandPowerEventListener() {
          @Override
          public void onBandPowerUpdate(AlgoBandPowerEvent event) {
            AlgoBandPowerData data = event.getBandPowerData();
            float[] BandPowerData = {data.delta(), data.theta(), data.alpha(), data.beta(),
                data.gamma()};
            uiThreadHandler.post(() -> eventSink.success(BandPowerData));
          }
        };
        headset.addEventListener(algoBandPowerEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoBandPowerEventListener);
      }
    };
  }

  private StreamHandler createAlgoBlinkHandler() {
    return new StreamHandler() {
      @Override
      public void onListen(Object o, EventSink eventSink) {
        algoBlinkEventListener = new IAlgoBlinkEventListener() {
          @Override
          public void onBlink(AlgoBlinkEvent event) {
            uiThreadHandler.post(() -> eventSink.success(event.getBlinkData().strength()));
          }
        };
        headset.addEventListener(algoBlinkEventListener);
      }

      @Override
      public void onCancel(Object o) {
        headset.removeEventListener(algoBlinkEventListener);
      }
    };
  }

  private MethodCallHandler createConnectionChannelHandler() {
    return new MethodCallHandler() {
      @Override
      public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("init")) {
          String deviceId = call.argument("deviceID");
          headset = new MindWaveMobile2(_BluetoothManager, deviceId);
          result.success(true);
        } else if (call.method.equals("connect")) {
          headset.connect();
          result.success(true);
        } else if (call.method.equals("disconnect")) {
          headset.disconnect();
          result.success(true);
        } else {
          result.notImplemented();
        }
      }
    };
  }
}
