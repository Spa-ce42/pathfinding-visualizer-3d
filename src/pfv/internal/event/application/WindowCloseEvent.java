package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

/**
 * The type Window close event.
 */
public class WindowCloseEvent extends Event {
    @Override
    public EventType getEventType() {
        return EventType.WINDOW_CLOSE;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_APPLICATION;
    }
}
