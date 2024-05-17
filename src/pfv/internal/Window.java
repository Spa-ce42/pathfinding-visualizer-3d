package pfv.internal;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

public class Window {
    private final long handle;
    private int clearOption;
    private int width, height;

    public Window(int width, int height, String title, boolean debug) {
        if(debug) {
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        }

        this.width = width;
        this.height = height;
        this.handle = glfwCreateWindow(width, height, title, NULL, NULL);
        glfwMakeContextCurrent(this.handle);
        GL.createCapabilities();

        if(debug) {
            glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
                throw new IllegalStateException(MemoryUtil.memUTF8(message));
            }, 0);
        }

        this.clearOption = GL_COLOR_BUFFER_BIT;

        glfwSetWindowSizeCallback(this.handle, (window, width1, height1) -> {
            this.width = width1;
            this.height = height1;
        });
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(this.handle);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void swapBuffers() {
        glfwSwapBuffers(this.handle);
    }

    public void dispose() {
        glfwDestroyWindow(this.handle);
    }

    public void setClearOption(int mask) {
        this.clearOption = mask;
    }

    public void clear() {
        glClear(this.clearOption);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        glfwSetWindowSize(this.handle, this.width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        glfwSetWindowSize(this.handle, this.width, this.height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(this.handle, this.width, this.height);
    }

    public float getAspectRatio() {
        return (float)this.width / (float)this.height;
    }

    public long getHandle() {
        return this.handle;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(this.handle, title);
    }

    static {
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }
    }
}
