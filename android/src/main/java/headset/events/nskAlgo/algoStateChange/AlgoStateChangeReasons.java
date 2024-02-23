package headset.events.nskAlgo.algoStateChange;

public enum AlgoStateChangeReasons {
  CONFIG_CHANGED(1),
  USER_PROFILE_CHANGED(2),
  CB_CHANGED(3),
  BY_USER(4),
  BASELINE_EXPIRED(5),
  NO_BASELINE(6),
  SIGNAL_QUALITY(7),
  MASK(255);
  private int value;

  AlgoStateChangeReasons(int value) {
    this.setValue(value);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

}
