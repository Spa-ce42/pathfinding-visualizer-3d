package pfv.internal.event;

public interface EventHandlingFunction<T extends Event> {
    boolean apply(T e);
}
