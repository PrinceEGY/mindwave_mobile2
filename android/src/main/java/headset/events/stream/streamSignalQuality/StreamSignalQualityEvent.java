package headset.events.stream.streamSignalQuality;

import headset.events.MeditationData;
import headset.events.SignalQualityData;
import headset.events.stream.StreamEvent;

public class StreamSignalQualityEvent extends StreamEvent {

  private final SignalQualityData signalQuality;

  public StreamSignalQualityEvent(Object source, SignalQualityData data) {
    super(source);
    this.signalQuality = data;
  }

  public SignalQualityData getSignalQualityData() {
    return this.signalQuality;
  }

  @Override
  public String toString() {
    return super.toString() + "StreamSignalQualityEvent { signalQuality=" + this.signalQuality
        + "}";
  }
}
