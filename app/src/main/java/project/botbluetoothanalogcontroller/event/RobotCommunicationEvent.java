package project.botbluetoothanalogcontroller.event;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class RobotCommunicationEvent implements IEvent {
    String message;

    public RobotCommunicationEvent(String lastMessage, String s) {
        message = lastMessage + "\n" + s;
    }

    public String getMessage() {
        return message;
    }
}
