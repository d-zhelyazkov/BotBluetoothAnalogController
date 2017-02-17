package project.botbluetoothanalogcontroller;

import java.nio.ByteBuffer;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public class AnalogCoordinates {
    private static final String STR_PATTERN = "%f %f";

    private float x;
    private float y;

    public AnalogCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public AnalogCoordinates() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public byte[] toByteArray() {
        ByteBuffer result = ByteBuffer.allocate(8);
        result.putFloat(x);
        result.putFloat(y);
        return result.array();
    }

    @Override
    public String toString() {
        return String.format(STR_PATTERN, x, y);
    }
}
