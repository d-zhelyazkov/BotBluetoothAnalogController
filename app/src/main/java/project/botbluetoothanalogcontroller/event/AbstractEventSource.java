package project.botbluetoothanalogcontroller.event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractEventSource implements IEventSource {
    private List<IEventListener> listeners = new LinkedList();

    @Override
    public final void spreadEvent(final IEvent event) {
        for (IEventListener listener : listeners) {
            listener.eventOccurred(event);
        }
    }

    @Override
    public final void addListener(final IEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void addListeners(Collection<IEventListener> listeners) {
        this.listeners.addAll(listeners);
    }
}
