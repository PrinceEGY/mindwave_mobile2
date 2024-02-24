package headset.coreNskAlgo;

import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoType;
import headset.events.AttentionData;
import headset.events.MeditationData;
import headset.events.SignalQualityData;
import headset.events.nskAlgo.algoAttention.AlgoAttentionEvent;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerData;
import headset.events.nskAlgo.algoBandPower.AlgoBandPowerEvent;
import headset.events.nskAlgo.algoBlink.AlgoBlinkData;
import headset.events.nskAlgo.algoBlink.AlgoBlinkEvent;
import headset.events.nskAlgo.algoMeditation.AlgoMeditationEvent;
import headset.events.nskAlgo.algoSignalQuality.AlgoSignalQualityEvent;
import headset.events.nskAlgo.algoStateChange.AlgoStateChangeEvent;

public class CoreNskAlgoSdk extends NskAlgoSdk {

  private final CoreNskAlgoSdkEventsController eventsHandler = new CoreNskAlgoSdkEventsController();

  public CoreNskAlgoSdk() {
    super();

    this.setOnStateChangeListener(new OnStateChangeListener() {
      @Override
      public void onStateChange(int state, int reason) {
        eventsHandler.fireEvent(new AlgoStateChangeEvent(this, state, reason));
      }
    });

    this.setOnAttAlgoIndexListener(new OnAttAlgoIndexListener() {
      @Override
      public void onAttAlgoIndex(int attention) {
        eventsHandler.fireEvent(new AlgoAttentionEvent(this, new AttentionData(attention)));
      }
    });

    this.setOnMedAlgoIndexListener(new OnMedAlgoIndexListener() {
      @Override
      public void onMedAlgoIndex(int meditation) {
        eventsHandler.fireEvent(new AlgoMeditationEvent(this, new MeditationData(meditation)));
      }
    });

    this.setOnEyeBlinkDetectionListener(new OnEyeBlinkDetectionListener() {
      @Override
      public void onEyeBlinkDetect(int strength) {
        eventsHandler.fireEvent(new AlgoBlinkEvent(this, new AlgoBlinkData(strength)));
      }
    });

    this.setOnSignalQualityListener(new OnSignalQualityListener() {
      @Override
      public void onSignalQuality(int signalQuality) {
        eventsHandler.fireEvent(
            new AlgoSignalQualityEvent(this, new SignalQualityData(signalQuality)));
      }
    });

    this.setOnBPAlgoIndexListener(new OnBPAlgoIndexListener() {
      @Override
      public void onBPAlgoIndex(float delta, float theta, float alpha, float beta, float gamma) {
        eventsHandler.fireEvent(
            new AlgoBandPowerEvent(this, new AlgoBandPowerData(delta, theta, alpha, beta, gamma)));
      }
    });

    startAlgo();
  }

  public void startAlgo() {
    NskAlgoInit(NskAlgoType.NSK_ALGO_TYPE_ATT.value +
        NskAlgoType.NSK_ALGO_TYPE_MED.value +
        NskAlgoType.NSK_ALGO_TYPE_BP.value +
        NskAlgoType.NSK_ALGO_TYPE_BLINK.value, "");
    NskAlgoStart(false);
  }

  public void stopAlgo() {
    NskAlgoUninit();
  }


  public CoreNskAlgoSdkEventsController getEventsHandler() {
    return this.eventsHandler;
  }

}
