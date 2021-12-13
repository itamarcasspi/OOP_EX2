import api.DirectedWeightedGraphAlgorithms;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Directed_Weighted_Graph_Algorithms_TimeTest {

    @Test
    void isConnected() {
//        DirectedWeightedGraphAlgorithms algo_10k = new Directed_Weighted_Graph_Algorithms("data/10000Nodes.json");
//        assertTrue(algo_10k.isConnected());


    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraphAlgorithms algo_10k = new Directed_Weighted_Graph_Algorithms("data/10000Nodes.json");
        algo_10k.shortestPathDist(0,9999);

    }

    @Test
    void shortestPath() {

    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithms algo_1k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");
        assertTrue(algo_1k.center().getKey()>-1);
    }

    @Test
    void save() {
    }
    @Test
    void load() {
//        DirectedWeightedGraphAlgorithms algo_1k = new Directed_Weighted_Graph_Algorithms("data/1000Nodes.json");
//        DirectedWeightedGraphAlgorithms algo_10k = new Directed_Weighted_Graph_Algorithms("data/10000Nodes.json");
//        DirectedWeightedGraphAlgorithms algo_100k = new Directed_Weighted_Graph_Algorithms("data/100000.json");
    }
}