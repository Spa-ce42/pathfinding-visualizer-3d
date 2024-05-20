package pfv.internal.event.application;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_APPLICATION;
import static pfv.internal.event.EventType.WINDOW_RESIZE;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

/**
 * The type Window resize event.
 */
public class WindowResizeEvent extends Event {
    private final int width, height;

    /**
     * Instantiates a new Window resize event.
     *
     * @param width  the width
     * @param height the height
     */
    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
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
