package pfv.internal.event.mouse;

import pfv.internal.event.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent {
    public MouseButtonReleasedEvent(int button) {
        super(button);
    }

    @Override
    public EventType getEventType() {
        return EventType.MOUSE_BUTTON_RELEASED;
    }
}
