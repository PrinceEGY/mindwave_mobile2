package headset.events.stream.streamEEG;

import headset.events.stream.StreamEvent;

public class StreamEEGDataEvent extends StreamEvent {

  private final StreamEEGData eegData;

  public StreamEEGDataEvent(Object source, StreamEEGData data) {
    super(source);
    this.eegData = data;
  }

  public StreamEEGData getEEGData() {
    return this.eegData;
  }

  public String toString() {
    return super.toString() + "StreamEEGDataEvent { eegData=" + this.eegData + "}";
  }
}
