package pfv.internal.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class IndexBuffer {
    private final int handle;

    public IndexBuffer(int[] i) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, i, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.handle);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void dispose() {
        glDeleteBuffers(handle);
    }
}
