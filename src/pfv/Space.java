package pfv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joml.Vector3i;

/**
 * The type Space.
 */
public class Space {
    /**
     * The constant AIR.
     */
    public static final byte AIR = 0;
    /**
     * The constant END.
     */
    public static final byte END = 1;
    /**
     * The constant START.
     */
    public static final byte START = 2;
    /**
     * The constant OBSTACLE.
     */
    public static final byte OBSTACLE = 3;

    private final Vector3i dimension;
    private final List<Vector3i> endCoordinates;
    private final List<Vector3i> startCoordinates;
    private final List<Vector3i> obstacleCoordinates;
    private final byte[][][] space;

    private static Vector3i readCoordinates(BufferedReader br) throws IOException {
        String[] s = br.readLine().split(",");
        return new Vector3i(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }

    /**
     * Instantiates a new Space.
     *
     * @param f the f
     */
    public Space(File f) {
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            this.endCoordinates = new ArrayList<>();
            this.startCoordinates = new ArrayList<>();
            this.obstacleCoordinates = new ArrayList<>();

            // dimension
            this.dimension = readCoordinates(br);
            this.space = new byte[this.dimension.x][this.dimension.y][this.dimension.z];
            int endCount = Integer.parseInt(br.readLine());

            for(int i = 0; i < endCount; ++i) {
                Vector3i v = readCoordinates(br);
                this.space[v.x][v.y][v.z] = END;
                this.endCoordinates.add(v);
            }

            int startCount = Integer.parseInt(br.readLine());

            for(int i = 0; i < startCount; ++i) {
                Vector3i v = readCoordinates(br);
                this.space[v.x][v.y][v.z] = START;
                this.startCoordinates.add(v);
            }

            int obstacleCount = Integer.parseInt(br.readLine());

            for(int i = 0; i < obstacleCount; ++i) {
                Vector3i v = readCoordinates(br);
                this.space[v.x][v.y][v.z] = OBSTACLE;
                this.obstacleCoordinates.add(v);
            }
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
   }

    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public Vector3i getDimension() {
        return new Vector3i(this.dimension);
   }

    /**
     * Ends list.
     *
     * @return the list
     */
    public List<Vector3i> ends() {
        return Collections.unmodifiableList(this.endCoordinates);
   }

    /**
     * Starts list.
     *
     * @return the list
     */
    public List<Vector3i> starts() {
        return Collections.unmodifiableList(this.startCoordinates);
    }

    /**
     * Obstacles list.
     *
     * @return the list
     */
    public List<Vector3i> obstacles() {
        return Collections.unmodifiableList(this.obstacleCoordinates);
    }

    /**
     * Gets point.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the point
     */
    public byte getPoint(int x, int y, int z) {
        return this.space[x][y][z];
    }

    /**
     * Is valid empty coordinates boolean.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return the boolean
     */
    public boolean isValidEmptyCoordinates(int x, int y, int z) {
        if(x < 0 || x >= this.dimension.x) {
            return false;
        }

        if(y < 0 || y >= this.dimension.y) {
            return false;
        }

        if(z < 0 || z >= this.dimension.z) {
            return false;
        }

        return this.space[x][y][z] == AIR || this.space[x][y][z] == END;
    }

    private void addIfValid(List<Vector3i> neighbors, int x, int y, int z) {
        if(isValidEmptyCoordinates(x, y, z)) {
            neighbors.add(new Vector3i(x, y, z));
        }
    }

    /**
     * Gets neighbors.
     *
     * @param point the point
     * @return the neighbors
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public List<Vector3i> getNeighbors(Vector3i point) {
        List<Vector3i> neighbors = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        int z = point.z;
//        this.addIfValid(neighbors, x - 1, y - 1, z - 1);
        this.addIfValid(neighbors, x - 1, y - 1, z + 0);
//        this.addIfValid(neighbors, x - 1, y - 1, z + 1);
        this.addIfValid(neighbors, x - 1, y + 0, z - 1);
        this.addIfValid(neighbors, x - 1, y + 0, z + 0);
        this.addIfValid(neighbors, x - 1, y + 0, z + 1);
//        this.addIfValid(neighbors, x - 1, y + 1, z - 1);
        this.addIfValid(neighbors, x - 1, y + 1, z + 0);
//        this.addIfValid(neighbors, x - 1, y + 1, z + 1);
        this.addIfValid(neighbors, x + 0, y - 1, z - 1);
        this.addIfValid(neighbors, x + 0, y - 1, z + 0);
        this.addIfValid(neighbors, x + 0, y - 1, z + 1);
        this.addIfValid(neighbors, x + 0, y + 0, z - 1);
        //this.addIfValid(neighbors, x + 0, y + 0, z + 0);
        this.addIfValid(neighbors, x + 0, y + 0, z + 1);
        this.addIfValid(neighbors, x + 0, y + 1, z - 1);
        this.addIfValid(neighbors, x + 0, y + 1, z + 0);
        this.addIfValid(neighbors, x + 0, y + 1, z + 1);
//        this.addIfValid(neighbors, x + 1, y - 1, z - 1);
        this.addIfValid(neighbors, x + 1, y - 1, z + 0);
//        this.addIfValid(neighbors, x + 1, y - 1, z + 1);
        this.addIfValid(neighbors, x + 1, y + 0, z - 1);
        this.addIfValid(neighbors, x + 1, y + 0, z + 0);
        this.addIfValid(neighbors, x + 1, y + 0, z + 1);
//        this.addIfValid(neighbors, x + 1, y + 1, z - 1);
        this.addIfValid(neighbors, x + 1, y + 1, z + 0);
//        this.addIfValid(neighbors, x + 1, y + 1, z + 1);
        return neighbors;
    }
}
