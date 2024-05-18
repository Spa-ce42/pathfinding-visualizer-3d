package pfv.internal.event.mouse;

import pfv.internal.event.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {
    public MouseButtonPressedEvent(int button) {
        super(button);
    }

    @Override
    public EventType getEventType() {
        return EventType.MOUSE_BUTTON_PRESSED;
    }
}
