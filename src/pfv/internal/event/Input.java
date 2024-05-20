package pfv.internal.event;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

/**
 * The type Input.
 */
public class Input {
    private static Input input;
    private final long window;

    /**
     * Instantiates a new Input.
     *
     * @param window the window
     */
    public Input(long window) {
        this.window = window;
    }

    /**
     * Is key pressed boolean.
     *
     * @param keycode the keycode
     * @return the boolean
     */
    public boolean isKeyPressed(int keycode) {
        int state = glfwGetKey(window, keycode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    /**
     * Is key released boolean.
     *
     * @param keycode the keycode
     * @return the boolean
     */
    public boolean isKeyReleased(int keycode) {
        int state = glfwGetKey(window, keycode);
        return state == GLFW_RELEASE;
    }

    /**
     * Is mouse button pressed boolean.
     *
     * @param button the button
     * @return the boolean
     */
    public boolean isMouseButtonPressed(int button) {
        int state = glfwGetMouseButton(window, button);
        return state == GLFW_PRESS;
    }

    /**
     * Is mouse button released boolean.
     *
     * @param button the button
     * @return the boolean
     */
    public boolean isMouseButtonReleased(int button) {
        int state = glfwGetMouseButton(window, button);
        return state == GLFW_RELEASE;
    }

    /**
     * Gets mouse x.
     *
     * @return the mouse x
     */
    public float getMouseX() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return (float)x[0];
    }

    /**
     * Gets mouse y.
     *
     * @return the mouse y
     */
    public float getMouseY() {
        double[] x = new double[1], y = new double[1];
        glfwGetCursorPos(window, x, y);
        return (float)y[0];
    }

    /**
     * Sets input.
     *
     * @param input the input
     */
    public static void setInput(Input input) {
        Input.input = input;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public static Input getInput() {
        return Input.input;
    }
}
