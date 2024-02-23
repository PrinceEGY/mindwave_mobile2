package headset.events.nskAlgo.algoBandPower;

import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoBandPowerEventListener extends IAlgoEventListener {

  void onBandPowerUpdate(AlgoBandPowerEvent event);
}
