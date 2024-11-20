import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("Graphs\\trialGraph.txt");
        System.out.println(exactIS(graph));
        inexactVC(graph);
        generateRandomGraph(10);
    }

    public static List<List<Integer>> powerset(List<Integer> set) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // Add the empty set

        for (int num : set) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> subset = new ArrayList<>(result.get(i));
                subset.add(num);
                result.add(subset);
            }
        }

        return result;
    }


    public static void exactVC(Graph graph) {
        int bestVC = Integer.MAX_VALUE;
        List<Integer> nodeList = new ArrayList<>();
        int[][] actualGraph = graph.getGraph();
        for (int[] node : actualGraph) {
            nodeList.add(node[0]);
        }

        List<List<Integer>> powerSet = powerset(nodeList);

        System.out.println("Test");
    }

    public static int inexactVC(Graph graph) {
        int[][] myListy = graph.getGraph();
        int numNodey = myListy.length;
        boolean[] pickedhm = new boolean[numNodey];
        List<Integer> VC = new ArrayList<>();
        for(int i= 0; i<numNodey; i++) {
            if (pickedhm[i]) {
                continue;
            }
            for (int friend : myListy[i]) {
                if (!pickedhm[friend]) {
                    VC.add(i);
                    VC.add(friend);
                    pickedhm[i] = true;
                    pickedhm[friend] = true;
                    break;

                }
            }
        }
        if(verifierVC(graph, VC)){
            return VC.size();
        }
        else {
            System.out.println("ERROR ERROR");
            return 0;
        }
    }

    public static boolean verifierIS(Graph graph, List<Integer> set) {
        int[][] actualGraph = graph.getGraph();

        for (int vertex : set) {
            int numNeighbors = actualGraph[vertex].length;

            for (int j = 0; j < numNeighbors; j++) {
                int neighbor = actualGraph[vertex][j] - 1;

                if (set.contains(neighbor)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean verifierVC(Graph g, List<Integer> set){
        int [][] graph = g.getGraph();
        List<Integer> coveredNodes = new ArrayList<Integer>();
        for(int vertex : set){
            if(!coveredNodes.contains(vertex)){
                coveredNodes.add(vertex);
            }
            for(int linkedNode : graph[vertex]){
                if(!coveredNodes.contains(linkedNode)){
                    coveredNodes.add(linkedNode);
                }
            }
        }
        if(coveredNodes.size() == graph.length){
            return true;
        }
        else{
            return false;
        }
    }
    public static int exactIS(Graph graph) {
        int bestIS = 0;
        List<Integer> nodeList = new ArrayList<>();
        int[][] actualGraph = graph.getGraph();
        int counter = 0;
        for (int[] node : actualGraph) {

            if (node.length == 0) {
                return 0;
            }

            nodeList.add(counter);
            counter++;
        }

        List<List<Integer>> powerSet = powerset(nodeList);


        for (List<Integer> set : powerSet) {
            if (verifierIS(graph, set)) {
                if (set.size() > bestIS) {
                    bestIS = set.size();
                    System.out.println(set.toString());
                }
            }
        }

        return bestIS;
    }

    public static void inexactIS(Graph graph) {
        int bestIS = Integer.MAX_VALUE;
    }

    public static void generateRandomGraph(int numNodes) {
        Random random = new Random();
        String fileName = "Graphs\\trialGraph.txt";

        try (FileWriter aFile = new FileWriter(fileName)) {
            aFile.write("numVert " + numNodes + "\n");
            aFile.write("vertex numNeighbors neighbors\n");

            for (int i = 0; i < numNodes; i++) {
                StringBuilder line = new StringBuilder(i + " ");
                List<Integer> neighbors = new ArrayList<>();

                for (int j = 0; j < numNodes; j++) {
                    if (i != j && random.nextDouble() < 0.4) {
                        neighbors.add(j);
                    }
                }

                line.append(neighbors.size()).append(" ");
                for (int neighbor : neighbors) {
                    line.append(neighbor).append(" ");
                }

                aFile.write(line.toString().trim() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Error writing the graph to file: " + e.getMessage());
        }
    }
}
