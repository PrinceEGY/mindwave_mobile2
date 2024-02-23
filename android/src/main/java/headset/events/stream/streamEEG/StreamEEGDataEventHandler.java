package headset.events.stream.streamEEG;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamEEGDataEventHandler implements
    IEventHandler<IStreamEEGDataEventListener, StreamEEGDataEvent> {

  private final ArrayList<IStreamEEGDataEventListener> listeners = new ArrayList<IStreamEEGDataEventListener>();

  public void addListener(IStreamEEGDataEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamEEGDataEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(
      IStreamEEGDataEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamEEGDataEvent event) {
    for (IStreamEEGDataEventListener listener : listeners) {
      listener.onEEGDataUpdate(event);
    }
  }
}
