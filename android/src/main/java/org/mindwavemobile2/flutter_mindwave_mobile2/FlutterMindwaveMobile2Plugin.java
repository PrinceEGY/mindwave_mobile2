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
  IHeadsetStateChangeEventListener headsetStateEventListener;
  IStreamAttentionEventListener attentionEventListener;
  IStreamMeditationEventListener meditationEventListener;
  IStreamEEGDataEventListener bandPowerEventListener;
  IStreamRawDataEventListener rawEventListener;
  private final StreamHandler headsetStateChannelHandler = new StreamHandler() {
    @Override
    public void onListen(Object o, EventSink eventSink) {
      headsetStateEventListener = new IHeadsetStateChangeEventListener() {
        @Override
        public void onHeadsetStateChange(HeadsetStateChangeEvent event) {
          uiThreadHandler.post(() -> eventSink.success(event.getState().getValue()));
        }
      };
      Log.i("Native","HeadsetState Listener Added");
      headset.addEventListener(headsetStateEventListener);
    }
    @Override
    public void onCancel(Object o) {
      Log.i("Native","HeadsetState Listener Removed");
      headset.removeEventListener(headsetStateEventListener);
    }
  };
  private final StreamHandler signalQualityChannelHandler = new StreamHandler(){
    @Override
    public void onListen(Object o, EventSink eventSink) {
       // TODO:
    }

    @Override
    public void onCancel(Object o) {
      // TODO:
    }
  };
  private final StreamHandler attentionChannelHandler = new StreamHandler() {
    @Override
    public void onListen(Object o, EventSink eventSink) {
      attentionEventListener = new IStreamAttentionEventListener() {
        @Override
        public void onAttentionUpdate(StreamAttentionEvent event) {
          uiThreadHandler.post(() -> eventSink.success(event.getAttentionData().attention()));
        }
      };;
      headset.addEventListener(attentionEventListener);
    }

    @Override
    public void onCancel(Object o) {
      headset.removeEventListener(attentionEventListener);
    }
  };

  private final StreamHandler meditationChannelHandler = new StreamHandler(){
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
  private final StreamHandler bandPowerChannelHandler = new StreamHandler(){
    @Override
    public void onListen(Object o, EventSink eventSink) {
      bandPowerEventListener = new IStreamEEGDataEventListener() {
        @Override
        public void onEEGDataUpdate(StreamEEGDataEvent event) {
          StreamEEGData data = event.getEEGData();
          int[] EEGData = {data.delta(), data.theta(), data.lowAlpha(), data.highAlpha(), data.lowAlpha(), data.highBeta(), data.lowGamma(), data.midGamma()};
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
  private final StreamHandler rawChannelHandler = new StreamHandler(){
    @Override
    public void onListen(Object o, EventSink eventSink) {
      rawEventListener = new IStreamRawDataEventListener() {
        @Override
        public void onRawDataUpdate(StreamRawDataEvent event) {
          short[] rawData = event.getRawData().rawData();
          // Flutter Channels doesn't support 'short' datatype, hence need to be casted
          int[] castedData = new int[rawData.length];
          for(int i=0; i<rawData.length;i++)
            castedData[i] = rawData[i];
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

  private final MethodCallHandler connectionChannelHandler = new MethodCallHandler(){
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
      if(call.method.equals("init")){
        String deviceId = call.argument("deviceID");
        headset = new MindWaveMobile2(_BluetoothManager, deviceId);
        result.success(true);
      }
      else if (call.method.equals("connect")) {
        headset.connect();
        result.success(true);
      }else if(call.method.equals("disconnect")){
        headset.disconnect();
        result.success(true);
      }
      else {
        result.notImplemented();
      }
    }
  };

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    _BluetoothManager = (BluetoothManager)flutterPluginBinding.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
    _connectionChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/ConnectionChannel");
    _connectionChannel.setMethodCallHandler(connectionChannelHandler);
    _headsetStateChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/HeadsetState");
    _headsetStateChannel.setStreamHandler(headsetStateChannelHandler);
    _signalQualityChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/SignalQuality");
    _signalQualityChannel.setStreamHandler(attentionChannelHandler);
    _attentionChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/Attention");
    _attentionChannel.setStreamHandler(attentionChannelHandler);
    _meditationChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/Meditation");
    _meditationChannel.setStreamHandler(meditationChannelHandler);
    _bandPowerChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/BandPower");
    _bandPowerChannel.setStreamHandler(bandPowerChannelHandler);
    _rawChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/RAW");
    _rawChannel.setStreamHandler(rawChannelHandler);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    headset.disconnect();
    _headsetStateChannel.setStreamHandler(null);
    _connectionChannel.setMethodCallHandler(null);
    _attentionChannel.setStreamHandler(null);
    _meditationChannel.setStreamHandler(null);
    _bandPowerChannel.setStreamHandler(null);
    _rawChannel.setStreamHandler(null);
  }
}
