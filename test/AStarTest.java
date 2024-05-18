import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import pfv.PointInformation;
import pfv.Space;
import pfv.internal.WindowProperties;
import pfv.internal.render.CubeRenderer;

public class AStarTest extends PFV {
    @Override
    public void onAttach() {
        super.space = new Space(new File("beesetup3.txt"));
        super.camera.setYaw((float)(Math.PI / 4));
        super.camera.setPosition(-10, 30, -10);
        super.camera.updateCameraVectors();
    }

    @Override
    public void onDetach() {

    }


    private List<Vector3i> reconstructPath(HashMap<Vector3i, Vector3i> cameFrom, Vector3i current) {
        List<Vector3i> totalPath = new ArrayList<>();
        totalPath.add(current);

        while(cameFrom.containsKey(current)) {
            current = cameFrom.get(current);

            if(totalPath.contains(current)) {
                continue;
            }

            totalPath.add(current);
        }

        Collections.reverse(totalPath);

        return totalPath;
    }

    private List<Vector3i> fullPath;

    private void pathFind(Vector3i start, Vector3i end) {
        PriorityQueue<PointInformation> openSet = new PriorityQueue<>();
        openSet.add(new PointInformation(start, start, end));
        HashMap<Vector3i, Vector3i> cameFrom = new HashMap<>();
        HashSet<Vector3i> closedSet = new HashSet<>();

        PointInformation pInfo = null;
        while(!openSet.isEmpty()) {

            pInfo = openSet.poll();
            Vector3i point = pInfo.point;

            begin();
            CubeRenderer.begin(this.camera);

            for(Vector3i v : reconstructPath(cameFrom, point)) {
                CubeRenderer.drawCube(new Vector3f(0, 0, 1), new Matrix4f().translate(v.x, v.y, v.z));
            }

            CubeRenderer.end();
            end();

            delay(40);

            if(point.equals(end)) {
                this.fullPath = this.reconstructPath(cameFrom, point);
                return;
            }

            closedSet.add(point);
            for(Vector3i neighbor : space.getNeighbors(point)) {
                begin();
                CubeRenderer.begin(this.camera);
                CubeRenderer.drawCube(new Vector3f(0, 1, 0), new Matrix4f().translate(neighbor.x, neighbor.y, neighbor.z));
                CubeRenderer.end();
                end();
                if(closedSet.contains(neighbor)) {
                    continue;
                }

                PointInformation nInfo = new PointInformation(start, neighbor, end);
                double tentativeGCost = pInfo.g + point.distance(neighbor);

                if(!openSet.contains(nInfo)) {
                    openSet.add(nInfo);
                } else if(tentativeGCost >= nInfo.g) {
                    continue;
                }

                cameFrom.put(neighbor, point);
            }
        }
    }

    @Override
    public void run() {
        List<Vector3i> starts = this.space.starts();
        List<Vector3i> ends = this.space.ends();

        for(int i = 0, n = starts.size(); i < n; ++i) {
            pathFind(starts.get(i), ends.get(i));

            long goal = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(1000);
            while(System.nanoTime() < goal) {
                begin();
                this.stable();
                end();
            }
        }
    }

    @Override
    public void stable() {
        CubeRenderer.begin(super.camera);

        for(Vector3i v : this.fullPath) {
            CubeRenderer.drawCube(new Vector3f(0, 1, 0), new Matrix4f().translate(v.x, v.y, v.z));
        }

        CubeRenderer.end();
    }

    public static void main(String[] args) {
        PFV pfv = new AStarTest();
        WindowProperties wp = new WindowProperties();
        wp.setWidth(1280 * 2);
        wp.setHeight(720 * 2);
        wp.setTitle("Pathfinding Visualizer (Hold left alt to make cursor visible)");
        wp.setVsync(false);
        pfv.initialize(wp);
        pfv.start();
    }
}
