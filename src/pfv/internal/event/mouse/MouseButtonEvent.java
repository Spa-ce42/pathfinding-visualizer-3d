package pfv.internal.event.mouse;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_INPUT;
import static pfv.internal.event.EventCategory.EVENT_CATEGORY_MOUSE;
import static pfv.internal.event.EventCategory.EVENT_CATEGORY_MOUSE_BUTTON;

import pfv.internal.event.Event;

/**
 * The type Mouse button event.
 */
public abstract class MouseButtonEvent extends Event {
    private final int button;

    /**
     * Instantiates a new Mouse button event.
     *
     * @param button the button
     */
    protected MouseButtonEvent(int button) {
        this.button = button;
    }

    /**
     * Gets button.
     *
     * @return the button
     */
    public int getButton() {
        return this.button;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_MOUSE | EVENT_CATEGORY_INPUT | EVENT_CATEGORY_MOUSE_BUTTON;
    }
}
