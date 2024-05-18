package pfv.internal.event.key;

import pfv.internal.event.EventType;

public class KeyReleasedEvent extends KeyEvent {
    public KeyReleasedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    public EventType getEventType() {
        return EventType.KEY_RELEASED;
    }
}
