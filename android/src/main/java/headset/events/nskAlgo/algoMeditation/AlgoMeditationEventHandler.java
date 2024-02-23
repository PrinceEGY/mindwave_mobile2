package headset.events.nskAlgo.algoMeditation;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoMeditationEventHandler implements
    IEventHandler<IAlgoMeditationEventListener, AlgoMeditationEvent> {

  private final ArrayList<IAlgoMeditationEventListener> listeners = new ArrayList<IAlgoMeditationEventListener>();

  public void addListener(IAlgoMeditationEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAlgoMeditationEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAlgoMeditationEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoMeditationEvent event) {
    for (IAlgoMeditationEventListener listener : listeners) {
      listener.onMeditationUpdate(event);
    }
  }

}
