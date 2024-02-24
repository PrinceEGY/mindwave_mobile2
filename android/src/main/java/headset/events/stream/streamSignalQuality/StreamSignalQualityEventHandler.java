package headset.events.stream.streamSignalQuality;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamSignalQualityEventHandler implements
    IEventHandler<IStreamSignalQualityEventListener, StreamSignalQualityEvent> {

  private final ArrayList<IStreamSignalQualityEventListener> listeners = new ArrayList<IStreamSignalQualityEventListener>();

  public void addListener(IStreamSignalQualityEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamSignalQualityEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IStreamSignalQualityEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamSignalQualityEvent event) {
    for (IStreamSignalQualityEventListener listener : listeners) {
      listener.onSignalQualityUpdate(event);
    }
  }

}
