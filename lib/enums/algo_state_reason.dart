/// Represents the different states returned from the AlgoSDK.
///
/// This enum defines various states that can be returned from the AlgoSDK
/// during algorithm processing.
enum AlgoState {
  /// The algorithm is initialized.
  INITED(256),

  /// The algorithm is running.
  RUNNING(512),

  /// The algorithm is collecting baseline data.
  COLLECTING_BASELINE_DATA(768),

  /// The algorithm is stopped.
  STOP(1024),

  /// The algorithm is paused.
  PAUSE(1280),

  /// The algorithm is uninitialized.
  UNINTIED(1536),

  /// The algorithm is analyzing bulk data.
  ANALYSING_BULK_DATA(2048),

  /// Mask value for the enum.
  MASK(65280);

  /// The integer value associated with the state.
  final int value;

  /// Constructs an [AlgoState] instance with the given integer value.
  const AlgoState(this.value);

  /// Retrieves an [AlgoState] instance from its integer value.
  ///
  /// - [value]: The integer value of the desired algorithm state.
  ///
  /// Returns the corresponding [AlgoState] instance.
  factory AlgoState.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
}

/// Represents the different reasons returned from the AlgoSDK.
///
/// This enum defines various reasons that can be returned from the AlgoSDK
/// during algorithm processing.
enum AlgoReason {
  /// Configuration changed.
  CONFIG_CHANGED(1),

  /// User profile changed.
  USER_PROFILE_CHANGED(2),

  /// CB changed.
  CB_CHANGED(3),

  /// Changed by the user.
  BY_USER(4),

  /// Baseline expired.
  BASELINE_EXPIRED(5),

  /// No baseline available.
  NO_BASELINE(6),

  /// Signal quality.
  SIGNAL_QUALITY(7),

  /// Mask value for the enum.
  MASK(255);

  /// The integer value associated with the reason.
  final int value;

  /// Constructs an [AlgoReason] instance with the given integer value.
  const AlgoReason(this.value);

  /// Retrieves an [AlgoReason] instance from its integer value.
  ///
  /// - [value]: The integer value of the desired algorithm reason.
  ///
  /// Returns the corresponding [AlgoReason] instance.
  factory AlgoReason.fromValue(int value) =>
      values.firstWhere((element) => element.value == value);
}
