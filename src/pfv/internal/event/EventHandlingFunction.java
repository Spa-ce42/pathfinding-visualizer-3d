package pfv.internal.event;

/**
 * The interface Event handling function.
 *
 * @param <T> the type parameter
 */
public interface EventHandlingFunction<T extends Event> {
    /**
     * Apply boolean.
     *
     * @param e the e
     * @return the boolean
     */
    boolean apply(T e);
}
