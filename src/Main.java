import java.util.*;


public class Main {
    public static void main(String[] args) {
        int averageIS = 0;
        int averageVC = 0;
        for (int i = 10; i < 2000; i+=10) {
//            List<Long> times = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                GraphToolBox.generateRandomGraph(i);
                Graph graph = new Graph("Graphs\\trialGraph.txt");
                long startTime = System.nanoTime();
                averageIS += (GraphToolBox.inexactVC(graph)).length;
                long endTime = System.nanoTime();
                System.out.println("average IS size for graph of size: " + graph.getGraph().length + " is " + (endTime - startTime) / 1000000.0);
            }
            averageIS /= 50;
//            System.out.println("average IS size for graph of size: " +i + " is " + averageIS);
        }
        Graph graph = new Graph("Graphs\\trialGraph.txt");
    }
}
