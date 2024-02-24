package headset.events.nskAlgo.algoStateChange;

import headset.events.nskAlgo.NskAlgoEvent;
import java.util.HashMap;
import java.util.Map;

public class AlgoStateChangeEvent extends NskAlgoEvent {

  private final AlgoState state;
  private final AlgoStateChangeReason reason;

  public AlgoStateChangeEvent(Object source, int state, int reason) {
    super(source);
    this.state = AlgoState.fromValue(state);
    this.reason = AlgoStateChangeReason.fromValue(reason);
  }

  public AlgoStateChangeEvent(Object source, AlgoState state, AlgoStateChangeReason reason) {
    super(source);
    this.state = state;
    this.reason = reason;
  }

  public AlgoState getState() {
    return state;
  }

  public AlgoStateChangeReason getReason() {
    return reason;
  }

  @Override
  public String toString() {
    return super.toString() + "AlgoStateChangeEvent { State: " + state + ", Reason: " + reason
        + "}";
  }
}
