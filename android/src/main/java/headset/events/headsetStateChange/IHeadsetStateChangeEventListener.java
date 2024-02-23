package headset.events.headsetStateChange;


import java.util.EventListener;

public interface IHeadsetStateChangeEventListener extends EventListener {

  void onHeadsetStateChange(HeadsetStateChangeEvent event);
}
