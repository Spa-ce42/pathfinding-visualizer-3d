package pfv.internal.event.mouse;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_INPUT;
import static pfv.internal.event.EventCategory.EVENT_CATEGORY_MOUSE;

import pfv.internal.event.Event;
import pfv.internal.event.EventType;

public class MouseMovedEvent extends Event {
    public final float x, y;

    public MouseMovedEvent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

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
