import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FruchtermanReingold {
    private final Graph<Integer> g;
    private float temp;

    private final Vector center;


    public FruchtermanReingold(Graph<Integer> g, float width, float height) {
        this.g = g;

        temp = 20f * (float)(Math.sqrt(g.size()));

        center = new Vector (width / 2, height /2);
    }

    public void FruchtermanReingoldIteration(Map<Integer, Vector> position, float k) {
        Map<Integer, Vector> movement = new HashMap<>();
        //Fill movement with zero vectors
        for (int node : g.getVertices())
            movement.put(node, Vector.zero());

        //Will add each visited node to the set to prevent repeating operations
        Set<Integer> visited = new HashSet<>();

        for (int nodeID : g.getVertices()) {
            visited.add(nodeID);
            for (int otherNodeID : g.getVertices()) {
                if (visited.contains(otherNodeID))
                    continue;

                //Repulsion force
                Vector delta = position.get(nodeID).minus(position.get(otherNodeID));
                float distance = delta.norm();

                //Ignore any distances greater than 1000
                if (distance > 1000f)
                    continue;
                float repulsion = k * k/ distance;

                //Update the movement value with movement[nodeID] += delta * repulsion / distance and
                //movement[otherNodeID] -= delta * repulsion / distance
                movement.put(nodeID, movement.get(nodeID).plus(delta.times(repulsion / distance)));
                movement.put(otherNodeID, movement.get(otherNodeID).minus(delta.times(repulsion / distance)));


            }
            //Attraction between edges
            for (int adjNodeID : g.getNeighbors(nodeID)) {
                Vector delta = position.get(nodeID).minus(position.get(adjNodeID));
                float distance = delta.norm();

                if(distance == 0)
                    continue;

                float attraction = distance * distance / k;

                movement.put(nodeID, movement.get(nodeID).minus(delta.times(attraction / distance)));
                movement.put(adjNodeID, movement.get(adjNodeID).plus(delta.times(attraction / distance)));
            }
            //Gravitation attraction to center
            Vector delta = position.get(nodeID).minus(center);
            float distance = delta.norm();

            if (distance < 200f)
                continue;

            float gravity = distance * distance / k;

            movement.put(nodeID, movement.get(nodeID).minus(delta.times(gravity / distance)));

        }
        //Apply movement to each node, capped by temperature
        for (int nodeID : g.getVertices()) {
            float mvmntNorm = movement.get(nodeID).norm();

            //if < 1.0 not worth computing
            if (mvmntNorm < 1.0)
                continue;
            float cappedMvmntNorm = Math.min(mvmntNorm, temp);

            Vector cappedMvment = movement.get(nodeID).times(cappedMvmntNorm / mvmntNorm);

            position.put(nodeID, position.get(nodeID).plus(cappedMvment));

        }

        //Turn down the temp!
        if (temp > 2.0)
            temp *= 0.85f;
        else
            temp = 1.5f;
    }

}
