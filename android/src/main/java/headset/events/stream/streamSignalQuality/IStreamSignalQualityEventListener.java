package headset.events.stream.streamSignalQuality;

import headset.events.stream.IStreamEventListener;

public interface IStreamSignalQualityEventListener extends IStreamEventListener {

  void onSignalQualityUpdate(StreamSignalQualityEvent event);
}