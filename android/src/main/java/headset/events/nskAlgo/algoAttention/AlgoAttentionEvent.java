package headset.events.nskAlgo.algoAttention;

import headset.events.AttentionData;
import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoAttentionEvent extends NskAlgoEvent {

  private final AttentionData attentionData;

  public AlgoAttentionEvent(Object source, AttentionData data) {
    super(source);
    this.attentionData = data;
  }

  public AttentionData getAttentionData() {
    return attentionData;
  }

  public String toString() {
    return super.toString() + "AlgoAttentionEvent { AttentionData: " + attentionData + "}";
  }
}
