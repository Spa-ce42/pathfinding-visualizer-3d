import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.DoubleBuffer;
import org.lwjgl.system.MemoryStack;
import pfv.internal.IndexBuffer;
import pfv.internal.PerspectiveCamera;
import pfv.internal.Shader;
import pfv.internal.VertexArray;
import pfv.internal.VertexBuffer;
import pfv.internal.Window;

public class Test {
    private static float cursorPosX;
    private static float cursorPosY;

    private static void processInput(Window window, PerspectiveCamera camera) {
        float cpx = cursorPosX;
        float cpy = cursorPosY;
        getCursorPos(window);
        float cpxo = cursorPosX - cpx;
        float cpyo = cursorPosY - cpy;

        camera.addYaw(cpxo * 0.01f);
        camera.addPitch(-cpyo * 0.01f);
        camera.updateCameraVectors();

        if(glfwGetKey(window.getHandle(), GLFW_KEY_W) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().mul(0.0001f));
        }

        if(glfwGetKey(window.getHandle(), GLFW_KEY_S) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().mul(-0.0001f));
        }

        if(glfwGetKey(window.getHandle(), GLFW_KEY_A) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(0.0001f));
        }

        if(glfwGetKey(window.getHandle(), GLFW_KEY_D) == GLFW_PRESS) {
            camera.addPosition(camera.getFront().cross(camera.getUp()).normalize().mul(-0.0001f));
        }
    }

    private static void getCursorPos(Window window) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer x = stack.mallocDouble(1);
            DoubleBuffer y = stack.mallocDouble(1);
            glfwGetCursorPos(window.getHandle(), x, y);
            cursorPosX = (float)x.get(0);
            cursorPosY = (float)y.get(0);
        }
    }

    public static void main(String[] args) {
        Window window = new Window(1280, 720, "Pathfinding Visualizer", true);
        PerspectiveCamera camera = new PerspectiveCamera(window.getAspectRatio());
        camera.addPosition(0, 0, -3);

        VertexArray va = new VertexArray();

        va.addVertexBuffer(new VertexBuffer(new float[] {
                0, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0
        }));

        va.setIndexBuffer(new IndexBuffer(new int[] {
                0, 1, 2
        }));

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        Shader shader = new Shader("""
        #version 330 core
        
        layout(location = 0) in vec3 a_Position;
        
        uniform mat4 view;
        uniform mat4 projection;
        
        void main() {
            gl_Position = projection * view * vec4(a_Position, 1);
        }
        """,
        """
        #version 330 core
        
        out vec4 color;
        
        void main() {
            color = vec4(0.8, 0.2, 0.3, 1);
        }
        """);

        shader.setMatrix4f("view", camera.getView());
        shader.setMatrix4f("projection", camera.getProjection());

        window.setClearOption(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        getCursorPos(window);

        while(!window.shouldClose()) {
            window.clear();

            processInput(window, camera);
            window.setTitle("Pathfinding Visualizer: " + camera.getPosition() + "(" + camera.getYaw() + ", " + camera.getPitch() + ")");

            shader.bind();
            shader.setMatrix4f("view", camera.getView());
            shader.setMatrix4f("projection", camera.getProjection());

            va.bind();
            glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);

            window.pollEvents();
            window.swapBuffers();
        }

        window.dispose();
    }
}
