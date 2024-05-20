package pfv.internal.glfw;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_FLOATING;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_POSITION_X;
import static org.lwjgl.glfw.GLFW.GLFW_POSITION_Y;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import pfv.internal.WindowProperties;
import pfv.internal.event.EventCallback;
import pfv.internal.event.application.WindowCloseEvent;
import pfv.internal.event.application.WindowResizeEvent;
import pfv.internal.event.key.KeyPressedEvent;
import pfv.internal.event.key.KeyReleasedEvent;
import pfv.internal.event.mouse.MouseButtonPressedEvent;
import pfv.internal.event.mouse.MouseButtonReleasedEvent;
import pfv.internal.event.mouse.MouseMovedEvent;
import pfv.internal.event.mouse.MouseScrolledEvent;

/**
 * The type Window.
 */
public class Window {
    private final long window;
    private String title;
    private int width, height;
    private boolean vsync;
    private EventCallback eventCallback;
    private boolean pinnedToTop;
    private boolean cursorVisible;

    /**
     * Instantiates a new Window.
     *
     * @param wp the wp
     */
    @SuppressWarnings("resource")
    public Window(WindowProperties wp) {
        this.title = wp.getTitle();
        this.width = wp.getWidth();
        this.height = wp.getHeight();
        this.setVsync(wp.isVsync());

        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        GLFWStates.initialize();
        GLFWVidMode gvm = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if(gvm != null) {
            glfwWindowHint(GLFW_POSITION_X, (gvm.width() - this.width) / 2);
            glfwWindowHint(GLFW_POSITION_Y, (gvm.height() - this.height) / 2);
        }

        this.window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        glfwMakeContextCurrent(this.window);

        glfwSetWindowSizeCallback(this.window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.eventCallback.onEvent(new WindowResizeEvent(width, height));
        });

        glfwSetWindowCloseCallback(this.window,
                l -> this.eventCallback.onEvent(new WindowCloseEvent()));

        glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
            switch(action) {
                case GLFW_PRESS -> this.eventCallback.onEvent(new KeyPressedEvent(key, 0));
                case GLFW_RELEASE -> this.eventCallback.onEvent(new KeyReleasedEvent(key));
                case GLFW_REPEAT -> this.eventCallback.onEvent(new KeyPressedEvent(key, 1));
            }
        });

        glfwSetMouseButtonCallback(this.window, (window, button, action, mods) -> {
            switch(action) {
                case GLFW_PRESS -> this.eventCallback.onEvent(new MouseButtonPressedEvent(button));
                case GLFW_RELEASE ->
                        this.eventCallback.onEvent(new MouseButtonReleasedEvent(button));
            }
        });

        glfwSetScrollCallback(this.window, (window, xOffset, yOffset) -> this.eventCallback.onEvent(
                new MouseScrolledEvent((float)xOffset, (float)yOffset)));
        glfwSetCursorPosCallback(this.window, (window, xPos, yPos) -> this.eventCallback.onEvent(
                new MouseMovedEvent((float)xPos, (float)yPos)));

        String iconPath = wp.getIconPath();

        if(iconPath != null) {
            this.setIcon(iconPath);
        }

        this.cursorVisible = true;
    }

    /**
     * On update.
     */
    public void onUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(this.window);
    }

    /**
     * Sets event callback.
     *
     * @param eventCallback the event callback
     */
    public void setEventCallback(EventCallback eventCallback) {
        this.eventCallback = eventCallback;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(this.window, title);
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Is vsync boolean.
     *
     * @return the boolean
     */
    public boolean isVsync() {
        return this.vsync;
    }

    /**
     * Sets vsync.
     *
     * @param enabled the enabled
     */
    public void setVsync(boolean enabled) {
        this.vsync = enabled;

        if(enabled) {
            glfwSwapInterval(1);
            return;
        }

        glfwSwapInterval(0);
    }

    /**
     * Gets native window.
     *
     * @return the native window
     */
    public long getNativeWindow() {
        return this.window;
    }

    /**
     * Sets icon.
     *
     * @param path the path
     */
    public void setIcon(String path) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            try(GLFWImage.Buffer icons = GLFWImage.malloc(1)) {
                ByteBuffer pixels32 = stbi_load(path, w, h, comp, 4);
                icons.position(0).width(w.get(0)).height(h.get(0)).pixels(pixels32);
                icons.position(0);
                glfwSetWindowIcon(this.window, icons);
                stbi_image_free(pixels32);
            }
        }
    }

    /**
     * Sets cursor visible.
     *
     * @param flag the flag
     */
    public void setCursorVisible(boolean flag) {
        if(flag == this.cursorVisible) {
            return;
        }

        if(!flag) {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        } else {
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }

        this.cursorVisible = flag;
    }

    /**
     * Sets cursor position.
     *
     * @param x the x
     * @param y the y
     */
    public void setCursorPosition(double x, double y) {
        glfwSetCursorPos(this.window, x, y);
    }

    /**
     * Is pinned to top boolean.
     *
     * @return the boolean
     */
    public boolean isPinnedToTop() {
        return this.pinnedToTop;
    }

    /**
     * Sets pinned to top.
     *
     * @param flag the flag
     */
    public void setPinnedToTop(boolean flag) {
        this.pinnedToTop = flag;
        glfwSetWindowAttrib(this.window, GLFW_FLOATING, flag ? GLFW_TRUE : GLFW_FALSE);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        glfwDestroyWindow(this.window);
    }

    /**
     * Should close boolean.
     *
     * @return the boolean
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(this.window);
    }
}
