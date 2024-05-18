package pfv.internal.event;

public class EventDispatcher {
    private final Event event;

    public EventDispatcher(Event event) {
        this.event = event;
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> boolean dispatch(EventHandler<T> eh) {
        if(this.event.getEventType() == eh.type()) {
            boolean b = eh.function().apply((T)this.event);
            this.event.setHandled(b);
            return b;
        }

        return false;
    }
}
