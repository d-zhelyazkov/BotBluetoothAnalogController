package project.botbluetoothanalogcontroller.event;

import java.util.Collection;

public interface IEventSource {
	void spreadEvent(final IEvent IEvent);
	void addListener(final IEventListener listener);
	void addListeners(Collection<IEventListener> listeners);
}
