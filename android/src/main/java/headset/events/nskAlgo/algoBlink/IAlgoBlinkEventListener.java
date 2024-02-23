package headset.events.nskAlgo.algoBlink;


import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoBlinkEventListener extends IAlgoEventListener {

  void onBlink(AlgoBlinkEvent event);
}
