package headset.events.nskAlgo.algoSignalQuality;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoSignalQualityEventHandler implements
    IEventHandler<IAlgoSignalQualityEventListener, AlgoSignalQualityEvent> {

  private final ArrayList<IAlgoSignalQualityEventListener> listeners = new ArrayList<IAlgoSignalQualityEventListener>();

  public void addListener(IAlgoSignalQualityEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAlgoSignalQualityEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAlgoSignalQualityEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoSignalQualityEvent event) {
    for (IAlgoSignalQualityEventListener listener : listeners) {
      listener.onSignalQualityUpdate(event);
    }
  }
}
