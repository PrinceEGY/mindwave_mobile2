package headset.events.stream.streamMeditation;

import headset.events.IEventHandler;
import java.util.ArrayList;

public class StreamMeditationEventHandler implements
    IEventHandler<IStreamMeditationEventListener, StreamMeditationEvent> {

  private final ArrayList<IStreamMeditationEventListener> listeners = new ArrayList<IStreamMeditationEventListener>();

  public void addListener(IStreamMeditationEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IStreamMeditationEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IStreamMeditationEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(StreamMeditationEvent event) {
    for (IStreamMeditationEventListener listener : listeners) {
      listener.onMeditationUpdate(event);
    }
  }

}
