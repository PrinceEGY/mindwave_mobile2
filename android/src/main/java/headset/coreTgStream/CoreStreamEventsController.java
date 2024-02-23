package headset.coreTgStream;

import android.util.Log;
import headset.events.stream.StreamEvent;
import headset.events.stream.streamAttention.IStreamAttentionEventListener;
import headset.events.stream.streamAttention.StreamAttentionEvent;
import headset.events.stream.streamAttention.StreamAttentionEventHandler;
import headset.events.stream.streamEEG.IStreamEEGDataEventListener;
import headset.events.stream.streamEEG.StreamEEGDataEvent;
import headset.events.stream.streamEEG.StreamEEGDataEventHandler;
import headset.events.stream.streamMeditation.IStreamMeditationEventListener;
import headset.events.stream.streamMeditation.StreamMeditationEvent;
import headset.events.stream.streamMeditation.StreamMeditationEventHandler;
import headset.events.stream.streamRaw.IStreamRawDataEventListener;
import headset.events.stream.streamRaw.StreamRawDataEvent;
import headset.events.stream.streamRaw.StreamRawDataEventHandler;
import java.util.EventListener;


public class CoreStreamEventsController {

  private final StreamAttentionEventHandler streamAttentionEventHandler;

  private final StreamMeditationEventHandler streamMeditationEventHandler;
  private final StreamRawDataEventHandler streamRawDataEventHandler;

  private final StreamEEGDataEventHandler streamEEGDataEventHandler;


  CoreStreamEventsController() {
    this.streamAttentionEventHandler = new StreamAttentionEventHandler();
    this.streamMeditationEventHandler = new StreamMeditationEventHandler();
    this.streamRawDataEventHandler = new StreamRawDataEventHandler();
    this.streamEEGDataEventHandler = new StreamEEGDataEventHandler();
  }

  public void fireEvent(StreamEvent event) {
    Log.w("CoreStreamEventsController", "fireEvent: " + event.toString());
    if (event instanceof StreamAttentionEvent) {
      this.streamAttentionEventHandler.fireEvent((StreamAttentionEvent) event);

    } else if (event instanceof StreamMeditationEvent) {
      this.streamMeditationEventHandler.fireEvent((StreamMeditationEvent) event);

    } else if (event instanceof StreamEEGDataEvent) {
      this.streamEEGDataEventHandler.fireEvent((StreamEEGDataEvent) event);

    } else if (event instanceof StreamRawDataEvent) {
      this.streamRawDataEventHandler.fireEvent((StreamRawDataEvent) event);

    } else {
      throw new IllegalArgumentException("Unknown event type: " + event.getClass().getName());
    }
  }

  public void addEventListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.addListener((IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.addListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      this.streamEEGDataEventHandler.addListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.addListener(
          (IStreamRawDataEventListener) listener);
    } else {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }

  public void removeEventListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      this.streamAttentionEventHandler.removeListener((IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      this.streamMeditationEventHandler.removeListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      this.streamEEGDataEventHandler.removeListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      this.streamRawDataEventHandler.removeListener(
          (IStreamRawDataEventListener) listener);

    } else {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }

  public boolean containsListener(EventListener listener) {
    if (listener instanceof IStreamAttentionEventListener) {
      return this.streamAttentionEventHandler.containsListener(
          (IStreamAttentionEventListener) listener);

    } else if (listener instanceof IStreamMeditationEventListener) {
      return this.streamMeditationEventHandler.containsListener(
          (IStreamMeditationEventListener) listener);

    } else if (listener instanceof IStreamEEGDataEventListener) {
      return this.streamEEGDataEventHandler.containsListener(
          (IStreamEEGDataEventListener) listener);

    } else if (listener instanceof IStreamRawDataEventListener) {
      return this.streamRawDataEventHandler.containsListener(
          (IStreamRawDataEventListener) listener);

    } else {
      throw new IllegalArgumentException("Invalid Listener Type: " + listener.getClass().getName());
    }
  }
}
