package headset;

import android.bluetooth.BluetoothManager;
import headset.coreTgStream.CoreTgStreamController;
import java.util.EventListener;


public class MindWaveMobile2 {

  private final CoreTgStreamController coreTgStreamController;

  //FIXME: This constructor is for testing purposes only
  public MindWaveMobile2() {
    this.coreTgStreamController = new CoreTgStreamController();
  }

  public MindWaveMobile2(BluetoothManager bluetoothManager, String deviceName) {
    this.coreTgStreamController = new CoreTgStreamController(bluetoothManager, deviceName);
  }

  public void connect() {
    this.coreTgStreamController.connect();
  }

  public void disconnect() {
    this.coreTgStreamController.disconnect();
  }

  public void changeBluetoothDevice(BluetoothManager bluetoothManager, String deviceName) {
    this.coreTgStreamController.changeBluetoothDevice(bluetoothManager, deviceName);
  }


  public void addEventListener(EventListener listener) {
    this.coreTgStreamController.addEventListener(listener);
  }

  public void removeEventListener(EventListener listener) {
    this.coreTgStreamController.removeEventListener(listener);
  }

  //FIXME: This method is for testing purposes only
  public boolean containsListener(EventListener listener) {
    return this.coreTgStreamController.containsListener(listener);
  }

  public void fireEvent(Object event) {
    this.coreTgStreamController.fireEvent(event);
  }

}
