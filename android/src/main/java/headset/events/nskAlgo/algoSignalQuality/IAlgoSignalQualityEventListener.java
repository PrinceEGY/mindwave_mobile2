package headset.events.nskAlgo.algoSignalQuality;

import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoSignalQualityEventListener extends IAlgoEventListener {

  void onSignalQualityUpdate(AlgoSignalQualityEvent event);
}
