import java.io.FileWriter;
import java.util.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph("Graphs\\trialGraph.txt");
        System.out.println(exactIS(graph).toString() + " " + exactIS(graph).size());
        System.out.println(exactVC(graph).toString() + " " + exactVC(graph).size());
        System.out.println(inexactVC(graph));
//        generateRandomGraph(10);
    }

    public static List<List<Integer>> powerset(List<Integer> set) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());

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


    public static List<Integer> exactVC(Graph graph) {
//        int bestVC = Integer.MAX_VALUE;
//        List<Integer> nodeList = new ArrayList<>();
//        int[][] actualGraph = graph.getGraph();
//        int counter = 0;
//        for (int[] node : actualGraph) {
//            nodeList.add(counter);
//            counter++;
//        }
//
//        List<List<Integer>> powerSet = powerset(nodeList);
//
//
//        for (List<Integer> set : powerSet) {
//            if (verifierVC(graph, set)) {
//                if (set.size() < bestVC) {
//                    bestVC = set.size();
//                    System.out.println(set.toString());
//                }
//            }
//        }
//
//        return bestVC;

        List<Integer> bestIS = exactIS(graph);
        int[][] aGraph = graph.getGraph();
        List<Integer> bestVC = new ArrayList<>();
        for (int i = 0; i < aGraph.length; i++) {
            if (!bestIS.contains(i)) {
                bestVC.add(i);
            }
        }
        return bestVC;
    }

    public static int inexactVC(Graph g) {
        int[][] graph = g.getGraph();
        Dictionary<Integer, int[]> dict = new Hashtable<>();
        List<Integer> tabooList = new ArrayList<Integer>();
        for (int i = 0; i < graph.length; i++) {
            dict.put(i, graph[i]);
        }
        int max = 0;
        int startingNode = 0;
        List<Integer> vc = new ArrayList<Integer>();
        while (dict.size() > 0) {
            max = 0;
            for (int i = 0; i < graph.length; i++) {
                if (graph[i].length > max && !tabooList.contains(i)) {
                    startingNode = i;
                    max = graph[i].length;
                }
            }
            vc.add(startingNode);
            tabooList.add(startingNode);
            dict.remove(startingNode);
            for (int i = 0; i < graph[startingNode].length; i++) {
                if (!vc.contains(graph[startingNode][i])) {
                    tabooList.add(graph[startingNode][i]);
                    dict.remove(graph[startingNode][i]);
                }
            }
        }


        if (verifierVC(g, vc)) {
            return vc.size();
        } else {
            System.out.println("ERROR ERROR");
            return 0;
        }


    }

    public static boolean verifierIS(Graph graph, List<Integer> set) {
        int[][] actualGraph = graph.getGraph();

        for (int vertex : set) {
            int numNeighbors = actualGraph[vertex].length;

            for (int j = 0; j < numNeighbors; j++) {
                int neighbor = actualGraph[vertex][j];

                if (set.contains(neighbor)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean verifierVC(Graph g, List<Integer> set) {
        int[][] graph = g.getGraph();
        List<Integer> coveredNodes = new ArrayList<Integer>();
        for (int vertex : set) {
            if (!coveredNodes.contains(vertex)) {
                coveredNodes.add(vertex);
            }
            for (int linkedNode : graph[vertex]) {
                if (!coveredNodes.contains(linkedNode)) {
                    coveredNodes.add(linkedNode);
                }
            }
        }
        if (coveredNodes.size() == graph.length) {
            return true;
        } else {
            return false;
        }
    }


    public static List<Integer> exactIS(Graph graph) {
        int bestIS = 0;
        List<Integer> bestSet = new ArrayList<>();
        List<Integer> nodeList = new ArrayList<>();
        int[][] actualGraph = graph.getGraph();
        int counter = 0;
        for (int[] node : actualGraph) {
            nodeList.add(counter);
            counter++;
        }

        List<List<Integer>> powerSet = powerset(nodeList);


        for (List<Integer> set : powerSet) {
            if (verifierIS(graph, set)) {
                if (set.size() > bestIS) {
                    bestIS = set.size();
                    bestSet = set;
//                    System.out.println(set.toString());
                }
            }
        }

        return bestSet;
    }

    public static int inexactIS(Graph graph) {
        int[][] joshylisty = graph.getGraph();
        int numofjoshy = joshylisty.length;
        boolean[] coveredquestionmark = new boolean[numofjoshy];
        List<Integer> IS = new ArrayList<>();
        for (int i = 0; i < numofjoshy; i++) {
            if (!coveredquestionmark[i]) {
                IS.add(i);
                for (int friend : joshylisty[i]) {
                    coveredquestionmark[friend] = true;
                }
            }
        }
        if (verifierIS(graph, IS)) {
            return IS.size();
        } else {
            System.out.println("ERROR ERROR");
            return 0;
        }
    }

    public static void generateRandomGraph(int numNodes) {
        Random random = new Random();
        String fileName = "Graphs\\trialGraph.txt";

        Map<Integer, List<Integer>> allEdges = new HashMap<>();

        for (int i = 0; i < numNodes; i++) {
            allEdges.put(i, new ArrayList<>());
        }

        for (int i = 0; i < numNodes; i++) {

            for (int j = 0; j < numNodes; j++) {
                if (i != j && random.nextDouble() < 0.4) {
                    List<Integer> nodeIList = allEdges.get(i);

                    if(!nodeIList.contains(j)) {
                        nodeIList.add(j);
                    }

                    List<Integer> nodeJList = allEdges.get(j);

                    if(!nodeJList.contains(i)) {
                        nodeJList.add(i);
                    }

                    allEdges.put(i, nodeIList);
                    allEdges.put(j, nodeJList);
                }
            }
        }

        try (FileWriter aFile = new FileWriter(fileName)) {
            aFile.write("numVert " + numNodes + "\n");
            aFile.write("vertex numNeighbors neighbors\n");

                for (int x = 0; x < numNodes; x++) {
                    StringBuilder line = new StringBuilder(x + " ");
                    int numNeighbors = allEdges.get(x).size();

                    line.append(numNeighbors).append(" ");
                    for (int neighbor : allEdges.get(x)) {
                        line.append(neighbor).append(" ");
                    }

                    line.append("\n");
                    aFile.write(line.toString());
                }

        } catch (IOException e) {
            System.err.println("Error writing the graph to file: " + e.getMessage());
        }
    }
}
