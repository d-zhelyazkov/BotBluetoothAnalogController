package project.botbluetoothanalogcontroller.bluetooth;

import android.bluetooth.BluetoothDevice;

import project.botbluetoothanalogcontroller.list.IDescriptiveItem;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class DescriptiveBluetoothDevice implements IDescriptiveItem {

    private BluetoothDevice bluetoothDevice;

    public DescriptiveBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public String getDescription() {
        return bluetoothDevice.getName();
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
}
