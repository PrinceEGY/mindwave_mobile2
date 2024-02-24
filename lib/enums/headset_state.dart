/// Represents the different states of the MindWave Mobile 2 headset.
///
/// This enum defines various states that the MindWave Mobile 2 headset can be in
/// during initialization, connection, operation, and error conditions.
enum HeadsetState {
  /// Initial state.
  INIT(0),

  /// The headset is in the process of connecting.
  CONNECTING(1),

  /// The headset is successfully connected.
  CONNECTED(2),

  /// The headset is working, receiving data.
  WORKING(3),

  /// The headset is stopped.
  STOPPED(4),

  /// The headset is disconnected.
  DISCONNECTED(5),

  /// The connection process is complete.
  COMPLETE(6),

  /// Connection failed.
  CONNECTION_FAILED(100),

  /// Recording started.
  RECORDING_START(7),

  /// Recording ended.
  RECORDING_END(8),

  /// Error occurred.
  ERROR(101),

  /// Timeout occurred while waiting for data.
  GET_DATA_TIME_OUT(9),

  /// Checksum failed.
  CHECK_SUM_FAIL(1000),

  /// Recording failed.
  RECORD_FAIL(1001);

  /// The integer value associated with the state.
  final int value;

  /// Constructs a [HeadsetState] instance with the given integer value.
  const HeadsetState(this.value);

  /// Retrieves a [HeadsetState] instance from its integer value.
  ///
  /// - [value]: The integer value of the desired headset state.
  ///
  /// Returns the corresponding [HeadsetState] instance.
  factory HeadsetState.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
}
