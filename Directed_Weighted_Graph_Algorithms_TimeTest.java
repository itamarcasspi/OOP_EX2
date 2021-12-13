import api.DirectedWeightedGraphAlgorithms;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Directed_Weighted_Graph_Algorithms_TimeTest {
    DirectedWeightedGraphAlgorithms algo_100k = new Directed_Weighted_Graph_Algorithms("data/100000.json");
    DirectedWeightedGraphAlgorithms algo_10k = new Directed_Weighted_Graph_Algorithms("data/10000Nodes.json");
    DirectedWeightedGraphAlgorithms algo_1k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");


    @Test
    void isConnected() {
//      assertTrue(algo_10k.isConnected());


    }

    @Test
    void shortestPathDist() {
        assertTrue(algo_10k.shortestPathDist(0,9000)>0);
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void save() {
    }
    @Test
    void load() {
//        DirectedWeightedGraphAlgorithms algo_1k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");
//        DirectedWeightedGraphAlgorithms algo_10k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");
//        DirectedWeightedGraphAlgorithms algo_100k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");
    }
}