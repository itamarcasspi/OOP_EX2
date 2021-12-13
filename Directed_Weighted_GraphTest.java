import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Directed_Weighted_GraphTest {
    DirectedWeightedGraph my_graph = new Directed_Weighted_Graph();
    GeoLocation location = new Geo_Location(4,4,4);
    NodeData a0 = new Node_Data(0,"0,1,2");
    NodeData b1 = new Node_Data(1,"0,1,2");
    NodeData c2 = new Node_Data(2,"0,1,2");
    NodeData d3 = new Node_Data(3,"0,1,2");
    NodeData e4 = new Node_Data(4,"0,1,2");
    NodeData f5 = new Node_Data(5,"0,1,2");

    @Test
    void getNode() {
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);

        assertEquals(my_graph.getNode(0),a0);
        assertEquals(my_graph.getNode(1),b1);
        assertEquals(my_graph.getNode(2),c2);
        assertEquals(my_graph.getNode(3),d3);
        assertEquals(my_graph.getNode(4),e4);
        assertEquals(my_graph.getNode(5),f5);
    }

    @Test
    void getEdge() {
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
        EdgeData a0_b1 = my_graph.getEdge(0, 1);
        EdgeData a0_c2 = my_graph.getEdge(0, 2);
        EdgeData b1_c2 = my_graph.getEdge(1, 2);
        EdgeData c2_e4 = my_graph.getEdge(2, 4);
        EdgeData b1_d3 = my_graph.getEdge(1, 3);
        EdgeData e4_d3 = my_graph.getEdge(4, 3);
        EdgeData d3_f5 = my_graph.getEdge(3, 5);

        assertTrue(a0_b1.getSrc() == 0);
        assertTrue(a0_b1.getDest() == 1);
        assertTrue(a0_b1.getWeight() == 4);

        assertTrue(a0_c2.getSrc() == 0);
        assertTrue(a0_c2.getDest() == 2);
        assertTrue(a0_c2.getWeight() == 2);

        assertTrue(b1_c2.getSrc() == 1);
        assertTrue(b1_c2.getDest() == 2);
        assertTrue(b1_c2.getWeight() == 5);

        assertTrue(c2_e4.getSrc() == 2);
        assertTrue(c2_e4.getDest() == 4);
        assertTrue(c2_e4.getWeight() == 3);

        assertTrue(b1_d3.getSrc() == 1);
        assertTrue(b1_d3.getDest() == 3);
        assertTrue(b1_d3.getWeight() == 10);

        assertTrue(e4_d3.getSrc() == 4);
        assertTrue(e4_d3.getDest() == 3);
        assertTrue(e4_d3.getWeight() == 4);

        assertTrue(d3_f5.getSrc() == 3);
        assertTrue(d3_f5.getDest() == 5);
        assertTrue(d3_f5.getWeight() == 11);
    }


    @Test
    void nodeIter() {
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.addNode(d3);
        my_graph.addNode(e4);
        my_graph.addNode(f5);
        HashMap<Integer, NodeData> new_node_map = new HashMap<>();
        Iterator<NodeData> node_it = my_graph.nodeIter();

        while(node_it.hasNext())
        {
            NodeData current = node_it.next();
            new_node_map.put(current.getKey(), current);
        }

        assertTrue(new_node_map.containsKey(0));
        assertTrue(new_node_map.containsKey(1));
        assertTrue(new_node_map.containsKey(2));
        assertTrue(new_node_map.containsKey(3));
        assertTrue(new_node_map.containsKey(4));
        assertTrue(new_node_map.containsKey(5));



    }
    //test of the "all edges" iterator
    @Test
    void edgeIter() {
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
        EdgeData a0_b1 = my_graph.getEdge(0, 1);
        EdgeData a0_c2 = my_graph.getEdge(0, 2);
        EdgeData b1_c2 = my_graph.getEdge(1, 2);
        EdgeData c2_e4 = my_graph.getEdge(2, 4);
        EdgeData b1_d3 = my_graph.getEdge(1, 3);
        EdgeData e4_d3 = my_graph.getEdge(4, 3);
        EdgeData d3_f5 = my_graph.getEdge(3, 5);

        HashMap<Integer,HashMap<Integer, EdgeData>> new_edge_map = new HashMap<>();
        Iterator<EdgeData> all_edge_it = my_graph.edgeIter();

        while(all_edge_it.hasNext())
        {
            EdgeData current = all_edge_it.next();
            if(!new_edge_map.containsKey(current.getSrc()))
            {
                HashMap<Integer, EdgeData> temp = new HashMap<>();
                new_edge_map.put(current.getSrc(),temp);
            }
            new_edge_map.get(current.getSrc()).put(current.getDest(),current);
        }

        assertTrue(new_edge_map.get(0).get(1).getSrc() == 0);
        assertTrue(new_edge_map.get(0).get(1).getDest() == 1);
        assertTrue(new_edge_map.get(0).get(1).getWeight() == 4);

        assertTrue(new_edge_map.get(0).get(2).getSrc() == 0);
        assertTrue(new_edge_map.get(0).get(2).getDest() == 2);
        assertTrue(new_edge_map.get(0).get(2).getWeight() == 2);

        assertTrue(new_edge_map.get(1).get(2).getSrc() == 1);
        assertTrue(new_edge_map.get(1).get(2).getDest() == 2);
        assertTrue(new_edge_map.get(1).get(2).getWeight() == 5);

        assertTrue(new_edge_map.get(2).get(4).getSrc() == 2);
        assertTrue(new_edge_map.get(2).get(4).getDest() == 4);
        assertTrue(new_edge_map.get(2).get(4).getWeight() == 3);

        assertTrue(new_edge_map.get(1).get(3).getSrc() == 1);
        assertTrue(new_edge_map.get(1).get(3).getDest() == 3);
        assertTrue(new_edge_map.get(1).get(3).getWeight() == 10);

        assertTrue(new_edge_map.get(4).get(3).getSrc() == 4);
        assertTrue(new_edge_map.get(4).get(3).getDest() == 3);
        assertTrue(new_edge_map.get(4).get(3).getWeight() == 4);

        assertTrue(new_edge_map.get(3).get(5).getSrc() == 3);
        assertTrue(new_edge_map.get(3).get(5).getDest() == 5);
        assertTrue(new_edge_map.get(3).get(5).getWeight() == 11);
    }

    @Test
    void testEdgeIter() {
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
        EdgeData a0_b1 = my_graph.getEdge(0, 1);
        EdgeData a0_c2 = my_graph.getEdge(0, 2);
        EdgeData b1_c2 = my_graph.getEdge(1, 2);
        EdgeData c2_e4 = my_graph.getEdge(2, 4);
        EdgeData b1_d3 = my_graph.getEdge(1, 3);
        EdgeData e4_d3 = my_graph.getEdge(4, 3);
        EdgeData d3_f5 = my_graph.getEdge(3, 5);

        HashMap<Integer, EdgeData> new_dest_src = new HashMap<>();
        Iterator<EdgeData> edge_it = my_graph.edgeIter(0);
        while(edge_it.hasNext())
        {
            EdgeData current = edge_it.next();
            new_dest_src.put(current.getDest(),current);
        }

        assertTrue(new_dest_src.get(1).getSrc() == 0);
        assertTrue(new_dest_src.get(1).getDest() == 1);
        assertTrue(new_dest_src.get(1).getWeight() == 4);

        assertTrue(new_dest_src.get(2).getSrc() == 0);
        assertTrue(new_dest_src.get(2).getDest() == 2);
        assertTrue(new_dest_src.get(2).getWeight() == 2);

        new_dest_src = new HashMap<>();
        edge_it = my_graph.edgeIter(1);
        while(edge_it.hasNext())
        {
            EdgeData current = edge_it.next();
            new_dest_src.put(current.getDest(),current);
        }

        assertTrue(new_dest_src.get(2).getSrc() == 1);
        assertTrue(new_dest_src.get(2).getDest() == 2);
        assertTrue(new_dest_src.get(2).getWeight() == 5);

        assertTrue(new_dest_src.get(3).getSrc() == 1);
        assertTrue(new_dest_src.get(3).getDest() == 3);
        assertTrue(new_dest_src.get(3).getWeight() == 10);
        new_dest_src = new HashMap<>();
        edge_it = my_graph.edgeIter(2);
        while(edge_it.hasNext())
        {
            EdgeData current = edge_it.next();
            new_dest_src.put(current.getDest(),current);
        }

        assertTrue(new_dest_src.get(4).getSrc() == 2);
        assertTrue(new_dest_src.get(4).getDest() == 4);
        assertTrue(new_dest_src.get(4).getWeight() == 3);

        new_dest_src = new HashMap<>();
        edge_it = my_graph.edgeIter(3);
        while(edge_it.hasNext())
        {
            EdgeData current = edge_it.next();
            new_dest_src.put(current.getDest(),current);
        }

        assertTrue(new_dest_src.get(5).getSrc() == 3);
        assertTrue(new_dest_src.get(5).getDest() == 5);
        assertTrue(new_dest_src.get(5).getWeight() == 11);

        new_dest_src = new HashMap<>();
        edge_it = my_graph.edgeIter(4);
        while(edge_it.hasNext())
        {
            EdgeData current = edge_it.next();
            new_dest_src.put(current.getDest(),current);
        }

        assertTrue(new_dest_src.get(3).getSrc() == 4);
        assertTrue(new_dest_src.get(3).getDest() == 3);
        assertTrue(new_dest_src.get(3).getWeight() == 4);
    }

    @Test
    void removeNode() {
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
        EdgeData a0_b1 = my_graph.getEdge(0, 1);
        EdgeData a0_c2 = my_graph.getEdge(0, 2);
        EdgeData b1_c2 = my_graph.getEdge(1, 2);
        EdgeData c2_e4 = my_graph.getEdge(2, 4);
        EdgeData b1_d3 = my_graph.getEdge(1, 3);
        EdgeData e4_d3 = my_graph.getEdge(4, 3);
        EdgeData d3_f5 = my_graph.getEdge(3, 5);


        assertEquals(my_graph.removeNode(0),a0);
        assertEquals(my_graph.getEdge(0,1),null);
        assertEquals(my_graph.getEdge(0,2),null);


    }

    @Test
    void removeEdge() {
        my_graph = new Directed_Weighted_Graph("data/G1.json");
        assertNotEquals(my_graph.getEdge(0,1),null);
        my_graph.removeEdge(0,1);
        assertEquals(my_graph.getEdge(0,1),null);

    }

    @Test
    void nodeSize() {

    }

    @Test
    void edgeSize() {
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
        EdgeData a0_b1 = my_graph.getEdge(0, 1);
        EdgeData a0_c2 = my_graph.getEdge(0, 2);
        EdgeData b1_c2 = my_graph.getEdge(1, 2);
        EdgeData c2_e4 = my_graph.getEdge(2, 4);
        EdgeData b1_d3 = my_graph.getEdge(1, 3);
        EdgeData e4_d3 = my_graph.getEdge(4, 3);
        EdgeData d3_f5 = my_graph.getEdge(3, 5);

        my_graph.removeNode(0);
        assertEquals(my_graph.edgeSize(),5);
        assertEquals(my_graph.getEdge(0,1),null);
        assertEquals(my_graph.getEdge(0,2),null);

        my_graph.removeNode(3);
        assertEquals(my_graph.edgeSize(),2);
        assertEquals(my_graph.getEdge(1,3),null);
        assertEquals(my_graph.getEdge(4,3),null);
        assertEquals(my_graph.getEdge(3,5),null);


    }

    @Test
    void getMC() {
        assertEquals(my_graph.getMC(),0);
        my_graph.addNode(a0);
        my_graph.addNode(b1);
        my_graph.addNode(c2);
        my_graph.connect(a0.getKey(), b1.getKey(), 4);
        my_graph.connect(a0.getKey(), c2.getKey(), 2);
        my_graph.connect(b1.getKey(), c2.getKey(), 5);
        assertEquals(my_graph.getMC(),6);
        my_graph.removeNode(0);
        assertEquals(my_graph.getMC(),9);
        my_graph.removeEdge(1,2);
        assertEquals(my_graph.getMC(),10);
    }

    @Test
    void getNodesMap() {
    }

    @Test
    void getEdgeMap() {
    }
}