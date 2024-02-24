/// Represents the band power data computed by an AlgoSDK.
///
/// This class encapsulates the band power values for different frequency bands
/// (delta, theta, alpha, beta, gamma) as computed by an algorithm.
class AlgoBandPower {
  /// The delta band power value.
  late num delta;

  /// The theta band power value.
  late num theta;

  /// The alpha band power value.
  late num alpha;

  /// The beta band power value.
  late num beta;

  /// The gamma band power value.
  late num gamma;

  /// Creates a new instance of [AlgoBandPower] with the given band power values.
  ///
  /// - [delta]: The delta band power value.
  /// - [theta]: The theta band power value.
  /// - [alpha]: The alpha band power value.
  /// - [beta]: The beta band power value.
  /// - [gamma]: The gamma band power value.
  AlgoBandPower(this.delta, this.theta, this.alpha, this.beta, this.gamma);
}
