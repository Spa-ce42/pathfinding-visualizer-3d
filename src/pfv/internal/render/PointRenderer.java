package pfv.internal.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.FloatBuffer;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import pfv.internal.PerspectiveCamera;
import pfv.internal.opengl.IndexBuffer;
import pfv.internal.opengl.Shader;
import pfv.internal.opengl.VertexArray;
import pfv.internal.opengl.VertexBuffer;

public class PointRenderer {
    private static Shader shader;
    private static boolean initialized;
    private static int maxCount;
    private static int count;
    private static FloatBuffer colors;
    private static VertexBuffer colorsVertexBuffer;
    private static int colorsPointer;
    private static FloatBuffer coordinates;
    private static VertexBuffer coordinatesVertexBuffer;
    private static int coordinatesPointer;
    private static VertexArray va;
    private static PerspectiveCamera pc;

    public static void initialize(int count) {
        if(initialized) {
            shader.dispose();
            MemoryUtil.memFree(colors);
            MemoryUtil.memFree(coordinates);
            va.dispose();
        }

        PointRenderer.maxCount = count;
        va = new VertexArray();

        colors = BufferUtils.createFloatBuffer(count * 3);
        for(int i = 0; i < 3 * count; i = i + 3) {
            colors.put(i + 0, 1);
            colors.put(i + 1, 1);
            colors.put(i + 2, 1);
        }
        colorsVertexBuffer = new VertexBuffer(colors);
        va.addVertexBuffer(colorsVertexBuffer);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glVertexAttribDivisor(0, 1);

        coordinates = BufferUtils.createFloatBuffer(count * 6);
        coordinatesVertexBuffer = new VertexBuffer(coordinates);
        va.addVertexBuffer(coordinatesVertexBuffer);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glVertexAttribDivisor(1, 1);

        va.setIndexBuffer(new IndexBuffer(new int[] {
                0
        }));

        shader = new Shader("""
        #version 330 core
        
        layout(location = 0) in vec3 a_Color;
        layout(location = 1) in vec3 a_Pos;
        
        uniform mat4 u_View;
        uniform mat4 u_Projection;
        
        out vec4 m_Color;
        
        void main() {
            gl_Position = u_Projection * u_View * vec4(a_Pos, 1);
            m_Color = vec4(a_Color, 1);
        }
        """,
        """
        #version 330 core
                
        in vec4 m_Color;
        out vec4 color;
                
        void main() {
            color = m_Color;
        }
        """);

        initialized = true;
    }

    public static void begin(PerspectiveCamera pc) {
        PointRenderer.pc = pc;
        colorsPointer = 0;
        coordinatesPointer = 0;
        count = 0;
    }

    public static void drawPoint(Vector3f color, Vector3f pos) {
        if(count >= maxCount) {
            end();
            begin(pc);
        }

        color.get(colorsPointer, colors);
        colorsPointer = colorsPointer + 3;
        pos.get(coordinatesPointer, coordinates);
        coordinatesPointer = coordinatesPointer + 3;
        ++count;
    }

    public static void end() {
        shader.bind();
        shader.setMatrix4f("u_View", pc.getView());
        shader.setMatrix4f("u_Projection", pc.getProjection());
        va.bind();
        colorsVertexBuffer.setData(colors, colorsPointer);
        coordinatesVertexBuffer.setData(coordinates, coordinatesPointer);
        glDrawElementsInstanced(GL_POINTS, 1, GL_UNSIGNED_INT, 0, count);
    }
}
