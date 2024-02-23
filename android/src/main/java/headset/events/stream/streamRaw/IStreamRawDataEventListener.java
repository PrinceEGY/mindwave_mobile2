package headset.events.stream.streamRaw;

import headset.events.stream.IStreamEventListener;

public interface IStreamRawDataEventListener extends IStreamEventListener {

  void onRawDataUpdate(StreamRawDataEvent event);
}
