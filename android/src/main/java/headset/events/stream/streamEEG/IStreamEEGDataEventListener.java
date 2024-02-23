package headset.events.stream.streamEEG;

import headset.events.stream.IStreamEventListener;

public interface IStreamEEGDataEventListener extends IStreamEventListener {

  void onEEGDataUpdate(StreamEEGDataEvent event);
}
