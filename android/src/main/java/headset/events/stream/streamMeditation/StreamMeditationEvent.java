package headset.events.stream.streamMeditation;

import headset.events.MeditationData;
import headset.events.stream.StreamEvent;

public class StreamMeditationEvent extends StreamEvent {

  private final MeditationData meditationData;

  public StreamMeditationEvent(Object source, MeditationData data) {
    super(source);
    this.meditationData = data;
  }

  public MeditationData getMeditationData() {
    return this.meditationData;
  }

  public String toString() {
    return super.toString() + "StreamMeditationEvent { meditationData=" + this.meditationData + "}";
  }
}
