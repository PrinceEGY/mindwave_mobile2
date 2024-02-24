package headset.events.nskAlgo.algoStateChange;

import headset.events.headsetStateChange.HeadsetState;

public enum AlgoStateChangeReason {
  //TODO: REMOVE TEST
  TEST(-1),
  CONFIG_CHANGED(1),
  USER_PROFILE_CHANGED(2),
  CB_CHANGED(3),
  BY_USER(4),
  BASELINE_EXPIRED(5),
  NO_BASELINE(6),
  SIGNAL_QUALITY(7),
  MASK(255);
  private int value;

  AlgoStateChangeReason(int value) {
    this.setValue(value);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public static AlgoStateChangeReason fromValue(int value) {
    for (AlgoStateChangeReason e : values()) {
      if (e.value == value) {
        return e;
      }
    }
    return null;
  }

}
