package headset.events.nskAlgo.algoStateChange;


import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoStateChangeEventListener extends IAlgoEventListener {

  void onAlgoStateChange(AlgoStateChangeEvent event);
}
