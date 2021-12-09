

import org.w3c.dom.Node;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import java.util.Collections;
import java.util.*;

public class Directed_Weighted_Graph_Algorithms implements DirectedWeightedGraphAlgorithms{
    DirectedWeightedGraph graph;
    public Directed_Weighted_Graph_Algorithms(DirectedWeightedGraph g)
    {
        this.init(g);
    }
    @Override
    public void init(DirectedWeightedGraph g) {
        graph = new Directed_Weighted_Graph((Directed_Weighted_Graph) g);
    }


    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        Directed_Weighted_Graph a_copy = new Directed_Weighted_Graph((Directed_Weighted_Graph) this.graph);
        return a_copy;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
    //dijkstra
    @Override
    public double shortestPathDist(int src, int dest) {
        double[] dist = new double[graph.nodeSize()];
        double[] prev = new double[graph.nodeSize()];
        HashMap<Integer,Integer> node_to_next = new HashMap<>();
        dist[src] = 0;
        PriorityQueue<ComparableNode> q = new PriorityQueue<>();
        Iterator<NodeData> node_it = graph.nodeIter();
        while(node_it.hasNext())
        {
            NodeData current = node_it.next();
            if(src!=current.getKey())
            {
                dist[current.getKey()] = 20000000;
            }

        }
        ComparableNode comparable_src = new ComparableNode(graph.getNode(src),0);
        q.add(comparable_src);


        while(!q.isEmpty())
        {
            ComparableNode u = q.remove();
            if(u.val <= dist[u.node.getKey()]) {
                dist[u.node.getKey()] = u.val;
                Iterator<EdgeData> edge_it = graph.edgeIter(u.node.getKey());
                if(edge_it!= null) {
                    while (edge_it.hasNext()) {
                        EdgeData edge = edge_it.next();
                        double alt = dist[u.node.getKey()] + edge.getWeight();
                        if (alt < dist[edge.getDest()]) {
                            ComparableNode v = new ComparableNode(graph.getNode(edge.getDest()), alt);
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
        double[] dist = new double[graph.nodeSize()];
        int[] prev = new int[graph.nodeSize()];
        dist[src] = 0;
        PriorityQueue<ComparableNode> q = new PriorityQueue<>();
        Iterator<NodeData> node_it = graph.nodeIter();
        while(node_it.hasNext())
        {
            NodeData current = node_it.next();
            if(src!=current.getKey())
            {
                dist[current.getKey()] = 20000000;
            }

        }
        ComparableNode comparable_src = new ComparableNode(graph.getNode(src),0);
        q.add(comparable_src);


        while(!q.isEmpty())
        {
            ComparableNode u = q.remove();
            if(u.val <= dist[u.node.getKey()]) {
                dist[u.node.getKey()] = u.val;
                Iterator<EdgeData> edge_it = graph.edgeIter(u.node.getKey());
                if(edge_it!= null) {
                    while (edge_it.hasNext()) {
                        EdgeData edge = edge_it.next();
                        double alt = dist[u.node.getKey()] + edge.getWeight();
                        if (alt < dist[edge.getDest()]) {
                            ComparableNode v = new ComparableNode(graph.getNode(edge.getDest()), alt);
                            dist[v.node.getKey()] = alt;
                            prev[v.node.getKey()] = u.node.getKey();

                            q.add(v);
                        }
                    }
                }
            }
        }
        List<NodeData> prevList = new LinkedList<>();
        prevList.add(graph.getNode(dest));
        int[] sorted_path = new int[prev.length];

        for (int i = dest; i!=src; i = prev[i]) {
            prevList.add(graph.getNode(prev[i]));
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
        return false;
    }

    @Override
    public boolean load(String file) {
        DirectedWeightedGraph new_graph = new Directed_Weighted_Graph();

        return false;
    }
}
