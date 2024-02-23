package headset.events.nskAlgo.algoStateChange;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoStateChangeEventHandler implements
    IEventHandler<IAlgoStateChangeEventListener, AlgoStateChangeEvent> {

  private final ArrayList<IAlgoStateChangeEventListener> listeners = new ArrayList<IAlgoStateChangeEventListener>();

  public void addListener(IAlgoStateChangeEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAlgoStateChangeEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAlgoStateChangeEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoStateChangeEvent event) {
    for (IAlgoStateChangeEventListener listener : listeners) {
      listener.onAlgoStateChange(event);
    }
  }
}
