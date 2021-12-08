package api;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class test {

    public static void main(String[] args) {
        Geo_Location location  = new Geo_Location(0,0,0);
        NodeData a0 = new Node_Data(location,5,"z");
        NodeData b1 = new Node_Data(location,4,"");
        NodeData c2 = new Node_Data(location,5,"z");
        NodeData d3 = new Node_Data(location,5,"z");
        NodeData e4 = new Node_Data(location,5,"z");
        NodeData f5 = new Node_Data(location,5,"z");
//        EdgeData e1_e2 = new Edge_Data(e1.getKey(),e2.getKey(),3);
//        EdgeData e1_e3 = new Edge_Data(e1.getKey(),e3.getKey(),4);
        DirectedWeightedGraph my_graph = new Directed_Weighted_Graph();
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);

        my_graph.connect(a0.getKey(),b1.getKey(),4);
        my_graph.connect(a0.getKey(),c2.getKey(),2);
        my_graph.connect(b1.getKey(),c2.getKey(),5);
        my_graph.connect(c2.getKey(),e4.getKey(),3);
        my_graph.connect(b1.getKey(),d3.getKey(),10);
        my_graph.connect(e4.getKey(),d3.getKey(),4);
        my_graph.connect(d3.getKey(),f5.getKey(),11);


        System.out.println("my_graph edges = "+my_graph.edgeSize());
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms(my_graph);
        Iterator<NodeData> my_it = algo.getGraph().nodeIter();


        while(my_it.hasNext())
        {
            System.out.println("key = "+my_it.next().getKey());

        }
        Iterator<EdgeData> all_edge = algo.getGraph().edgeIter();
        while(all_edge.hasNext())
        {
            EdgeData current = all_edge.next();
            System.out.println("src = "+current.getSrc()+" dest = "+current.getDest());

        }
        Iterator<EdgeData> e0_to = algo.getGraph().edgeIter(0);
        while(e0_to.hasNext())
        {
            EdgeData current = e0_to.next();
            System.out.println("src = "+current.getSrc()+" dest = "+current.getDest());

        }
        System.out.println("shortest path is : "+algo.shortestPathDist(a0.getKey(),f5.getKey()));

    }
}
