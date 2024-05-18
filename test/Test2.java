import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import pfv.PointInformation;
import pfv.Space;
import pfv.internal.WindowProperties;
import pfv.internal.render.CubeRenderer;

public class Test2 extends PFV {
    @Override
    public void onAttach() {
        super.space = new Space(new File("beesetup3.txt"));
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

    @Override
    public void run() {
        Vector3i start = this.space.starts().getFirst();
        Vector3i end = this.space.ends().getFirst();
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
                CubeRenderer.drawCube(new Vector3f(1, 1, 1), new Matrix4f().translate(v.x, v.y, v.z));
            }

            CubeRenderer.end();
            end();

            delay(25);

            if(point.equals(end)) {
                this.fullPath = this.reconstructPath(cameFrom, point);
                return;
            }

            closedSet.add(point);
            for(Vector3i neighbor : space.getNeighbors(point)) {
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
    public void stable() {
        CubeRenderer.begin(super.camera);

        for(Vector3i v : this.fullPath) {
            CubeRenderer.drawCube(new Vector3f(1, 1, 1), new Matrix4f().translate(v.x, v.y, v.z));
        }

        CubeRenderer.end();
    }

    public static void main(String[] args) {
        PFV pfv = new Test2();
        WindowProperties wp = new WindowProperties();
        wp.setWidth(1280 * 2);
        wp.setHeight(720 * 2);
        wp.setTitle("Pathfinding Visualizer (Hold left alt to make cursor visible)");
        wp.setVsync(false);
        pfv.initialize(wp);
        pfv.start();
    }
}
