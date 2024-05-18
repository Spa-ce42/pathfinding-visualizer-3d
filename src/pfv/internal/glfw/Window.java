package pfv.internal.glfw;

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
import static org.lwjgl.glfw.GLFW.glfwInitHint;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
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
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
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

public class Window {
    private final long window;
    private String title;
    private int width, height;
    private boolean vsync;
    private EventCallback eventCallback;
    private boolean pinnedToTop;

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
    }

    public void onUpdate() {
        glfwPollEvents();
        glfwSwapBuffers(this.window);
    }

    public void setEventCallback(EventCallback eventCallback) {
        this.eventCallback = eventCallback;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(this.window, title);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isVsync() {
        return this.vsync;
    }

    public void setVsync(boolean enabled) {
        this.vsync = enabled;

        if(enabled) {
            glfwSwapInterval(1);
            return;
        }

        glfwSwapInterval(0);
    }

    public long getNativeWindow() {
        return this.window;
    }

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

    public boolean isPinnedToTop() {
        return this.pinnedToTop;
    }

    public void setPinnedToTop(boolean flag) {
        this.pinnedToTop = flag;
        glfwSetWindowAttrib(this.window, GLFW_FLOATING, flag ? GLFW_TRUE : GLFW_FALSE);
    }

    public void dispose() {
        glfwDestroyWindow(this.window);
    }
}
