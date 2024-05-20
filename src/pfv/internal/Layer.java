package pfv.internal;

import pfv.internal.event.Event;

/**
 * The interface Layer.
 */
public interface Layer {
    /**
     * On attach.
     */
    void onAttach();

    /**
     * On detach.
     */
    void onDetach();

    /**
     * Run.
     */
    void run();

    /**
     * Stable.
     */
    void stable();

    /**
     * On event.
     *
     * @param e the e
     */
    void onEvent(Event e);
}
