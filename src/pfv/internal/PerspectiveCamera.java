package pfv.internal;

import static org.joml.Math.PI;
import static org.joml.Math.cos;
import static org.joml.Math.sin;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

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

    public Matrix4f getView() {
        this.updateCameraVectors();
        return new Matrix4f().lookAt(this.position, new Vector3f(this.position).add(this.front), this.up);
    }

    public Matrix4f getProjection() {
        return new Matrix4f().perspective(this.fov, this.aspectRatio, this.nearClip, this.farClip);
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void addPosition(float x, float y, float z) {
        this.position.x = this.position.x + x;
        this.position.y = this.position.y + y;
        this.position.z = this.position.z + z;
    }

    public void addPosition(Vector3f v) {
        this.addPosition(v.x, v.y, v.z);
    }

    public Vector3f getPosition() {
        return new Vector3f(this.position);
    }

    public void setYaw(float rad) {
        this.yaw = rad;
    }

    public void addYaw(float rad) {
        this.yaw = this.yaw + rad;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setPitch(float rad) {
        this.pitch = rad;
    }

    public void addPitch(float rad) {
        this.pitch = this.pitch + rad;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Vector3f getFront() {
        return new Vector3f(this.front);
    }

    public void updateCameraVectors() {
        this.front = new Vector3f(
                cos(this.yaw) * cos(this.pitch),
                sin(this.pitch),
                sin(this.yaw) * cos(this.pitch)
        ).normalize();
        this.right = new Vector3f(this.front).cross(this.worldUp).normalize();
        this.up = new Vector3f(this.right).cross(this.front).normalize();
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public float getNearClip() {
        return nearClip;
    }

    public void setNearClip(float nearClip) {
        this.nearClip = nearClip;
    }

    public float getFarClip() {
        return farClip;
    }

    public void setFarClip(float farClip) {
        this.farClip = farClip;
    }

    public Vector3f getUp() {
        return new Vector3f(this.up);
    }
}
