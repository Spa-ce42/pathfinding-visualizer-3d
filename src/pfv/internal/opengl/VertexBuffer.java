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

public class VertexBuffer {
    private final int handle;

    public VertexBuffer(float[] f) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
    }

    public VertexBuffer(FloatBuffer fb) {
        this.handle = glGenBuffers();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
    }

    public void setData(FloatBuffer fb) {
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
    }

    public void setData(FloatBuffer fb, int size) {
        this.bind();
        nglBufferData(GL_ARRAY_BUFFER, (long)size * Float.BYTES, MemoryUtil.memAddress(fb), GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, this.handle);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void dispose() {
        glDeleteBuffers(this.handle);
    }
}
