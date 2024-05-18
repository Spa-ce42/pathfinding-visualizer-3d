package pfv.internal.event;

public abstract class Event {
    private boolean handled;

    public abstract EventType getEventType();

    public abstract int getEventCategories();

    public boolean inCategory(int eventCategory) {
        return (this.getEventCategories() & eventCategory) != 0;
    }

    public boolean isHandled() {
        return this.handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @Override
    public String toString() {
        return this.getEventType().toString();
    }
}
