package headset.events.stream.streamBandPower;

import headset.events.stream.StreamEvent;

public class StreamBandPowerEvent extends StreamEvent {

  private final StreamBandPower bandPower;

  public StreamBandPowerEvent(Object source, StreamBandPower data) {
    super(source);
    this.bandPower = data;
  }

  public StreamBandPower getBandPower() {
    return this.bandPower;
  }

  @Override
  public String toString() {
    return super.toString() + "StreamBandPowerEvent { bandPower=" + this.bandPower + "}";
  }
}
