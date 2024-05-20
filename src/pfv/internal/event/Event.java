package pfv.internal.event;

/**
 * The type Event.
 */
public abstract class Event {
    private boolean handled;

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public abstract EventType getEventType();

    /**
     * Gets event categories.
     *
     * @return the event categories
     */
    public abstract int getEventCategories();

    /**
     * In category boolean.
     *
     * @param eventCategory the event category
     * @return the boolean
     */
    public boolean inCategory(int eventCategory) {
        return (this.getEventCategories() & eventCategory) != 0;
    }

    /**
     * Is handled boolean.
     *
     * @return the boolean
     */
    public boolean isHandled() {
        return this.handled;
    }

    /**
     * Sets handled.
     *
     * @param handled the handled
     */
    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @Override
    public String toString() {
        return this.getEventType().toString();
    }
}
