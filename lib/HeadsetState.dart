enum HeadsetState {
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

  factory HeadsetState.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
  const HeadsetState(this.value);
  final int value;
}
