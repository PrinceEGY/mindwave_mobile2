package headset.events.stream.streamRaw;

import headset.events.stream.StreamEvent;

public class StreamRawDataEvent extends StreamEvent {

  private final StreamRawData rawData;

  public StreamRawDataEvent(Object source, StreamRawData data) {
    super(source);
    this.rawData = data;
  }

  public StreamRawData getRawData() {
    return this.rawData;
  }

  public String toString() {
    return super.toString() + "StreamRawDataEvent { rawData=" + this.rawData + "}";
  }
}
