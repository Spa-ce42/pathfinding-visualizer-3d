package pfv.internal;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import pfv.internal.opengl.IndexBuffer;
import pfv.internal.opengl.Shader;
import pfv.internal.opengl.VertexArray;
import pfv.internal.opengl.VertexBuffer;

public class CubeRenderer {
    private static Shader shader;
    private static boolean initialized;
    private static int count;
    private static FloatBuffer colors;
    private static FloatBuffer transformations;
    private static VertexArray va;
    private static PerspectiveCamera pc;

    public static void initialize(int count) {
        if(initialized) {
            shader.dispose();
            MemoryUtil.memFree(colors);
            MemoryUtil.memFree(transformations);
            va.dispose();
        }

        CubeRenderer.count = count;
        va = new VertexArray();

        va.addVertexBuffer(new VertexBuffer(new float[] {
                0, 0, 0,
                1, 0, 0,
                1, 0, 1,
                0, 0, 1,
                0, 1, 0,
                1, 1, 0,
                0, 1, 1,
                1, 1, 1,
        }));
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glVertexAttribDivisor(0, 1);

        colors = BufferUtils.createFloatBuffer(count * 3);
        for(int i = 0; i < 3 * count; i = i + 3) {
            colors.put(i + 0, 1);
            colors.put(i + 1, 1);
            colors.put(i + 2, 1);
        }
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glVertexAttribDivisor(1, 1);

        transformations = BufferUtils.createFloatBuffer(count * 16);
        Matrix4f mat4f = new Matrix4f();
        for(int i = 0; i < 16 * count; i = i + 16) {
            mat4f.get(i, transformations);
        }

        va.addVertexBuffer(new VertexBuffer(transformations));
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 16, GL_FLOAT, false, 16 * Float.BYTES, 0);
        glVertexAttribDivisor(2, 1);

        va.setIndexBuffer(new IndexBuffer(new int[] {
                0, 1, 5,
                0, 4, 5,
                1, 2, 7,
                1, 5, 7,
                4, 5, 7,
                4, 6, 7,
                0, 1, 2,
                0, 3, 2,
                0, 3, 6,
                0, 4, 6,
                2, 3, 6,
                2, 6, 7
        }));

        shader = new Shader(
                """
                #version 330 core
                
                layout(location = 0) in vec3 a_Pos;
                layout(location = 1) in vec3 a_Color;
                layout(location = 2) in mat4 a_Transformation;
                
                uniform mat4 u_View;
                uniform mat4 u_Projection;
                
                out vec3 m_Color;
                
                void main() {
                    gl_Position = u_Projection * u_View * vec4(a_Pos, 1);
                    m_Color = a_Color;
                }
                """,
                """
                #version 330 core
                
                in vec3 m_Color;
                out vec4 gl_Color;
                
                void main() {
                    gl_Color = vec4(1, 1, 1, 1);
                }
                """
        );

        initialized = true;
    }

    public static void begin(PerspectiveCamera pc) {
        CubeRenderer.pc = pc;
    }

    public static void end() {
        shader.bind();
        shader.setMatrix4f("u_View", pc.getView());
        shader.setMatrix4f("u_Projection", pc.getProjection());
        va.bind();
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
    }
}
