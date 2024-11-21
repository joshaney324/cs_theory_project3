import java.util.*;


public class Main {
    public static void main(String[] args) {
        int averageIS = 0;
        int averageVC = 0;
        for (int i = 10; i < 2000; i+=10) {
            averageIS = 0;
            averageVC = 0;
            for (int j = 0; j < 50; j++) {
                GraphToolBox.generateRandomGraph(i);
                Graph graph = new Graph("Graphs\\trialGraph.txt");
                averageVC += (GraphToolBox.inexactVC(graph)).length;
            }
            averageVC /= 50;
            System.out.println("average VC size for graph of size: " +i + " is " + averageVC);
        }
        Graph graph = new Graph("Graphs\\trialGraph.txt");
    }
}
