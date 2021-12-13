import  api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = new Directed_Weighted_Graph(json_file);
        return ans;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new Directed_Weighted_Graph_Algorithms(json_file);
        return ans;
    }


    /**
     * This static function will run your GUI using the json file.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file); //if we use the extends way it will work. (this is boaz's line)

        JFrame screen = new JFrame("OOP-EX2");
        screen.setSize(600,500);

        GUI graph = new GUI(alg);
        screen.add(graph);

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);

    }

    public static void runGUI(DirectedWeightedGraphAlgorithms alg) {

        JFrame screen = new JFrame("OOP-EX2");
        screen.setSize(600,500);

        GUI graph = new GUI(alg);
        screen.add(graph);

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
    }

    public static void main(String[] args) {
        // DirectedWeightedGraphAlgorithms t = new Directed_Weighted_Graph_Algorithms("data/G1.json");
        String basePath = "data/";
        DirectedWeightedGraphAlgorithms t = new Directed_Weighted_Graph_Algorithms(basePath + args[0]);
        runGUI(t);
    }
}

