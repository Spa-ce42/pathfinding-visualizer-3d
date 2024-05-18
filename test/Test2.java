import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.DoubleBuffer;
import org.lwjgl.system.MemoryStack;
import pfv.internal.CubeRenderer;
import pfv.internal.Layer;
import pfv.internal.PerspectiveCamera;
import pfv.internal.WindowProperties;
import pfv.internal.event.Event;
import pfv.internal.event.Input;
import pfv.internal.glfw.Window;
import pfv.internal.opengl.IndexBuffer;
import pfv.internal.opengl.Shader;
import pfv.internal.opengl.VertexArray;
import pfv.internal.opengl.VertexBuffer;

public class Test2 implements Layer {
    private static PFV pfv;
    private PerspectiveCamera camera;
    private Shader shader;
    private VertexArray va;
    private float cursorPosX;
    private float cursorPosY;

    @Override
    public void onAttach() {
        Window window = pfv.getWindow();

        camera = new PerspectiveCamera((float)window.getWidth() / window.getHeight());
        camera.addPosition(0, 0, -3);
    }

    @Override
    public void onDetach() {

    }

    private void getCursorPos(Window window) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer x = stack.mallocDouble(1);
            DoubleBuffer y = stack.mallocDouble(1);
            glfwGetCursorPos(window.getNativeWindow(), x, y);
            cursorPosX = (float)x.get(0);
            cursorPosY = (float)y.get(0);
        }
    }

    private void processInput(Window window, PerspectiveCamera camera) {
        float cpx = cursorPosX;
        float cpy = cursorPosY;
        getCursorPos(window);
        float cpxo = cursorPosX - cpx;
        float cpyo = cursorPosY - cpy;

        camera.addYaw(cpxo * 0.01f);
        camera.addPitch(-cpyo * 0.01f);
        camera.updateCameraVectors();

        if(glfwGetKey(window.getNativeWindow(), GLFW_KEY_W) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().mul(0.01f));
        }

        if(glfwGetKey(window.getNativeWindow(), GLFW_KEY_S) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().mul(-0.01f));
        }

        if(glfwGetKey(window.getNativeWindow(), GLFW_KEY_A) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(-0.01f));
        }

        if(glfwGetKey(window.getNativeWindow(), GLFW_KEY_D) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(0.01f));
        }

        Input input = Input.getInput();

        if(input.isKeyPressed0(GLFW_KEY_SPACE)) {
            camera.addPosition(0, 0.01f, 0);
        }

        if(input.isKeyPressed0(GLFW_KEY_LEFT_SHIFT)) {
            camera.addPosition(0, -0.01f, 0);
        }
    }

    @Override
    public void onUpdate(float ts) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Window window = pfv.getWindow();
        processInput(window, camera);
        window.setTitle("Pathfinding Visualizer: " + camera.getPosition() + "(" + camera.getYaw() + ", " + camera.getPitch() + ")");

        CubeRenderer.begin(camera);
        CubeRenderer.end();
    }

    @Override
    public void onEvent(Event e) {

    }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        pfv = new PFV(test2);
        WindowProperties wp = new WindowProperties();
        wp.setWidth(1280 * 2);
        wp.setHeight(720 * 2);
        wp.setTitle("Pathfinding Visualizer");
        wp.setVsync(false);
        pfv.initialize(wp);
        pfv.run();
    }
}
