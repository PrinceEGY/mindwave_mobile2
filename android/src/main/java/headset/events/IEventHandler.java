package headset.events;

import java.util.EventListener;
import java.util.EventObject;

public interface IEventHandler<T extends EventListener, U extends EventObject> {

  void addListener(T listener);

  void removeListener(T listener);

  boolean containsListener(T listener);

  void fireEvent(U event);

}
