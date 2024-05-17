package pfv.internal;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

public class VertexArray {
    private final int handle;
    private List<VertexBuffer> vertexBuffers;
    private IndexBuffer indexBuffer;

    public VertexArray() {
        this.handle = glGenVertexArrays();
        this.bind();
        this.vertexBuffers = new ArrayList<>();
    }

    public void addVertexBuffer(VertexBuffer vb) {
        this.bind();
        this.vertexBuffers.add(vb);
    }

    public void setIndexBuffer(IndexBuffer ib) {
        this.bind();
        this.indexBuffer = ib;
    }

    public void bind() {
        glBindVertexArray(this.handle);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}
