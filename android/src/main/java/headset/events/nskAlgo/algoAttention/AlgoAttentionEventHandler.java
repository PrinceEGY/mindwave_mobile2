package headset.events.nskAlgo.algoAttention;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoAttentionEventHandler implements
    IEventHandler<IAlgoAttentionEventListener, AlgoAttentionEvent> {

  private final ArrayList<IAlgoAttentionEventListener> listeners = new ArrayList<IAlgoAttentionEventListener>();

  public void addListener(IAlgoAttentionEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(
      IAlgoAttentionEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(
      IAlgoAttentionEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoAttentionEvent event) {
    for (IAlgoAttentionEventListener listener : listeners) {
      listener.onAttentionUpdate(event);
    }
  }
}
