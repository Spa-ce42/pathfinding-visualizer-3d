package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

/**
 * The type App tick event.
 */
public class AppTickEvent extends Event {
    @Override
    public EventType getEventType() {
        return EventType.APP_TICK;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_APPLICATION;
    }
}
