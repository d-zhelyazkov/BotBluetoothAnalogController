package project.botbluetoothanalogcontroller.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public interface IBluetoothDeviceDiscoveredListener {
    void onDiscover(BluetoothDevice device);
}
