package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;
import static pfv.internal.event.EventType.APP_UPDATE;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

public class AppUpdateEvent extends Event {
    @Override
    public EventType getEventType() {
        return APP_UPDATE;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_APPLICATION;
    }
}
