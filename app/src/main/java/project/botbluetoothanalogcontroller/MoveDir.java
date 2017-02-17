package project.botbluetoothanalogcontroller;

import java.nio.ByteBuffer;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public enum MoveDir {
    UNDEF,
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT;

    public byte[] toByteArray()
    {
        byte[] result = new byte[1];
        result[0] = ByteBuffer.allocate(4).putInt(ordinal()).array()[3];
        return result;
    }
}
