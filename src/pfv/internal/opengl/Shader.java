package pfv.internal.opengl;

import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

/**
 * The type Shader.
 */
public class Shader {
    private final int program;

    /**
     * Instantiates a new Shader.
     *
     * @param vss the vss
     * @param fss the fss
     */
    public Shader(String vss, String fss) {
        this.program = glCreateProgram();
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vss);
        glCompileShader(vertexShader);


        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fss);
        glCompileShader(fragmentShader);

        glAttachShader(this.program, vertexShader);
        glAttachShader(this.program, fragmentShader);
        glLinkProgram(this.program);
        this.bind();
    }

    /**
     * Sets matrix 4 f.
     *
     * @param name     the name
     * @param matrix4f the matrix 4 f
     */
    public void setMatrix4f(String name, Matrix4f matrix4f) {
        int l = glGetUniformLocation(this.program, name);
        try(MemoryStack stack = MemoryStack.stackPush()) {
            glUniformMatrix4fv(l, false, matrix4f.get(stack.mallocFloat(16)));
        }
    }

    /**
     * Bind.
     */
    public void bind() {
        glUseProgram(this.program);
    }

    /**
     * Unbind.
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Dispose.
     */
    public void dispose() {
        glDeleteProgram(this.program);
    }
}
