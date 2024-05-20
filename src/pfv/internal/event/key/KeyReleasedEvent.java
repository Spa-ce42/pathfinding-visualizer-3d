package pfv.internal.event.key;

import pfv.internal.event.EventType;

/**
 * The type Key released event.
 */
public class KeyReleasedEvent extends KeyEvent {
    /**
     * Instantiates a new Key released event.
     *
     * @param keyCode the key code
     */
    public KeyReleasedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    public EventType getEventType() {
        return EventType.KEY_RELEASED;
    }
}
