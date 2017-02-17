package project.botbluetoothanalogcontroller;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import project.botbluetoothanalogcontroller.bluetooth.BluetoothDeviceSearch;
import project.botbluetoothanalogcontroller.bluetooth.DescriptiveBluetoothDevice;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTAdapterNotAvailableException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTCannotConnectToADeviceException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTReceiveException;
import project.botbluetoothanalogcontroller.bluetooth.exception.BTTransitionException;
import project.botbluetoothanalogcontroller.event.IEvent;
import project.botbluetoothanalogcontroller.event.RobotCommunicationEvent;
import project.botbluetoothanalogcontroller.event.RobotConnectedMessage;
import project.botbluetoothanalogcontroller.event.RobotFoundMessage;
import project.botbluetoothanalogcontroller.event.RobotSearchMessage;
import project.botbluetoothanalogcontroller.event.TryingToConnectMessage;
import project.botbluetoothanalogcontroller.list.IDescriptiveItem;
import project.botbluetoothanalogcontroller.list.ListDialog;

public class MainActivity extends AppCompatActivity implements IRobotDataSource, Observer {

    private String lastPressedButton = "";
    private TextView messageText;
    private TextView analogText;
    private RobotCommunicator communicator;
    private ListDialog availableDevicesDialog;
    private BluetoothDeviceSearch bluetoothDeviceSearch;
    private JoystickGraphicsSimulator joystickGraphicsSimulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button selectDeviceButton = (Button) findViewById(R.id.selectDeviceButton);
        selectDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableDevicesDialog.show();
            }
        });

        messageText = (TextView) findViewById(R.id.messageText);
        analogText = (TextView) findViewById(R.id.analogText);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        joystickGraphicsSimulator = new JoystickGraphicsSimulator(this, R.drawable.radar, image);
        joystickGraphicsSimulator.addObserver(this);
        update(joystickGraphicsSimulator, null);
    }


    @Override
    public void onResume() {
        super.onResume();

        try {
            bluetoothDeviceSearch = new BluetoothDeviceSearch();
            SharedResources.getInstance().getBluetoothModule().init();
            availableDevicesDialog = new ListDialog(
                    this,
                    getString(R.string.sel_device),
                    bluetoothDeviceSearch
            ) {
                @Override
                public void itemClicked(IDescriptiveItem item) {
                    deviceSelected(((DescriptiveBluetoothDevice) item).getBluetoothDevice());
                }
            };
            bluetoothDeviceSearch.search();
        } catch (BTAdapterNotAvailableException e) {
            showText(R.string.bt_not_avail);
        }

    }

    private void showText(int rID) {
        messageText.setText(getString(rID));
    }

    public void eventOccurred(IEvent event) {
        if (event instanceof RobotFoundMessage) {
            showText(R.string.bot_found);
        } else if (event instanceof RobotSearchMessage) {
            showText(R.string.bot_srch);
        } else if (event instanceof BTCannotConnectToADeviceException) {
            showText(R.string.bt_conn_err);
        } else if (event instanceof BTReceiveException) {
            showText(R.string.bt_rec_err);
        } else if (event instanceof BTTransitionException) {
            showText(R.string.bt_transm_err);
        } else if (event instanceof RobotConnectedMessage) {
            showText(R.string.bot_conn);
        } else if (event instanceof TryingToConnectMessage) {
            showText(R.string.try_conn);
        } else if (event instanceof RobotCommunicationEvent) {
            messageText.setText(((RobotCommunicationEvent) event).getMessage());
        }
    }


    @Override
    public AnalogCoordinates requestAnalogInfo() {
        return joystickGraphicsSimulator.getAnalogInfo();
    }

    @Override
    public MoveDir requestDirection() {
        switch (lastPressedButton) {
            case "FORWARD":
                return MoveDir.FORWARD;
            case "BACKWARD":
                return MoveDir.BACKWARD;
            case "LEFT":
                return MoveDir.LEFT;
            case "RIGHT":
                return MoveDir.RIGHT;
        }

        return MoveDir.UNDEF;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedResources.getInstance().getBluetoothModule().finishWork();
        if (communicator != null)
            communicator.cancel(true);
    }

    public void deviceSelected(BluetoothDevice device) {
        if (communicator != null)
            communicator.cancel(true);
        communicator = new RobotCommunicator(device, this) {
            @Override
            protected void onProgressUpdate(IEvent... progress) {
                eventOccurred(progress[0]);
            }
        };
        communicator.execute();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable == joystickGraphicsSimulator) {
            analogText.setText(joystickGraphicsSimulator.getAnalogInfo().toString());
        }
    }
}
