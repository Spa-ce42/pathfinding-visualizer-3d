package pfv.internal.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * The type Index buffer.
 */
public class IndexBuffer {
    private final int handle;

    /**
     * Instantiates a new Index buffer.
     *
     * @param i the
     */
    public IndexBuffer(int[] i) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
    }

    /**
     * Bind.
     */
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.handle);
    }

    /**
     * Unbind.
     */
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        glDeleteBuffers(handle);
    }
}
