import java.io.FileWriter;
import java.util.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("Graphs\\graph1.txt");
//        System.out.println(Arrays.toString(GraphToolBox.optimalIS(graph)) + " " + GraphToolBox.optimalIS(graph).length);
//        System.out.println(GraphToolBox.exactVC(graph).toString() + " " + GraphToolBox.exactVC(graph).size());
        System.out.println(GraphToolBox.inexactVC(graph));
        System.out.println(Arrays.toString(GraphToolBox.inexactIS(graph)) + " " + GraphToolBox.inexactIS(graph).length);
        GraphToolBox.generateRandomGraph(100);
    }
}
