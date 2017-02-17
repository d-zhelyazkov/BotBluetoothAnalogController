package project.botbluetoothanalogcontroller;

/**
 * Created by XRC_7331 on 11/29/2015.
 */
public interface IRobotDataSource {
    AnalogCoordinates requestAnalogInfo();

    MoveDir requestDirection();
}
