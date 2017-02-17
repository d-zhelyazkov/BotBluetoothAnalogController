package project.botbluetoothanalogcontroller;

import project.botbluetoothanalogcontroller.bluetooth.BluetoothModule;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class SharedResources {
    private static SharedResources ourInstance = new SharedResources();

    public static SharedResources getInstance() {
        return ourInstance;
    }

    private BluetoothModule bluetoothModule;
    private SharedResources() {
            bluetoothModule = new BluetoothModule();
    }

    public BluetoothModule getBluetoothModule() {
        return bluetoothModule;
    }
}
