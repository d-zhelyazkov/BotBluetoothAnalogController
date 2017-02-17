package project.botbluetoothanalogcontroller.bluetooth;

import android.bluetooth.BluetoothDevice;

import java.util.LinkedList;
import java.util.List;

import project.botbluetoothanalogcontroller.SharedResources;
import project.botbluetoothanalogcontroller.list.ItemsContainer;

public class BluetoothDeviceSearch
        extends ItemsContainer<DescriptiveBluetoothDevice> {

    private BluetoothModule bluetooth;
    private List<DescriptiveBluetoothDevice> foundDevices = new LinkedList();

    public BluetoothDeviceSearch() {
        bluetooth = SharedResources.getInstance().getBluetoothModule();
    }

    public void search() {
        for (BluetoothDevice device : bluetooth.getPairedDevices()) {
            foundDevices.add(new DescriptiveBluetoothDevice(device));
        }
    }

    @Override
    public List<DescriptiveBluetoothDevice> getItems() {
        return foundDevices;
    }
}
