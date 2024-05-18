package pfv.internal;

import pfv.internal.event.Event;

public interface Layer {
    void onAttach();
    void onDetach();
    void onUpdate(float ts);
    void onEvent(Event e);
}
