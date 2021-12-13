import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.GeoLocation;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Directed_Weighted_Graph_AlgorithmsTest {


    //all three graph should be connected

    @Test
    void isConnected() {
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms("data/G1.json");
        assertTrue(algo.isConnected());
        algo.getGraph().removeNode(4);
        algo.getGraph().removeNode(6);
        assertFalse(algo.isConnected());

        algo = new Directed_Weighted_Graph_Algorithms("data/G2.json");
        assertTrue(algo.isConnected());
        algo = new Directed_Weighted_Graph_Algorithms("data/G3.json");
        assertTrue(algo.isConnected());


        DirectedWeightedGraph my_graph = new Directed_Weighted_Graph();
        GeoLocation location = new Geo_Location(4,4,4);
        NodeData a0 = new Node_Data(0,"0,1,2");
        NodeData b1 = new Node_Data(1,"0,1,2");
        NodeData c2 = new Node_Data(2,"0,1,2");
        NodeData d3 = new Node_Data(3,"0,1,2");
        NodeData e4 = new Node_Data(4,"0,1,2");
        NodeData f5 = new Node_Data(5,"0,1,2");
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);
        my_graph.connect(a0.getKey(), b1.getKey(), 4);
        my_graph.connect(a0.getKey(), c2.getKey(), 2);
        my_graph.connect(b1.getKey(), c2.getKey(), 5);
        my_graph.connect(c2.getKey(), e4.getKey(), 3);
        my_graph.connect(b1.getKey(), d3.getKey(), 10);
        my_graph.connect(e4.getKey(), d3.getKey(), 4);
        my_graph.connect(d3.getKey(), f5.getKey(), 11);
        algo = new Directed_Weighted_Graph_Algorithms(my_graph);
        assertTrue(algo.isConnected());
        algo.getGraph().removeNode(0);
        assertTrue(algo.isConnected());

        algo = new Directed_Weighted_Graph_Algorithms("data/10000Nodes.json");
        assertTrue(algo.isConnected());

    }
    //https://en.wikipedia.org/wiki/Shortest_path_problem#/media/File:Shortest_path_with_direct_weights.svg
    @Test
    void shortestPathDist() {
        DirectedWeightedGraph my_graph = new Directed_Weighted_Graph();
        GeoLocation location = new Geo_Location(4,4,4);
        NodeData a0 = new Node_Data(0,"0,1,2");
        NodeData b1 = new Node_Data(1,"0,1,2");
        NodeData c2 = new Node_Data(2,"0,1,2");
        NodeData d3 = new Node_Data(3,"0,1,2");
        NodeData e4 = new Node_Data(4,"0,1,2");
        NodeData f5 = new Node_Data(5,"0,1,2");
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);
        my_graph.connect(a0.getKey(), b1.getKey(), 4);
        my_graph.connect(a0.getKey(), c2.getKey(), 2);
        my_graph.connect(b1.getKey(), c2.getKey(), 5);
        my_graph.connect(c2.getKey(), e4.getKey(), 3);
        my_graph.connect(b1.getKey(), d3.getKey(), 10);
        my_graph.connect(e4.getKey(), d3.getKey(), 4);
        my_graph.connect(d3.getKey(), f5.getKey(), 11);
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms(my_graph);
        assertEquals(algo.shortestPathDist(0,5),20);
    }

    //https://en.wikipedia.org/wiki/Shortest_path_problem#/media/File:Shortest_path_with_direct_weights.svg
    @Test
    void shortestPath() {
        DirectedWeightedGraph my_graph = new Directed_Weighted_Graph();
        GeoLocation location = new Geo_Location(4,4,4);
        NodeData a0 = new Node_Data(0,"0,1,2");
        NodeData b1 = new Node_Data(1,"0,1,2");
        NodeData c2 = new Node_Data(2,"0,1,2");
        NodeData d3 = new Node_Data(3,"0,1,2");
        NodeData e4 = new Node_Data(4,"0,1,2");
        NodeData f5 = new Node_Data(5,"0,1,2");
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);
        my_graph.connect(a0.getKey(), b1.getKey(), 4);
        my_graph.connect(a0.getKey(), c2.getKey(), 2);
        my_graph.connect(b1.getKey(), c2.getKey(), 5);
        my_graph.connect(c2.getKey(), e4.getKey(), 3);
        my_graph.connect(b1.getKey(), d3.getKey(), 10);
        my_graph.connect(e4.getKey(), d3.getKey(), 4);
        my_graph.connect(d3.getKey(), f5.getKey(), 11);
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms(my_graph);
        List<NodeData> shortest_path = algo.shortestPath(0,5);
        assertEquals(shortest_path.get(0).getKey(),0);
        assertEquals(shortest_path.get(1).getKey(),2);
        assertEquals(shortest_path.get(2).getKey(),4);
        assertEquals(shortest_path.get(3).getKey(),3);
        assertEquals(shortest_path.get(4).getKey(),5);

    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms("data/G1.json");
        assertEquals(algo.center().getKey(),8);
        algo = new Directed_Weighted_Graph_Algorithms("data/G2.json");
        assertEquals(algo.center().getKey(),0);
        algo = new Directed_Weighted_Graph_Algorithms("data/G3.json");
        assertEquals(algo.center().getKey(),40);



//        algo = new Directed_Weighted_Graph_Algorithms("data/100000Nodes.json");
//        assertEquals(algo.center().getKey(),40);



    }

    @Test
    void tsp() {
        DirectedWeightedGraphAlgorithms algo = new Directed_Weighted_Graph_Algorithms("data/G1.json");
        List<NodeData> correct_path = new LinkedList<>();
        correct_path.add(algo.getGraph().getNode(12));
        correct_path.add(algo.getGraph().getNode(11));
        correct_path.add(algo.getGraph().getNode(10));
        correct_path.add(algo.getGraph().getNode(9));
        correct_path.add(algo.getGraph().getNode(8));
        correct_path.add(algo.getGraph().getNode(7));
        correct_path.add(algo.getGraph().getNode(6));
        correct_path.add(algo.getGraph().getNode(5));

        List<NodeData> list_for_tsp = new LinkedList<>();
        list_for_tsp.add(algo.getGraph().getNode(10));
        list_for_tsp.add(algo.getGraph().getNode(11));
        list_for_tsp.add(algo.getGraph().getNode(12));
        list_for_tsp.add(algo.getGraph().getNode(5));

        List<NodeData> tsp = algo.tsp(list_for_tsp);
        for (int i = 0; i<tsp.size() ; i++)
        {
            assertEquals(correct_path.get(i),tsp.get(i));
        }

        list_for_tsp.clear();
        list_for_tsp.add(algo.getGraph().getNode(10));
        list_for_tsp.add(algo.getGraph().getNode(11));
        list_for_tsp.add(algo.getGraph().getNode(7));
        list_for_tsp.add(algo.getGraph().getNode(5));
        list_for_tsp.add(algo.getGraph().getNode(12));
        tsp = algo.tsp(list_for_tsp);
        for (int i = 0; i<tsp.size() ; i++)
        {
            assertEquals(correct_path.get(i),tsp.get(i));
        }

    }
}