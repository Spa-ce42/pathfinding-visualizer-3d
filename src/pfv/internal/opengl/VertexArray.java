package pfv.internal.opengl;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Vertex array.
 */
public class VertexArray {
    private final int handle;
    private final List<VertexBuffer> vertexBuffers;
    private IndexBuffer indexBuffer;

    /**
     * Instantiates a new Vertex array.
     */
    public VertexArray() {
        this.handle = glGenVertexArrays();
        this.bind();
        this.vertexBuffers = new ArrayList<>();
    }

    /**
     * Add vertex buffer.
     *
     * @param vb the vb
     */
    public void addVertexBuffer(VertexBuffer vb) {
        this.bind();
        this.vertexBuffers.add(vb);
    }

    /**
     * Sets index buffer.
     *
     * @param ib the ib
     */
    public void setIndexBuffer(IndexBuffer ib) {
        this.bind();
        this.indexBuffer = ib;
    }

    /**
     * Bind.
     */
    public void bind() {
        glBindVertexArray(this.handle);
    }

    /**
     * Unbind.
     */
    public void unbind() {
        glBindVertexArray(0);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        for(VertexBuffer vb : vertexBuffers) {
            vb.dispose();
        }

        this.indexBuffer.dispose();
    }
}
