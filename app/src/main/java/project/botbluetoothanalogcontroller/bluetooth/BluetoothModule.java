package project.botbluetoothanalogcontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import project.botbluetoothanalogcontroller.bluetooth.exception.BTAdapterNotAvailableException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTCannotConnectToADeviceException;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class BluetoothModule {

    private static final String SERIAL_UUID = "00001101-0000-1000-8000-00805f9b34fb"; //Standard SerialPortService ID

    private boolean enabledAtStart = false;
    private BluetoothAdapter mBluetoothAdapter;
    private Collection<BluetoothConnection> connections = new LinkedList();

    public BluetoothModule() {
    }

    public void init() throws BTAdapterNotAvailableException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            throw new BTAdapterNotAvailableException();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            enabledAtStart = true;
        }

    }

    public Collection<BluetoothDevice> getPairedDevices() {
        return mBluetoothAdapter.getBondedDevices();
    }


    public BluetoothConnection connectToADevice(BluetoothDevice device) throws BTCannotConnectToADeviceException {
        UUID uuid = UUID.fromString(SERIAL_UUID);
        BluetoothConnection connection = new BluetoothConnection(device, uuid);
        connections.add(connection);
        return connection;
    }

    public void finishWork() {
        for (BluetoothConnection connection : connections) {
            connection.close();
        }


    }


}
