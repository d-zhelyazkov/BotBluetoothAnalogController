package project.botbluetoothanalogcontroller.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Collection;
import java.util.LinkedList;

public class BluetoothDeviceFoundReceiver extends BroadcastReceiver {

    private static Collection<IBluetoothDeviceDiscoveredListener> listeners = new LinkedList();

    public static void addListener(IBluetoothDeviceDiscoveredListener listener) {
        listeners.add(listener);
    }

    public BluetoothDeviceFoundReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // Add the name and address to an array adapter to show in a ListView
            for (IBluetoothDeviceDiscoveredListener listener : listeners)
                listener.onDiscover(device);
        }
    }
}
