package headset.events.headsetStateChange;

import java.util.EventObject;

public class HeadsetStateChangeEvent extends EventObject {

  private final HeadsetState state;

  public HeadsetStateChangeEvent(Object source, HeadsetState state) {
    super(source);
    this.state = state;
  }

  public HeadsetState getState() {
    return state;
  }
}
