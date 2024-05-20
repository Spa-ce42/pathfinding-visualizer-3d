package pfv.internal.event;

/**
 * The type Event category.
 */
public class EventCategory {
    /**
     * The constant NONE.
     */
    public static final int NONE = 0;
    /**
     * The constant EVENT_CATEGORY_APPLICATION.
     */
    public static final int EVENT_CATEGORY_APPLICATION = 1;
    /**
     * The constant EVENT_CATEGORY_INPUT.
     */
    public static final int EVENT_CATEGORY_INPUT = 1 << 1;
    /**
     * The constant EVENT_CATEGORY_KEYBOARD.
     */
    public static final int EVENT_CATEGORY_KEYBOARD = 1 << 2;
    /**
     * The constant EVENT_CATEGORY_MOUSE.
     */
    public static final int EVENT_CATEGORY_MOUSE = 1 << 3;
    /**
     * The constant EVENT_CATEGORY_MOUSE_BUTTON.
     */
    public static final int EVENT_CATEGORY_MOUSE_BUTTON = 1 << 4;

    private EventCategory() {
        throw new AssertionError();
    }
}
