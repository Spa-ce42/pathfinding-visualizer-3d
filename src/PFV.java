import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static pfv.internal.event.EventType.WINDOW_CLOSE;
import static pfv.internal.event.EventType.WINDOW_RESIZE;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import pfv.internal.CubeRenderer;
import pfv.internal.Layer;
import pfv.internal.WindowProperties;
import pfv.internal.event.Event;
import pfv.internal.event.EventDispatcher;
import pfv.internal.event.EventHandler;
import pfv.internal.event.Input;
import pfv.internal.event.application.WindowCloseEvent;
import pfv.internal.event.application.WindowResizeEvent;
import pfv.internal.glfw.Window;

public class PFV {
    private static PFV pfv;
    private Window window;
    private boolean running;
    private long lastFrameTime;
    private boolean minimized;
    private final Layer layer;
    private EventHandler<WindowCloseEvent> windowCloseHandler;
    private EventHandler<WindowResizeEvent> windowResizeHandler;

    public PFV(Layer u) {
        this.layer = u;
        pfv = this;
    }

    public static PFV getPFV() {
        return pfv;
    }

    private boolean onWindowClose(WindowCloseEvent e) {
        this.running = false;
        return true;
    }

    private boolean onWindowResize(WindowResizeEvent e) {
        if(e.getWidth() <= 0 || e.getHeight() <= 0) {
            this.minimized = true;
            return false;
        }

        if(this.minimized) {
            this.minimized = false;
            return false;
        }

        return false;
    }

    public void onEvent(Event e) {
        EventDispatcher ed = new EventDispatcher(e);
        ed.dispatch(this.windowCloseHandler);
        ed.dispatch(this.windowResizeHandler);
        this.layer.onEvent(e);
    }

    public void initialize() {
        WindowProperties wp = new WindowProperties();
        wp.setWidth(1280);
        wp.setHeight(720);
        wp.setTitle("Program");
        this.initialize(wp);
    }

    public void initialize(WindowProperties wp) {
        this.window = new Window(wp);
        GL.createCapabilities();
        glfwMakeContextCurrent(window.getNativeWindow());

        glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            System.err.println(MemoryUtil.memUTF8(message));
        }, this.window.getNativeWindow());

        this.window.setEventCallback(this::onEvent);
        Input.setInput(new Input(this.window.getNativeWindow()));
        this.running = true;
        this.minimized = false;
        this.windowCloseHandler = new EventHandler<>(WINDOW_CLOSE, this::onWindowClose);
        this.windowResizeHandler = new EventHandler<>(WINDOW_RESIZE, this::onWindowResize);
        this.layer.onAttach();

        CubeRenderer.initialize(2000);
    }

    public void run() {
        this.lastFrameTime = System.nanoTime();

        while(this.running) {
            long time = System.nanoTime();
            float ts = (time - this.lastFrameTime) / 1000000f;
            this.lastFrameTime = time;

            if(!this.minimized) {
                this.layer.onUpdate(ts);
            }

            this.window.onUpdate();
        }

        this.layer.onDetach();
        this.window.dispose();
    }

    public Window getWindow() {
        return this.window;
    }

    public void setRunning(boolean b) {
        this.running = b;
    }
}
