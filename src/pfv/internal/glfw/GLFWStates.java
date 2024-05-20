package pfv.internal.glfw;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.lwjgl.system.MemoryUtil;

/**
 * The type Glfw states.
 */
public class GLFWStates {
    private static boolean initialized;

    private GLFWStates() {
        throw new AssertionError();
    }

    /**
     * Initialize.
     */
    public static void initialize() {
        if(initialized) {
            return;
        }

        initialized = glfwInit();

        if(!initialized) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        glfwSetErrorCallback((error, description) -> {
            String s = MemoryUtil.memUTF8(description);
            throw new IllegalStateException("GLFW Error " + error + ": " + s);
        });
    }
}
