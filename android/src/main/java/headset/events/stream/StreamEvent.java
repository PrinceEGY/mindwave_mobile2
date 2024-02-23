package headset.events.stream;

import java.util.EventObject;

public class StreamEvent extends EventObject {

  public StreamEvent(Object source) {
    super(source);
  }

  public String toString() {
    return "StreamEvent : { Source: " + source + "} ";
  }

}
