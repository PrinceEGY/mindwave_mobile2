package headset.events.headsetStateChange;

import android.util.Log;
import headset.events.IEventHandler;
import java.util.ArrayList;

public class HeadsetStateChangeEventHandler implements
    IEventHandler<IHeadsetStateChangeEventListener, HeadsetStateChangeEvent> {

  private final ArrayList<IHeadsetStateChangeEventListener> listeners = new ArrayList<IHeadsetStateChangeEventListener>();

  public void addListener(IHeadsetStateChangeEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IHeadsetStateChangeEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IHeadsetStateChangeEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(HeadsetStateChangeEvent event) {
    Log.w("State Change Event", event.getState().name());
    for (IHeadsetStateChangeEventListener listener : listeners) {
      listener.onHeadsetStateChange(event);
    }
  }
}
