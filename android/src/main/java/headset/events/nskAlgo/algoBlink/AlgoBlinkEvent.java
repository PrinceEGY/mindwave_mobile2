package headset.events.nskAlgo.algoBlink;

import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoBlinkEvent extends NskAlgoEvent {

  private final AlgoBlinkData blinkData;

  public AlgoBlinkEvent(Object source, AlgoBlinkData data) {
    super(source);
    this.blinkData = data;
  }

  public AlgoBlinkData getBlinkData() {
    return this.blinkData;
  }

  public String toString() {
    return super.toString() + "AlgoBlinkEvent { BlinkData: " + blinkData + "}";
  }

}
