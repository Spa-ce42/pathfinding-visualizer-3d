package pfv.internal.event.mouse;

import pfv.internal.event.EventType;

/**
 * The type Mouse button pressed event.
 */
public class MouseButtonPressedEvent extends MouseButtonEvent {
    /**
     * Instantiates a new Mouse button pressed event.
     *
     * @param button the button
     */
    public MouseButtonPressedEvent(int button) {
        super(button);
    }

    @Override
    public EventType getEventType() {
        return EventType.MOUSE_BUTTON_PRESSED;
    }
}
