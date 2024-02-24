package headset.events.headsetStateChange;

public enum HeadsetState {
  //TODO: REMOVE TEST
  TEST(-1),
  INIT(0),
  CONNECTING(1),
  CONNECTED(2),
  WORKING(3),
  STOPPED(4),
  DISCONNECTED(5),
  COMPLETE(6),
  CONNECTION_FAILED(100),
  RECORDING_START(7),
  RECORDING_END(8),
  ERROR(101),
  GET_DATA_TIME_OUT(9),
  CHECK_SUM_FAIL(1000),
  RECORD_FAIL(1001);

  private int value;

  HeadsetState(int value) {
    this.setValue(value);
  }
  
  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public static HeadsetState fromValue(int value) {
    for (HeadsetState e : values()) {
      if (e.value == value) {
        return e;
      }
    }
    return null;
  }
}
