package pfv.internal.event;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class Input {
    private static Input input;
    private final long window;

    public Input(long window) {
        this.window = window;
    }

    public boolean isKeyPressed0(int keycode) {
        int state = glfwGetKey(window, keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    public boolean isKeyReleased0(int keycode) {
        int state = glfwGetKey(window, keycode);
        return state == GLFW_RELEASE;
    }

    public boolean isMouseButtonPressed0(int button) {
        int state = glfwGetMouseButton(window, button);
        return state == GLFW_PRESS;
    }

    public boolean isMouseButtonReleased0(int button) {
        int state = glfwGetMouseButton(window, button);
        return state == GLFW_RELEASE;
    }

    public float getMouseX0() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return (float)x[0];
    }

    public float getMouseY0() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return (float)y[0];
    }

    public static void setInput(Input input) {
        Input.input = input;
    }

    public static Input getInput() {
        return Input.input;
    }
}
