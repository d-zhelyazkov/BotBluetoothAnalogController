package project.botbluetoothanalogcontroller.list;

import java.util.List;
import java.util.Observable;

/**
 * Created by XRC_7331 on 11/24/2015.
 */
public abstract class ItemsContainer<T extends IDescriptiveItem> extends Observable {
    public abstract List<T> getItems();
}
