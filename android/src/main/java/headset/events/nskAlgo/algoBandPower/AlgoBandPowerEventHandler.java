package headset.events.nskAlgo.algoBandPower;


import headset.events.IEventHandler;
import java.util.ArrayList;

public class AlgoBandPowerEventHandler implements
    IEventHandler<IAlgoBandPowerEventListener, AlgoBandPowerEvent> {

  private final ArrayList<IAlgoBandPowerEventListener> listeners = new ArrayList<IAlgoBandPowerEventListener>();

  public void addListener(IAlgoBandPowerEventListener listener) {
    listeners.add(listener);
  }

  public void removeListener(IAlgoBandPowerEventListener listener) {
    listeners.remove(listener);
  }

  public boolean containsListener(IAlgoBandPowerEventListener listener) {
    return listeners.contains(listener);
  }

  public void fireEvent(AlgoBandPowerEvent event) {
    for (IAlgoBandPowerEventListener listener : listeners) {
      listener.onBandPowerUpdate(event);
    }
  }
}
