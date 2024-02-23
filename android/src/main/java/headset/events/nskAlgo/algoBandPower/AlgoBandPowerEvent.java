package headset.events.nskAlgo.algoBandPower;

import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoBandPowerEvent extends NskAlgoEvent {

  private final AlgoBandPowerData bandPowerData;

  public AlgoBandPowerEvent(Object source, AlgoBandPowerData bandPowerData) {
    super(source);
    this.bandPowerData = bandPowerData;
  }

  public AlgoBandPowerData getBandPowerData() {
    return this.bandPowerData;
  }

  public String toString() {
    return super.toString() + "AlgoBandPowerEvent { BandPowerData: " + bandPowerData + "}";
  }
}
