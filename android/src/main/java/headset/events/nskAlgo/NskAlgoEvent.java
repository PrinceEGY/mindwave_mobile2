package headset.events.nskAlgo;

import java.util.EventObject;

public class NskAlgoEvent extends EventObject {

  public NskAlgoEvent(Object source) {
    super(source);
  }

  public String toString() {
    return "NskAlgoEvent : { Source: " + source + "} ";
  }
}
