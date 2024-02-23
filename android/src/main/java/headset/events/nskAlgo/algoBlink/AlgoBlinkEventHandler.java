package headset.events.nskAlgo.algoBlink;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoBlinkEventHandler implements
    IEventHandler<IAlgoBlinkEventListener, AlgoBlinkEvent> {

  private final ArrayList<IAlgoBlinkEventListener> listeners = new ArrayList<>();

  public void addListener(IAlgoBlinkEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAlgoBlinkEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAlgoBlinkEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoBlinkEvent event) {
    for (IAlgoBlinkEventListener listener : listeners) {
      listener.onBlink(event);
    }
  }
}
