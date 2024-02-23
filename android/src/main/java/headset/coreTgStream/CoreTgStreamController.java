package headset.coreTgStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import com.neurosky.connection.TgStreamReader;
import java.util.EventListener;
import java.util.Objects;

public class CoreTgStreamController {

  private final CoreTgStreamHandler coreTgStreamHandler;
  private BluetoothDevice bluetoothDevice;
  private TgStreamReader tgStreamReader;

  //FIXME: This constructor is for testing purposes only
  public CoreTgStreamController() {
    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public CoreTgStreamController(BluetoothManager bluetoothManager, String deviceName) {
    BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
    this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
    this.coreTgStreamHandler = new CoreTgStreamHandler();
  }

  public void connect() {
    if (Objects.isNull(this.tgStreamReader)) {
      this.tgStreamReader = new TgStreamReader(this.bluetoothDevice, this.coreTgStreamHandler);
      this.coreTgStreamHandler.setTgStreamReader(this.tgStreamReader);
      tgStreamReader.connect();
    }
  }

  private void restart() {
    if (Objects.nonNull(this.tgStreamReader) && this.tgStreamReader.isBTConnected()) {
      this.tgStreamReader.stop();
      this.tgStreamReader.close();
      this.tgStreamReader = null;
      this.connect();
    }
  }

  public void disconnect() {
    if (Objects.nonNull(this.tgStreamReader)) {
      this.tgStreamReader.stop();
      this.tgStreamReader.close();
      this.tgStreamReader = null;
    }
  }

  public void changeBluetoothDevice(BluetoothManager bluetoothManager, String deviceName) {
    if (Objects.nonNull(this.tgStreamReader)) {
      this.disconnect();
      BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
      this.bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceName);
      this.connect();
    }
  }

  public void addEventListener(EventListener listener) {
    this.coreTgStreamHandler.addEventListener(listener);
  }

  public void removeEventListener(EventListener listener) {
    this.coreTgStreamHandler.removeEventListener(listener);
  }

  //FIXME: This method is for testing purposes only
  public boolean containsListener(EventListener listener) {
    return this.coreTgStreamHandler.containsListener(listener);
  }

  //FIXME: This method is for testing purposes only
  public void fireEvent(Object event) {
    this.coreTgStreamHandler.fireEvent(event);
  }
}
