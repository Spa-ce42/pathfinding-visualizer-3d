package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;
import static pfv.internal.event.EventType.WINDOW_RESIZE;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

public class WindowResizeEvent extends Event {
    private final int width, height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public EventType getEventType() {
        return WINDOW_RESIZE;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_APPLICATION;
    }
}
