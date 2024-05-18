package pfv.internal;

import pfv.internal.event.Event;

public interface Layer {
    void onAttach();
    void onDetach();
    void run();
    void stable();
    void onEvent(Event e);
}
