package pfv.internal.event.mouse;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_INPUT;
import static pfv.internal.event.EventCategory.EVENT_CATEGORY_MOUSE;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

/**
 * The type Mouse moved event.
 */
public class MouseMovedEvent extends Event {
    /**
     * The X.
     */
    public final float x, /**
     * The Y.
     */
    y;

    /**
     * Instantiates a new Mouse moved event.
     *
     * @param x the x
     * @param y the y
     */
    public MouseMovedEvent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return this.y;
    }

    @Override
    public EventType getEventType() {
        return EventType.MOUSE_MOVED;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_MOUSE | EVENT_CATEGORY_INPUT;
    }
}
