package pfv.internal.event;

/**
 * The type Event dispatcher.
 */
public class EventDispatcher {
    private final Event event;

    /**
     * Instantiates a new Event dispatcher.
     *
     * @param event the event
     */
    public EventDispatcher(Event event) {
        this.event = event;
    }

    /**
     * Dispatch boolean.
     *
     * @param <T> the type parameter
     * @param eh  the eh
     * @return the boolean
     */
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
