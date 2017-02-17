package project.botbluetoothanalogcontroller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import project.botbluetoothanalogcontroller.event.RobotCommunicationEvent;

public class RobotTranslator {
    private static final String REQUEST_ANALOG_INFO = "1";
    private static final String REQUEST_DIRECTION = "0";

    private static final RobotTranslator instance = new RobotTranslator();

    public static RobotTranslator getInstance() {
        return instance;
    }

    private IRobotDataSource dataSource;
    private RobotCommunicationEvent lastCommunicationEvent;

    private RobotTranslator() {
    }

    public void setDataSource(IRobotDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] translate(byte[] data) throws UnsupportedEncodingException {
        String lastMessage = new String(data, "UTF-8");
        byte[] result = new byte[0];
        if (lastMessage.contains(REQUEST_ANALOG_INFO)) {
            result = dataSource.requestAnalogInfo().toByteArray();
        } else if (lastMessage.contains(REQUEST_DIRECTION)) {
            result = dataSource.requestDirection().toByteArray();
        }
        String resultStr = Arrays.toString(result);
        lastCommunicationEvent = new RobotCommunicationEvent(lastMessage, resultStr);

        return result;
    }

    public RobotCommunicationEvent getLastCommunicationEvent() {
        return lastCommunicationEvent;
    }
}
