package headset.events.headsetStateChange;

import java.util.EventObject;

public class HeadsetStateChangeEvent extends EventObject {

  private final HeadsetStateTypes state;

  public HeadsetStateChangeEvent(Object source, HeadsetStateTypes state) {
    super(source);
    this.state = state;
  }

  public HeadsetStateTypes getState() {
    return state;
  }
}
