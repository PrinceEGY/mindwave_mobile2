package headset.events.stream.streamRaw;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamRawDataEventHandler implements
    IEventHandler<IStreamRawDataEventListener, StreamRawDataEvent> {

  private final ArrayList<IStreamRawDataEventListener> listeners = new ArrayList<IStreamRawDataEventListener>();

  public void addListener(IStreamRawDataEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamRawDataEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IStreamRawDataEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamRawDataEvent event) {
    for (IStreamRawDataEventListener listener : listeners) {
      listener.onRawDataUpdate(event);
    }
  }
}
