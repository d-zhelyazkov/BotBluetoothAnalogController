package project.botbluetoothanalogcontroller.bluetooth.exception;

import java.io.IOException;

import project.botbluetoothanalogcontroller.event.IEvent;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class BTCannotConnectToADeviceException extends Exception implements IEvent{
    public BTCannotConnectToADeviceException(IOException e) {
        super(e);
    }
}
