enum AlgoState {
  INITED(256),
  RUNNING(512),
  COLLECTING_BASELINE_DATA(768),
  STOP(1024),
  PAUSE(1280),
  UNINTIED(1536),
  ANALYSING_BULK_DATA(2048),
  MASK(65280);

  factory AlgoState.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
  const AlgoState(this.value);
  final int value;
}

enum AlgoReason {
  CONFIG_CHANGED(1),
  USER_PROFILE_CHANGED(2),
  CB_CHANGED(3),
  BY_USER(4),
  BASELINE_EXPIRED(5),
  NO_BASELINE(6),
  SIGNAL_QUALITY(7),
  MASK(255);

  factory AlgoReason.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
  const AlgoReason(this.value);
  final int value;
}
