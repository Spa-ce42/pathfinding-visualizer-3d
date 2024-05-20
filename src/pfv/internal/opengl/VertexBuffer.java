package pfv.internal.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.nglBufferData;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryUtil;

/**
 * The type Vertex buffer.
 */
public class VertexBuffer {
    private final int handle;

    /**
     * Instantiates a new Vertex buffer.
     *
     * @param f the f
     */
    public VertexBuffer(float[] f) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
    }

    /**
     * Instantiates a new Vertex buffer.
     *
     * @param fb the fb
     */
    public VertexBuffer(FloatBuffer fb) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
    }

    /**
     * Sets data.
     *
     * @param fb the fb
     */
    public void setData(FloatBuffer fb) {
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
    }

    /**
     * Sets data.
     *
     * @param fb   the fb
     * @param size the size
     */
    public void setData(FloatBuffer fb, int size) {
        this.bind();
        nglBufferData(GL_ARRAY_BUFFER, (long)size * Float.BYTES, MemoryUtil.memAddress(fb), GL_STATIC_DRAW);
    }

    /**
     * Bind.
     */
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, this.handle);
    }

    /**
     * Unbind.
     */
    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        glDeleteBuffers(this.handle);
    }
}
