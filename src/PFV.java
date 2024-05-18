import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static pfv.internal.event.EventType.WINDOW_CLOSE;
import static pfv.internal.event.EventType.WINDOW_RESIZE;

import java.io.File;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import pfv.Space;
import pfv.internal.PerspectiveCamera;
import pfv.internal.event.EventType;
import pfv.internal.event.key.KeyPressedEvent;
import pfv.internal.event.key.KeyReleasedEvent;
import pfv.internal.render.CubeRenderer;
import pfv.internal.Layer;
import pfv.internal.render.LineRenderer;
import pfv.internal.WindowProperties;
import pfv.internal.event.Event;
import pfv.internal.event.EventDispatcher;
import pfv.internal.event.EventHandler;
import pfv.internal.event.Input;
import pfv.internal.event.application.WindowCloseEvent;
import pfv.internal.event.application.WindowResizeEvent;
import pfv.internal.glfw.Window;
import pfv.internal.render.PointRenderer;

public abstract class PFV implements Layer {
    private Window window;
    private boolean running;
    private long lastFrameTime;
    private boolean minimized;
    private EventHandler<WindowCloseEvent> windowCloseHandler;
    private EventHandler<WindowResizeEvent> windowResizeHandler;
    protected PerspectiveCamera camera;
    protected Space space;
    private float cursorPosX;
    private float cursorPosY;

    public PFV() {

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

        if(e.getEventType() == EventType.WINDOW_RESIZE) {
            WindowResizeEvent wre = (WindowResizeEvent)e;
            this.camera.setAspectRatio((float)wre.getWidth() / (float)wre.getHeight());
            glViewport(0, 0, wre.getWidth(), wre.getHeight());
            return;
        }

        if(e.getEventType() == EventType.KEY_PRESSED) {
            KeyPressedEvent kpe = (KeyPressedEvent)e;

            if(kpe.getKeyCode() == GLFW_KEY_LEFT_ALT) {
                this.window.setCursorVisible(true);
            }

            return;
        }

        if(e.getEventType() == EventType.KEY_RELEASED) {
            KeyReleasedEvent kpe = (KeyReleasedEvent)e;

            if(kpe.getKeyCode() == GLFW_KEY_LEFT_ALT) {
                this.window.setCursorVisible(false);
                this.window.setCursorPosition(this.window.getWidth() / 2f, this.window.getHeight() / 2f);
            }
        }
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
        this.onAttach();
        window = this.getWindow();
        window.setCursorVisible(false);
        window.setCursorPosition(window.getWidth() / 2f, window.getHeight() / 2f);

        glEnable(GL_DEPTH_TEST);
        this.camera = new PerspectiveCamera((float)this.window.getWidth() / (float)this.window.getHeight());

        CubeRenderer.initialize(100000);
        LineRenderer.initialize(100);
        PointRenderer.initialize(10000);
    }

    public void space(File f) {
        this.space = new Space(f);
    }

    private void getCursorPos() {
        Input input = Input.getInput();
        this.cursorPosX = input.getMouseX();
        this.cursorPosY = input.getMouseY();
    }

    private void processInput(float ts) {
        Input input = Input.getInput();

        if(!input.isKeyPressed(GLFW_KEY_LEFT_ALT)) {
            getCursorPos();
            float cpxo = cursorPosX - window.getWidth() / 2f;
            float cpyo = cursorPosY - window.getHeight() / 2f;

            camera.addYaw(cpxo * ts * 0.001f);
            float p = camera.getPitch();
            float fp = p - cpyo * ts * 0.001f;
            if(Math.abs(fp) >= Math.PI / 2) {
                if(fp > 0) {
                    fp = (float)(Math.PI / 2) - Math.ulp((float)(Math.PI / 2));
                } else {
                    fp = (float)(Math.PI / 2) + Math.ulp((float)(Math.PI / 2));
                }
            }

            camera.setPitch(fp);
            camera.updateCameraVectors();
            this.window.setCursorPosition(window.getWidth() / 2f, window.getHeight() / 2f);
        }

        if(input.isKeyPressed(GLFW_KEY_W)) {
            camera.addPosition(camera.getFront().mul(ts * 0.01f));
        }

        if(input.isKeyPressed(GLFW_KEY_S)) {
            camera.addPosition(camera.getFront().mul(-ts * 0.01f));
        }

        if(input.isKeyPressed(GLFW_KEY_A)) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(-ts * 0.01f));
        }

        if(input.isKeyPressed(GLFW_KEY_D)) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(ts * 0.01f));
        }

        if(input.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.addPosition(0, ts * 0.01f, 0);
        }

        if(input.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.addPosition(0, -ts * 0.01f, 0);
        }
    }

    public void delay(long delayMillis) {
        long last = System.nanoTime();
        long current;
        long goal = System.nanoTime() + delayMillis * 1000000;
        while((current = System.nanoTime()) < goal) {
            glfwPollEvents();
            processInput((float)(current - last) / 1000000);
        }
    }

    private void onUpdate(float ts) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        processInput(ts);

        CubeRenderer.begin(camera);

        for(Vector3i v : this.space.ends()) {
            CubeRenderer.drawCube(new Vector3f(0.2f, 0.8f, 0.3f), new Matrix4f().translate(v.x, v.y, v.z));
        }

        for(Vector3i v : this.space.starts()) {
            CubeRenderer.drawCube(new Vector3f(0.8f, 0.8f, 0.2f), new Matrix4f().translate(v.x, v.y, v.z));
        }

        for(Vector3i v : this.space.obstacles()) {
            CubeRenderer.drawCube(new Vector3f(1, 0, 0), new Matrix4f().translate(v.x + 0.475f, v.y + 0.475f, v.z + 0.475f).scale(0.05f));
        }

        CubeRenderer.end();

        LineRenderer.begin(camera);
        Vector3i dimension = this.space.getDimension();
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(dimension.x, 0, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(0, dimension.y, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(0, 0, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, 0, dimension.z), new Vector3f(dimension.x, 0, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, dimension.y, dimension.z), new Vector3f(dimension.x, dimension.y, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, dimension.y, dimension.z), new Vector3f(0, dimension.y, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, dimension.y, dimension.z), new Vector3f(0, dimension.y, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, dimension.y, dimension.z), new Vector3f(0, 0, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(0, dimension.y, 0), new Vector3f(dimension.x, dimension.y, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(dimension.x, 0, 0), new Vector3f(dimension.x, 0, dimension.z));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(dimension.x, dimension.y, dimension.z), new Vector3f(dimension.x, dimension.y, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(dimension.x, 0, 0), new Vector3f(dimension.x, dimension.y, 0));
        LineRenderer.drawLine(new Vector3f(1, 1, 1), new Vector3f(dimension.x, 0, dimension.z), new Vector3f(dimension.x, dimension.y, dimension.z));
        LineRenderer.end();
    }

    public void begin() {
        long time = System.nanoTime();
        float ts = (time - this.lastFrameTime) / 1000000f;
        this.lastFrameTime = time;

        if(!this.minimized) {
            this.onUpdate(ts);
        }
    }

    public void end() {
        this.window.onUpdate();
    }

    public void start() {
        this.lastFrameTime = System.nanoTime();
        this.run();

        while(this.running) {
            this.begin();
            this.stable();
            this.end();
        }

        this.onDetach();
        this.window.dispose();
    }

    public Window getWindow() {
        return this.window;
    }

    public void setRunning(boolean b) {
        this.running = b;
    }
}
