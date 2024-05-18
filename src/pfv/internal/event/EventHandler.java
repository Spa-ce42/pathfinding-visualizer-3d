package pfv.internal.event;

public record EventHandler<T extends Event>(EventType type, EventHandlingFunction<T> function) {

}
