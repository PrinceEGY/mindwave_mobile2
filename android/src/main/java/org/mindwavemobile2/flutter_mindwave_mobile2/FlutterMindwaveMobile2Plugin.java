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
  private EventChannel _attentionChannel;
  private EventChannel _headsetStateChannel;
  IStreamAttentionEventListener attentionEventListener;
  IHeadsetStateChangeEventListener headsetStateEventListener;
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
      headset.removeEventListener(headsetStateEventListener);
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
    _attentionChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), NAMESPACE+"/Attention");
    _attentionChannel.setStreamHandler(attentionChannelHandler);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    headset.disconnect();
    _connectionChannel.setMethodCallHandler(null);
    _headsetStateChannel.setStreamHandler(null);
    _attentionChannel.setStreamHandler(null);
  }
}
