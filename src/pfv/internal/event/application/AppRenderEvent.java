package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;
import static pfv.internal.event.EventType.APP_RENDER;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

/**
 * The type App render event.
 */
public class AppRenderEvent extends Event {
    @Override
    public EventType getEventType() {
        return APP_RENDER;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_APPLICATION;
    }
}
