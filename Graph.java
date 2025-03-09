import java.util.*;

public class Graph<V> {

    // Adjacency Map implementation
    private final Map<V, Map<V, Integer>> adjMap;

    public Graph() {
        adjMap = new HashMap<>();
    }

    /**
     * Adds a new vertex to the current graph if no such vertex already exists.
     * Returns true if the operation is successful and false otherwise
     *
     * @param vertexData the desired vertex to add
     * @return True if operation is successful and false otherwise
     */
    public boolean addVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException();

        boolean flag = false;

        if (!adjMap.containsKey(vertexData)) {
            adjMap.put(vertexData, new HashMap<>());
            flag = true;
        }
        return flag;
    }

    /**
     * Returns true if vertexData exists in the current graph, and false
     * otherwise return false
     *
     * @param vertexData a vertex in the graph
     * @return boolean
     */
    public boolean isVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException();

        return adjMap.containsKey(vertexData);
    }

    /**
     * Returns a collection containing all vertices of the current graph
     *
     * @return a collection of type V
     */
    public Collection<V> getVertices() {
        return adjMap.keySet();
    }

    /**
     * Adds a directed edge of desired weight between the specified srcVert and
     * destVert. If either specified vertices don't exist in the current graph,
     * then they should be created. Weights cannot be zero or negative, and
     * false will be returned if so. Additionally, an edge cannot be from a
     * vertex to itself, and will also return false.
     *
     * @param srcVert  the source vertex
     * @param destVert the destination vertex
     * @param weight   non-zero weight of the edge
     * @return True if the operation is successful and false otherwise
     */
    public boolean addEdge(V srcVert, V destVert, int weight) {
        if (srcVert == null || destVert == null)
            throw new IllegalArgumentException();

        if (weight <= 0 || srcVert.equals(destVert))
            return false;

        if (!adjMap.containsKey(srcVert))
            addVertex(srcVert);

        if (!adjMap.containsKey(destVert))
            addVertex(destVert);

        adjMap.get(srcVert).put(destVert, weight);

        return true;
    }

    /**
     * Returns the weight associated with the edge between srcVert and destVert.
     * Returns -1 if either of the vertices don't exist, or the associated edge
     * doesn't exist.
     *
     * @param srcVert  the source vertex
     * @param destVert the destination vertex
     * @return the weight associated with the edge, or -1 if no such edge
     *         exists.
     */
    public int getEdge(V srcVert, V destVert) {
        if (srcVert == null || destVert == null)
            throw new IllegalArgumentException();

        int weight = -1;

        if ((adjMap.containsKey(srcVert)
                && adjMap.get(srcVert).containsKey(destVert)))
            weight = adjMap.get(srcVert).get(destVert);
        else if ((adjMap.containsKey(destVert)
                && adjMap.get(destVert).containsKey(srcVert)))
            weight = adjMap.get(destVert).get(srcVert);

        return weight;
    }

    /**
     * Removes the specified edge from the current graph. Returns true if such
     * an edge exists and false otherwise
     *
     * @param srcVert  the source vertex
     * @param destVert the destination vertex
     * @return True if the operation is successful and false otherwise
     */
    public boolean removeEdge(V srcVert, V destVert) {
        if (srcVert == null || destVert == null)
            throw new IllegalArgumentException();

        boolean flag = false;

        if (adjMap.containsKey(srcVert)
                && adjMap.get(srcVert).containsKey(destVert)) {

            adjMap.get(srcVert).remove(destVert);

            flag = true;
        }

        return flag;
    }
    /**
     * Removes the desired vertex from the graph, and removes all associated
     * incoming and outgoing edges associated with that vertex. Returns true if
     * the desired vertex exists in the graph and is removed, and false
     * otherwise.
     *
     * @param vertexData the desired vertex to be removed
     * @return True if the operation is successful and false otherwise.
     */
    public boolean removeVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException();

        boolean flag = false;

        if (adjMap.containsKey(vertexData)) {
            adjMap.remove(vertexData);

            for (V v : adjMap.keySet()) {
                adjMap.get(v).remove(vertexData);

                flag = true;
            }
        }
        return flag;
    }
    /**
     * Returns a collection containing all neighboring vertices to vertexData.
     * Returns null if no such vertexData exists in the graph.
     *
     * @param vertexData the desired vertex
     * @return Collection of type V, or null if no such vertex exists
     */
    public Collection<V> getNeighbors(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException();

        if (adjMap.containsKey(vertexData))
            return adjMap.get(vertexData).keySet();
        else
            return null;
    }

    public int size() {
        return adjMap.size();
    }
    public int numberOfEdges() {
        int count = 0;
        for (Map m : adjMap.values()) {
            count += m.size();
        }
        return count;
    }

}
