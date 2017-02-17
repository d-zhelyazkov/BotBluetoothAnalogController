package project.botbluetoothanalogcontroller.event;

public interface IEventListener {
	void eventOccurred(final IEvent IEvent);
	void setSource(IEventSource source);
}
