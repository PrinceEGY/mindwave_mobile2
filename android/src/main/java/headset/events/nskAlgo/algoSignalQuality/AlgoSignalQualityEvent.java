package headset.events.nskAlgo.algoSignalQuality;


import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoSignalQualityEvent extends NskAlgoEvent {

  private final AlgoSignalQualityData signalQuality;

  public AlgoSignalQualityEvent(Object source, AlgoSignalQualityData data) {
    super(source);
    this.signalQuality = data;
  }

  public AlgoSignalQualityData getSignalQualityData() {
    return this.signalQuality;
  }

  public String toString() {
    return super.toString() + "AlgoSignalQualityEvent { SignalQuality: " + signalQuality + "}";
  }
}