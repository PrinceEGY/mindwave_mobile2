package headset.events.stream.streamMeditation;

import headset.events.stream.IStreamEventListener;

public interface IStreamMeditationEventListener extends IStreamEventListener {

  void onMeditationUpdate(StreamMeditationEvent event);
}