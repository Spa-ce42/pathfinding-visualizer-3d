package pfv.internal.event.key;

import static pfv.internal.event.EventCategory.EVENT_CATEGORY_INPUT;
import static pfv.internal.event.EventCategory.EVENT_CATEGORY_KEYBOARD;

import pfv.internal.event.Event;

/**
 * The type Key event.
 */
public abstract class KeyEvent extends Event {
    private final int keyCode;

    /**
     * Instantiates a new Key event.
     *
     * @param keyCode the key code
     */
    protected KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets key code.
     *
     * @return the key code
     */
    public int getKeyCode() {
        return this.keyCode;
    }

    @Override
    public int getEventCategories() {
        return EVENT_CATEGORY_KEYBOARD | EVENT_CATEGORY_INPUT;
    }
}
