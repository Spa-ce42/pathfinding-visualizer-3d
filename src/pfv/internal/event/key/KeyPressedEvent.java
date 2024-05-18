package pfv.internal.event.key;

import pfv.internal.event.EventType;

public class KeyPressedEvent extends KeyEvent {
    private final int repeatCount;

    public KeyPressedEvent(int keyCode, int repeatCount) {
        super(keyCode);
        this.repeatCount = repeatCount;
    }

    public int getRepeatCount() {
        return this.repeatCount;
    }

    @Override
    public EventType getEventType() {
        return EventType.KEY_PRESSED;
    }
}
