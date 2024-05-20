package pfv.internal.event;

/**
 * The enum Event type.
 */
public enum EventType {
    /**
     * None event type.
     */
    NONE,
    /**
     * Window close event type.
     */
    WINDOW_CLOSE,
    /**
     * Window resize event type.
     */
    WINDOW_RESIZE,
    /**
     * Window focus event type.
     */
    WINDOW_FOCUS,
    /**
     * Window lost focus event type.
     */
    WINDOW_LOST_FOCUS,
    /**
     * Window moved event type.
     */
    WINDOW_MOVED,
    /**
     * App tick event type.
     */
    APP_TICK,
    /**
     * App update event type.
     */
    APP_UPDATE,
    /**
     * App render event type.
     */
    APP_RENDER,
    /**
     * Key pressed event type.
     */
    KEY_PRESSED,
    /**
     * Key released event type.
     */
    KEY_RELEASED,
    /**
     * Mouse button pressed event type.
     */
    MOUSE_BUTTON_PRESSED,
    /**
     * Mouse button released event type.
     */
    MOUSE_BUTTON_RELEASED,
    /**
     * Mouse moved event type.
     */
    MOUSE_MOVED,
    /**
     * Mouse scrolled event type.
     */
    MOUSE_SCROLLED
}
