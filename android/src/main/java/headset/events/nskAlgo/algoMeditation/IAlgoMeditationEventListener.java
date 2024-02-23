package headset.events.nskAlgo.algoMeditation;

import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoMeditationEventListener extends IAlgoEventListener {

  void onMeditationUpdate(AlgoMeditationEvent event);
}