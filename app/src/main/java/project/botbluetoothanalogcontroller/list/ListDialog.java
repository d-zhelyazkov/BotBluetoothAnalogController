package project.botbluetoothanalogcontroller.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by XRC_7331 on 11/24/2015.
 */
public abstract class ListDialog implements Observer{
    private AlertDialog.Builder builder;
    private List<IDescriptiveItem> items;
    private ItemsContainer container;

    public ListDialog(Context context, CharSequence title, ItemsContainer container) {
        this.container = container;
        container.addObserver(this);

        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
    }

    public abstract void itemClicked(IDescriptiveItem item);

    public void show() {
        setItems();
        builder.show();
    }

    private void setItems()
    {
        items = container.getItems();
        String[] titles = new String[items.size()];
        for(int i = 0; i < items.size(); i ++)
            titles[i] = items.get(i).getDescription();
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemClicked(items.get(which));
            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable == container)
            setItems();
    }
}
