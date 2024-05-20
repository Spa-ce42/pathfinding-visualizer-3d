package pfv.internal.event.mouse;

import pfv.internal.event.EventType;

/**
 * The type Mouse button released event.
 */
public class MouseButtonReleasedEvent extends MouseButtonEvent {
    /**
     * Instantiates a new Mouse button released event.
     *
     * @param button the button
     */
    public MouseButtonReleasedEvent(int button) {
        super(button);
    }

    @Override
    public EventType getEventType() {
        return EventType.MOUSE_BUTTON_RELEASED;
    }
}
