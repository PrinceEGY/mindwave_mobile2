package headset.events.stream.streamAttention;

import headset.events.stream.IStreamEventListener;

public interface IStreamAttentionEventListener extends IStreamEventListener {

  void onAttentionUpdate(StreamAttentionEvent event);
}
