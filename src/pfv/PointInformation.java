package pfv;

import org.joml.Vector3i;

/**
 * The type Point information.
 */
public class PointInformation implements Comparable<PointInformation> {
    /**
     * The Point.
     */
    public final Vector3i point;
    /**
     * The F.
     */
    public final double f;
    /**
     * The G.
     */
    public final double g;
    /**
     * The H.
     */
    public final double h;

    /**
     * Instantiates a new Point information.
     *
     * @param start the start
     * @param point the point
     * @param end   the end
     */
    public PointInformation(Vector3i start, Vector3i point, Vector3i end) {
        this.g = start.distance(point);
        this.h = point.distance(end);
        this.f = this.g + this.h;
        this.point = point;
    }

    @Override
    public int compareTo(PointInformation o) {
        double delta = this.f - o.f;
        double absDelta = Math.abs(delta);

        if(0 < absDelta && absDelta < 1) {
            return delta > 0 ? 1 : -1;
        }

        return (int)delta;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof PointInformation c)) {
            return false;
        }

        return this.point.equals(c.point);
    }

    @Override
    public String toString() {
        return this.point.toString();
    }

}