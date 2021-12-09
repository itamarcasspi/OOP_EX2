
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import java.util.Collections;

import java.io.FileWriter;
import java.io.IOException;
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
        prevList.add(rawGraph.getNode(dest));
        int[] sorted_path = new int[prev.length];

        for (int i = dest; i!=src; i = prev[i]) {
            prevList.add(rawGraph.getNode(prev[i]));
        }

        Collections.reverse(prevList);

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
        JSONObject json = new JSONObject();
        JSONArray e = new JSONArray();
        JSONArray v = new JSONArray();


        Iterator<NodeData> NI = rawGraph.nodeIter();
        while (NI.hasNext()) {
            NodeData currNode = NI.next();
            JSONObject jsonNode = new JSONObject();
            jsonNode.put("pos", "" + currNode.getLocation().x() + "," + currNode.getLocation().y() + "," + currNode.getLocation().z());
            jsonNode.put("id", currNode.getKey());
            v.put(jsonNode);
        }
        Iterator<EdgeData> NJ = rawGraph.edgeIter();
        while (NJ.hasNext()) {
            EdgeData currEdge = NJ.next();
            JSONObject jsonEdge = new JSONObject();
            jsonEdge.put("src", currEdge.getSrc());
            jsonEdge.put("w", currEdge.getWeight());
            jsonEdge.put("dest", currEdge.getDest());
            e.put(jsonEdge);
        }
        json.put("Edges", e);
        json.put("Nodes", v);

        try {
            FileWriter Files = new FileWriter(file);
            Files.write(json.toString());
            Files.close();
        }catch (IOException f) {
            f.printStackTrace();
            return false;
        }
        return true;

    }



    @Override
    public boolean load(String file) {
        DirectedWeightedGraph new_graph = new Directed_Weighted_Graph();

        return false;
    }
}