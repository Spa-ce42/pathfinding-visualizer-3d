package pfv.internal.event;

public class EventCategory {
    public static final int NONE = 0;
    public static final int EVENT_CATEGORY_APPLICATION = 1;
    public static final int EVENT_CATEGORY_INPUT = 1 << 1;
    public static final int EVENT_CATEGORY_KEYBOARD = 1 << 2;
    public static final int EVENT_CATEGORY_MOUSE = 1 << 3;
    public static final int EVENT_CATEGORY_MOUSE_BUTTON = 1 << 4;

    private EventCategory() {
        throw new AssertionError();
    }
}
