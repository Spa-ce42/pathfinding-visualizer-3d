package pfv.internal;

import static org.joml.Math.PI;
import static org.joml.Math.cos;
import static org.joml.Math.sin;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/**
 * The type Perspective camera.
 */
public class PerspectiveCamera {
    private final Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private Vector3f right;
    private final Vector3f worldUp;
    private float yaw;
    private float pitch;

    private float fov;
    private float aspectRatio;
    private float nearClip;
    private float farClip;

    /**
     * Instantiates a new Perspective camera.
     *
     * @param aspectRatio the aspect ratio
     */
    public PerspectiveCamera(float aspectRatio) {
        this.position = new Vector3f();
        this.up = new Vector3f(0, 1, 0);
        this.front = new Vector3f(0, 0, -1);
        this.worldUp = new Vector3f(up);
        this.yaw = 0;
        this.pitch = 0;
        this.updateCameraVectors();

        this.fov = (float)(PI / 2);
        this.aspectRatio = aspectRatio;
        this.nearClip = 0.001f;
        this.farClip = 1000;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    public Matrix4f getView() {
        this.updateCameraVectors();
        return new Matrix4f().lookAt(this.position, new Vector3f(this.position).add(this.front), this.up);
    }

    /**
     * Gets projection.
     *
     * @return the projection
     */
    public Matrix4f getProjection() {
        return new Matrix4f().perspective(this.fov, this.aspectRatio, this.nearClip, this.farClip);
    }

    /**
     * Sets position.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     * Add position.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public void addPosition(float x, float y, float z) {
        this.position.x = this.position.x + x;
        this.position.y = this.position.y + y;
        this.position.z = this.position.z + z;
    }

    /**
     * Add position.
     *
     * @param v the v
     */
    public void addPosition(Vector3f v) {
        this.addPosition(v.x, v.y, v.z);
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Vector3f getPosition() {
        return new Vector3f(this.position);
    }

    /**
     * Sets yaw.
     *
     * @param rad the rad
     */
    public void setYaw(float rad) {
        this.yaw = rad;
    }

    /**
     * Add yaw.
     *
     * @param rad the rad
     */
    public void addYaw(float rad) {
        this.yaw = this.yaw + rad;
    }

    /**
     * Gets yaw.
     *
     * @return the yaw
     */
    public float getYaw() {
        return this.yaw;
    }

    /**
     * Sets pitch.
     *
     * @param rad the rad
     */
    public void setPitch(float rad) {
        this.pitch = rad;
    }

    /**
     * Add pitch.
     *
     * @param rad the rad
     */
    public void addPitch(float rad) {
        this.pitch = this.pitch + rad;
    }

    /**
     * Gets pitch.
     *
     * @return the pitch
     */
    public float getPitch() {
        return this.pitch;
    }

    /**
     * Gets front.
     *
     * @return the front
     */
    public Vector3f getFront() {
        return new Vector3f(this.front);
    }

    /**
     * Update camera vectors.
     */
    public void updateCameraVectors() {
        this.front = new Vector3f(
                cos(this.yaw) * cos(this.pitch),
                sin(this.pitch),
                sin(this.yaw) * cos(this.pitch)
        ).normalize();
        this.right = new Vector3f(this.front).cross(this.worldUp).normalize();
        this.up = new Vector3f(this.right).cross(this.front).normalize();
    }

    /**
     * Gets fov.
     *
     * @return the fov
     */
    public float getFov() {
        return fov;
    }

    /**
     * Sets fov.
     *
     * @param fov the fov
     */
    public void setFov(float fov) {
        this.fov = fov;
    }

    /**
     * Gets aspect ratio.
     *
     * @return the aspect ratio
     */
    public float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Sets aspect ratio.
     *
     * @param aspectRatio the aspect ratio
     */
    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * Gets near clip.
     *
     * @return the near clip
     */
    public float getNearClip() {
        return nearClip;
    }

    /**
     * Sets near clip.
     *
     * @param nearClip the near clip
     */
    public void setNearClip(float nearClip) {
        this.nearClip = nearClip;
    }

    /**
     * Gets far clip.
     *
     * @return the far clip
     */
    public float getFarClip() {
        return farClip;
    }

    /**
     * Sets far clip.
     *
     * @param farClip the far clip
     */
    public void setFarClip(float farClip) {
        this.farClip = farClip;
    }

    /**
     * Gets up.
     *
     * @return the up
     */
    public Vector3f getUp() {
        return new Vector3f(this.up);
    }
}
