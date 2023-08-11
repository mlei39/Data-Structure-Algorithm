import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Minkun
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 * Resources: none
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("we have null input or the start is not in the graph");
        }
        List<Vertex<T>> visit = new ArrayList<>();
        Set<Vertex<T>> vSet = new HashSet<>();
        Queue<Vertex<T>> q = new LinkedList<>();

        visit.add(start);
        vSet.add(start);
        q.add(start);
        while (!q.isEmpty()) {
            Vertex<T> temp = q.poll();
            List<VertexDistance<T>> adj = graph.getAdjList().get(temp);
            for (VertexDistance<T> w : adj) {
                if (!vSet.contains(w.getVertex())) {
                    visit.add(w.getVertex());
                    vSet.add(w.getVertex());
                    q.add(w.getVertex());
                }
            }
        }

        return visit;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("we have null input or the start is not in the graph");
        }
        List<Vertex<T>> visit = new ArrayList<>();
        Set<Vertex<T>> vSet = new HashSet<>();
        dfsH(start, graph, visit, vSet);
        return visit;
    }

    /**
     * This is the recursive helper function of dfs
     * @param curr the vertex that we are looking at
     * @param graph the graph that we want to perform the dfs on
     * @param visit an arraylist of the visited vertices, which is the return of the dfs method
     * @param vSet a hashset of the visited vertices, for achieving O(1) runtime of the contains method
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsH(Vertex<T> curr, Graph<T> graph, List<Vertex<T>> visit, Set<Vertex<T>> vSet) {
        visit.add(curr);
        vSet.add(curr);
        List<VertexDistance<T>> adj = graph.getAdjList().get(curr);
        for (VertexDistance<T> w : adj) {
            if (!vSet.contains(w.getVertex())) {
                dfsH(w.getVertex(), graph, visit, vSet);
            }
        }
        return;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("we have null input or the start is not in the graph");
        }
        Map<Vertex<T>, Integer> visit = new HashMap<>();
        Map<Vertex<T>, Integer> dm = new HashMap<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        for (Vertex<T> j : graph.getVertices()) {
            dm.put(j, Integer.MAX_VALUE);
        }

        pq.add(new VertexDistance<>(start, 0));
        int size = graph.getVertices().size();
        while (!pq.isEmpty() && visit.size() < size) {
            VertexDistance<T> temp = pq.poll();
            if (visit.get(temp.getVertex()) == null) {
                visit.put(temp.getVertex(), temp.getDistance());
                dm.put(temp.getVertex(), temp.getDistance());

                List<VertexDistance<T>> adj = graph.getAdjList().get(temp.getVertex());
                for (VertexDistance<T> w : adj) {
                    if (visit.get(w.getVertex()) == null) {
                        pq.add(new VertexDistance<>(w.getVertex(), temp.getDistance() + w.getDistance()));
                    }
                }
            }
        }

        return dm;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * You may assume that there will only be one valid MST that can be formed.
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * You should NOT allow self-loops or parallel edges into the MST.
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("we can't have a null input");
        }

        DisjointSet<T> ds = new DisjointSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        Queue<Edge<T>> pq = new PriorityQueue<>();
        for (Edge<T> j : graph.getEdges()) {
            pq.add(j);
        }
        while (!pq.isEmpty() && mst.size() < graph.getVertices().size() * 2 - 2) {
            Edge<T> temp = pq.poll();
            if (!ds.find(temp.getU()).equals(ds.find(temp.getV()))) {
                mst.add(temp);
                mst.add(new Edge<>(temp.getV(), temp.getU(), temp.getWeight()));
                ds.union(ds.find(temp.getU()), ds.find(temp.getV()));
            }
        }
        if (mst.size() < graph.getVertices().size() * 2 - 2) {
            return null;
        } else {
            return mst;
        }
    }
}
