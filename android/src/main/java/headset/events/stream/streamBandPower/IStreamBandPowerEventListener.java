package headset.events.stream.streamBandPower;

import headset.events.stream.IStreamEventListener;

public interface IStreamBandPowerEventListener extends IStreamEventListener {

  void onBandPowerUpdate(StreamBandPowerEvent event);
}
