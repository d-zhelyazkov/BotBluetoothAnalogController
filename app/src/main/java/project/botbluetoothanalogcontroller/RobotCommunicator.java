package project.botbluetoothanalogcontroller;

import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

import project.botbluetoothanalogcontroller.bluetooth.BluetoothConnection;
import project.botbluetoothanalogcontroller.bluetooth.BluetoothModule;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTCannotConnectToADeviceException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTReceiveException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTTransitionException;
import project.botbluetoothanalogcontroller.event.IEvent;
import project.botbluetoothanalogcontroller.event.RobotConnectedMessage;
import project.botbluetoothanalogcontroller.event.TryingToConnectMessage;

public abstract class RobotCommunicator extends AsyncTask<Void, IEvent, Void> {

    private BluetoothModule bluetooth;
    private BluetoothDevice device;
    private RobotTranslator translator;
    protected boolean stopWorker = false;

    public RobotCommunicator(BluetoothDevice device, IRobotDataSource robotDataSource) {
        translator = RobotTranslator.getInstance();
        translator.setDataSource(robotDataSource);
        this.device = device;
        bluetooth = SharedResources.getInstance().getBluetoothModule();
    }

    @Override
    protected Void doInBackground(Void... params) {
        BluetoothConnection connection = null;
        while ((!Thread.currentThread().isInterrupted()) && (!stopWorker)) {
            try {
                if ((connection == null) || (!connection.isConnected())) {
                    publishProgress(new TryingToConnectMessage());
                    connection = bluetooth.connectToADevice(device);
                    publishProgress(new RobotConnectedMessage());
                }
                if (connection.isDataAvailable()) {
                    byte[] receivedData = connection.readData();
                    try {
                        byte[] response = translator.translate(receivedData);
                        connection.sendData(response);
                    } catch (UnsupportedEncodingException e) {
                        throw new BTReceiveException(e);
                    }
                    publishProgress(translator.getLastCommunicationEvent());
                }

            } catch (BTCannotConnectToADeviceException | BTReceiveException | BTTransitionException e) {
                publishProgress(e);
            }
        }

        return null;
    }


    @Override
    protected abstract void onProgressUpdate(IEvent... progress);

    @Override
    protected void onCancelled(Void result) {
        stopWorker = true;
    }

}
