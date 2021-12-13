
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

import static api.lib.parseJSONFile;

public class Directed_Weighted_Graph_Algorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph rawGraph;
    private DirectedWeightedGraph newGraph;
    private double[][][] pathData;
    boolean isConnected = true;
    private boolean pathCalculated = false;

    /**
     * sets the pathCalculated boolean to the given boolean
     **/
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

        HashMap<Integer, Boolean> visited = new HashMap<>();
        int[] nodes_id = new int[this.rawGraph.nodeSize()];
        LinkedList<Integer> q = new LinkedList<>();
        Iterator<NodeData> node = rawGraph.nodeIter();

        int i = 0;
        while (node.hasNext()) {
            NodeData current = node.next();
            nodes_id[i++] = current.getKey();

            visited.put(current.getKey(), false);
        }

        node = rawGraph.nodeIter();
        int current = node.next().getKey();
        visited.put(current, true);
        q.add(current);

        while (!q.isEmpty()) {
            current = q.poll();
            Iterator<EdgeData> edge_it = rawGraph.edgeIter(current);
            while (edge_it.hasNext()) {

                EdgeData current_edge = edge_it.next();
                if (!visited.get(current_edge.getDest())) {
                    visited.put(current_edge.getDest(), true);
                    q.add(current_edge.getDest());
                }

            }
        }

        return !visited.containsValue(false);
    }

    // dijkstra
    @Override
    public double shortestPathDist(int src, int dest) {
        if (rawGraph.getNode(src) == null || rawGraph.getNode(dest) == null) {
            return -1;
        }
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
        return dist[dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (rawGraph.getNode(src) == null || rawGraph.getNode(dest) == null) {
            return null;
        }
        double[] dist = new double[rawGraph.nodeSize()];
        int[] prev = new int[rawGraph.nodeSize() + 1];
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
        for (int i = dest; i != src; i = prev[i]) {
            prevList.add(rawGraph.getNode(prev[i]));
        }
        Collections.reverse(prevList);
        return prevList;
    }

    @Override
    public NodeData center() {
        int center_id = 0;
        double min_longest_path = Integer.MAX_VALUE;
        Iterator<NodeData> node_it = rawGraph.nodeIter();
        double[][] dist = new double[rawGraph.nodeSize()][rawGraph.nodeSize()];
        for (int i = 0; i < rawGraph.nodeSize(); i++) {
            for (int j = 0; j < rawGraph.nodeSize(); j++) {
                dist[i][j] = 2000000000;
                if (i == j) {
                    dist[i][j] = 0;
                }
            }
        }
        Iterator<EdgeData> edge_it = rawGraph.edgeIter();
        while (edge_it.hasNext()) {
            EdgeData current = edge_it.next();
            dist[current.getSrc()][current.getDest()] = current.getWeight();
        }
        for (int k = 0; k < rawGraph.nodeSize(); k++) {
            for (int j = 0; j < rawGraph.nodeSize(); j++) {
                for (int i = 0; i < rawGraph.nodeSize(); i++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < rawGraph.nodeSize(); i++) {
            double current_max_path = 0;
            for (int j = 0; j < rawGraph.nodeSize(); j++) {
                if (dist[i][j] > current_max_path) {
                    current_max_path = dist[i][j];
                }
            }
            if (current_max_path < min_longest_path) {
                min_longest_path = current_max_path;
                center_id = i;
            }
        }
        return rawGraph.getNode(center_id);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData> ans = new LinkedList<>();
        List<NodeData> original = new LinkedList<>(cities);
        List<NodeData> temp = new LinkedList<>();

        int closest_node = 0;
        double shortest_path = Double.MAX_VALUE;

        for(int z = 0; z<cities.size();z++) {

            double current_path_cost = 0;
            for (int i = z, t = 0; t < cities.size(); i = closest_node, t++) {
                double shortest_dist = Double.MAX_VALUE;

                if (cities.get(i) != null) {
                    for (int j = 0; j < cities.size(); j++) {
                        if (cities.get(j) == null || i == j) {
                            continue;
                        }
                        double curr_dist = shortestPathDist(cities.get(i).getKey(), cities.get(j).getKey());
                        if (curr_dist < shortest_dist) {
                            closest_node = j;
                            shortest_dist = curr_dist;
                        }
                    }
                    if (cities.get(i).getKey() != cities.get(closest_node).getKey()) {
                        List<NodeData> to_closest_node = shortestPath(cities.get(i).getKey(), cities.get(closest_node).getKey());
                        current_path_cost+=shortestPathDist(cities.get(i).getKey(), cities.get(closest_node).getKey());
                        cities.set(i, null);
                        for (int k = 0; k < to_closest_node.size(); k++) {
                            ans.add(to_closest_node.get(k));
                        }
                    }
                }


            }

            cities = new LinkedList<>(original);
            if(current_path_cost<shortest_path)
            {
                shortest_path = current_path_cost;
                temp = ans;
            }
            ans = new LinkedList<>();
        }
        for (int i = 0; i < temp.size() - 1; i++) {
            if (temp.get(i) == temp.get(i + 1)) {
                temp.remove(i);
            }
        }
        return temp;
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
        } catch (IOException f) {
            f.printStackTrace();
            return false;
        }
        return true;

    }


    @Override
    public boolean load(String file) {
        DirectedWeightedGraph new_graph = new Directed_Weighted_Graph();

        JSONObject jsonObject = null;
        try {
            jsonObject = parseJSONFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        JSONArray jsonNodes = jsonObject.getJSONArray("Nodes");
        for (int i = 0; i < jsonNodes.length(); i++) {
            int key = jsonNodes.getJSONObject(i).getInt("id");
            String pos = jsonNodes.getJSONObject(i).getString("pos");
            Node_Data v = new Node_Data(key, pos);
            new_graph.addNode(v);
        }

        JSONArray jsonEdges = jsonObject.getJSONArray("Edges");
        for (int i = 0; i < jsonEdges.length(); i++) {
            int src = jsonEdges.getJSONObject(i).getInt("src");
            int dest = jsonEdges.getJSONObject(i).getInt("dest");
            double w = jsonEdges.getJSONObject(i).getDouble("w");
            new_graph.connect(src, dest, w);
        }
        rawGraph = new Directed_Weighted_Graph((Directed_Weighted_Graph) new_graph);
        return true;
    }
}
