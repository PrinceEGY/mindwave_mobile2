package headset.events.nskAlgo.algoMeditation;

import headset.events.MeditationData;
import headset.events.nskAlgo.NskAlgoEvent;

public class AlgoMeditationEvent extends NskAlgoEvent {

  private final MeditationData meditationData;

  public AlgoMeditationEvent(Object source, MeditationData data) {
    super(source);
    this.meditationData = data;
  }

  public MeditationData getMeditationData() {
    return this.meditationData;
  }

  public String toString() {
    return super.toString() + "AlgoMeditationEvent { MeditationData: " + meditationData + "}";
  }
}
