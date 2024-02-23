package headset.events.stream.streamAttention;

import headset.events.AttentionData;
import headset.events.stream.StreamEvent;

public class StreamAttentionEvent extends StreamEvent {

  private final AttentionData attentionData;

  public StreamAttentionEvent(Object source, AttentionData data) {
    super(source);
    this.attentionData = data;
  }

  public AttentionData getAttentionData() {
    return attentionData;
  }

  public String toString() {
    return super.toString() + "StreamAttentionEvent { attentionData=" + this.attentionData + "}";
  }
}
