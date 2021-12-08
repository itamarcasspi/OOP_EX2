
import org.w3c.dom.Node;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.*;

public class Directed_Weighted_Graph_Algorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph rawGraph;
    private DirectedWeightedGraph newGraph;
    private double[][][] pathData;
    boolean isConnected = true;
    private boolean pathCalculated = false;

    /** sets the pathCalculated boolean to the given boolean **/
    public void setPathCalculated(boolean pathCalculated) {
        this.pathCalculated = pathCalculated;
        if (!pathCalculated)
            isConnected = true;
    }

    public Directed_Weighted_Graph_Algorithms(DirectedWeightedGraph g) {
        this.init(g);
    }

    public Directed_Weighted_Graph_Algorithms(String jsonFileName) {
        DirectedWeightedGraph g = new Directed_Weighted_Graph(jsonFileName);
        init(g);
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        rawGraph = new Directed_Weighted_Graph((Directed_Weighted_Graph) g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.rawGraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Directed_Weighted_Graph a_copy = new Directed_Weighted_Graph((Directed_Weighted_Graph) this.rawGraph);
        return a_copy;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    // dijkstra
    @Override
    public double shortestPathDist(int src, int dest) {
        double[] dist = new double[rawGraph.nodeSize()];
        double[] prev = new double[rawGraph.nodeSize()];
        dist[src] = 0;
        PriorityQueue<ComparableNode> q = new PriorityQueue<>();
        Iterator<NodeData> node_it = rawGraph.nodeIter();
        while (node_it.hasNext()) {
            NodeData current = node_it.next();
            if (src != current.getKey()) {
                dist[current.getKey()] = 20000000;
            }

        }
        ComparableNode comparable_src = new ComparableNode(rawGraph.getNode(src), 0);
        q.add(comparable_src);

        while (!q.isEmpty()) {
            ComparableNode u = q.remove();
            if (u.val <= dist[u.node.getKey()]) {
                dist[u.node.getKey()] = u.val;
                Iterator<EdgeData> edge_it = rawGraph.edgeIter(u.node.getKey());
                if (edge_it != null) {
                    while (edge_it.hasNext()) {
                        EdgeData edge = edge_it.next();
                        double alt = dist[u.node.getKey()] + edge.getWeight();
                        if (alt < dist[edge.getDest()]) {
                            ComparableNode v = new ComparableNode(rawGraph.getNode(edge.getDest()), alt);
                            dist[v.node.getKey()] = alt;
                            prev[v.node.getKey()] = u.node.getKey();
                            q.add(v);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < prev.length; i++) {
            System.out.println("path - " + prev[i]);
        }
        return dist[dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        double[] dist = new double[rawGraph.nodeSize()];
        int[] prev = new int[rawGraph.nodeSize()];
        dist[src] = 0;
        PriorityQueue<ComparableNode> q = new PriorityQueue<>();
        Iterator<NodeData> node_it = rawGraph.nodeIter();
        while (node_it.hasNext()) {
            NodeData current = node_it.next();
            if (src != current.getKey()) {
                dist[current.getKey()] = 20000000;
            }

        }
        ComparableNode comparable_src = new ComparableNode(rawGraph.getNode(src), 0);
        q.add(comparable_src);

        while (!q.isEmpty()) {
            ComparableNode u = q.remove();
            if (u.val <= dist[u.node.getKey()]) {
                dist[u.node.getKey()] = u.val;
                Iterator<EdgeData> edge_it = rawGraph.edgeIter(u.node.getKey());
                if (edge_it != null) {
                    while (edge_it.hasNext()) {
                        EdgeData edge = edge_it.next();
                        double alt = dist[u.node.getKey()] + edge.getWeight();
                        if (alt < dist[edge.getDest()]) {
                            ComparableNode v = new ComparableNode(rawGraph.getNode(edge.getDest()), alt);
                            dist[v.node.getKey()] = alt;
                            prev[v.node.getKey()] = u.node.getKey();

                            q.add(v);
                        }
                    }
                }
            }
        }
        List<NodeData> prevList = new LinkedList<>();

        return prevList;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
