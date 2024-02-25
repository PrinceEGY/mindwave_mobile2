/// Represents the band power data returned from StreamSDK.
///
/// This class encapsulates the band power values for different frequency bands
/// (delta, theta, low alpha, high alpha, low beta, low gamma, middle gamma)
/// as returned from the StreamSDK.
class BandPower {
  /// The delta band power value.
  late num delta;

  /// The theta band power value.
  late num theta;

  /// The low alpha band power value.
  late num lowAlpha;

  /// The high alpha band power value.
  late num highAlpha;

  /// The low beta band power value.
  late num lowBeta;

  /// The low beta band power value.
  late num highBeta;

  /// The low gamma band power value.
  late num lowGamma;

  /// The middle gamma band power value.
  late num middleGamma;

  /// Creates a new instance of [BandPower] with the given band power values.
  ///
  /// - [delta]: The delta band power value.
  /// - [theta]: The theta band power value.
  /// - [lowAlpha]: The low alpha band power value.
  /// - [highAlpha]: The high alpha band power value.
  /// - [lowBeta]: The low beta band power value.
  /// - [highBeta]: The high beta band power value.
  /// - [lowGamma]: The low gamma band power value.
  /// - [middleGamma]: The middle gamma band power value.
  BandPower(this.delta, this.theta, this.lowAlpha, this.highAlpha, this.lowBeta,
      this.highBeta, this.lowGamma, this.middleGamma);
}
