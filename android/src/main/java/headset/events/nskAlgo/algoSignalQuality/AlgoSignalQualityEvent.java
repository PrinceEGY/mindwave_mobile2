package headset.events.nskAlgo.algoSignalQuality;


import headset.events.SignalQualityData;
import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoSignalQualityEvent extends NskAlgoEvent {

  private final SignalQualityData signalQuality;

  public AlgoSignalQualityEvent(Object source, SignalQualityData data) {
    super(source);
    this.signalQuality = data;
  }

  public SignalQualityData getSignalQualityData() {
    return this.signalQuality;
  }

  @Override
  public String toString() {
    return super.toString() + "AlgoSignalQualityEvent { SignalQuality: " + signalQuality + "}";
  }
}