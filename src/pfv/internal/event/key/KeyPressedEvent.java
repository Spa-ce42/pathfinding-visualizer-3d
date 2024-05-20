package pfv.internal.event.key;

import pfv.internal.event.EventType;

/**
 * The type Key pressed event.
 */
public class KeyPressedEvent extends KeyEvent {
    private final int repeatCount;

    /**
     * Instantiates a new Key pressed event.
     *
     * @param keyCode     the key code
     * @param repeatCount the repeat count
     */
    public KeyPressedEvent(int keyCode, int repeatCount) {
        super(keyCode);
        this.repeatCount = repeatCount;
    }

    /**
     * Gets repeat count.
     *
     * @return the repeat count
     */
    public int getRepeatCount() {
        return this.repeatCount;
    }

    @Override
    public EventType getEventType() {
        return EventType.KEY_PRESSED;
    }
}
