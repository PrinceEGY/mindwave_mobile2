package headset.events.nskAlgo.algoAttention;

import headset.events.nskAlgo.IAlgoEventListener;

public interface IAlgoAttentionEventListener extends IAlgoEventListener {

  void onAttentionUpdate(AlgoAttentionEvent event);
}

