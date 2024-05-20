package pfv.internal.event;

/**
 * The type Event handler.
 *
 * @param <T> the type parameter
 */
public record EventHandler<T extends Event>(EventType type, EventHandlingFunction<T> function) {

}
