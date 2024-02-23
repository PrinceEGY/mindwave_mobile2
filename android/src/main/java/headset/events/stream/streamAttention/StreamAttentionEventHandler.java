package headset.events.stream.streamAttention;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamAttentionEventHandler implements
    IEventHandler<IStreamAttentionEventListener, StreamAttentionEvent> {

  private final ArrayList<IStreamAttentionEventListener> listeners = new ArrayList<IStreamAttentionEventListener>();

  public void addListener(IStreamAttentionEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamAttentionEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IStreamAttentionEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamAttentionEvent event) {
    for (IStreamAttentionEventListener listener : listeners) {
      listener.onAttentionUpdate(event);
    }
  }
}
