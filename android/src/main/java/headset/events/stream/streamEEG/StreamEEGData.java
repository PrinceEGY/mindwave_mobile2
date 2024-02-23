package headset.events.stream.streamEEG;

public record StreamEEGData(int delta, int theta, int lowAlpha, int highAlpha, int lowBeta,
                            int highBeta, int lowGamma, int midGamma) {

}
