package headset.events.stream.streamBandPower;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamBandPowerHandler implements
    IEventHandler<IStreamBandPowerEventListener, StreamBandPowerEvent> {

  private final ArrayList<IStreamBandPowerEventListener> listeners = new ArrayList<IStreamBandPowerEventListener>();

  public void addListener(IStreamBandPowerEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamBandPowerEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(
      IStreamBandPowerEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamBandPowerEvent event) {
    for (IStreamBandPowerEventListener listener : listeners) {
      listener.onBandPowerUpdate(event);
    }
  }
}
