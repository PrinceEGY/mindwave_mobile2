package headset.events.nskAlgo.algoStateChange;

import headset.events.nskAlgo.NskAlgoEvent;
import java.util.HashMap;
import java.util.Map;

public class AlgoStateChangeEvent extends NskAlgoEvent {

  private static final Map<Integer, AlgoStateTypes> INTEGER_ALGO_STATE_TYPES_MAP = new HashMap<Integer, AlgoStateTypes>();
  private static final Map<Integer, AlgoStateChangeReasons> INTEGER_ALGO_STATE_CHANGE_REASONS_MAP = new HashMap<Integer, AlgoStateChangeReasons>();

  static {
    for (AlgoStateChangeReasons type : AlgoStateChangeReasons.values()) {
      INTEGER_ALGO_STATE_CHANGE_REASONS_MAP.put(type.ordinal(), type);
    }

    for (AlgoStateTypes type : AlgoStateTypes.values()) {
      INTEGER_ALGO_STATE_TYPES_MAP.put(type.ordinal(), type);
    }
  }

  private final AlgoStateTypes state;
  private final AlgoStateChangeReasons reason;

  public AlgoStateChangeEvent(Object source, int state, int reason) {
    super(source);
    this.state = INTEGER_ALGO_STATE_TYPES_MAP.get(state);
    this.reason = INTEGER_ALGO_STATE_CHANGE_REASONS_MAP.get(reason);
  }

  public AlgoStateTypes getState() {
    return state;
  }

  public AlgoStateChangeReasons getReason() {
    return reason;
  }

  public String toString() {
    return super.toString() + "AlgoStateChangeEvent { State: " + state + ", Reason: " + reason
        + "}";
  }
}
