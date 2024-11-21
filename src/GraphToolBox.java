import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author yaw
 */
public class GraphToolBox {
    // return an array containing the vertex numbers of an optimal VC.
    public static List<Integer> exactVC(Graph graph) {
        int[] vertexList = optimalIS(graph);
        List<Integer> bestIS = new ArrayList<>();
        for (int i : vertexList) {
            bestIS.add(i);
        }
        int[][] aGraph = graph.getGraph();
        List<Integer> bestVC = new ArrayList<>();
        for (int i = 0; i < aGraph.length; i++) {
            if (!bestIS.contains(i)) {
                bestVC.add(i);
            }
        }
        return bestVC;
    }


    // return (in polynomial time) an array containing the vertex numbers of a VC.
    public static int[] inexactVC(Graph g) {
        int[] approximateIS = inexactIS(g);
        List<Integer> approximateISList = new ArrayList<>();
        List<Integer> approximateVCList = new ArrayList<>();
        int[][] graph = g.getGraph();

        for (int vertex : approximateIS) {
            approximateISList.add(vertex);
        }

        for (int i = 0; i < graph.length; i ++) {
            if (!approximateISList.contains(i)) {
                approximateVCList.add(i);
            }
        }

        int[] vertexList = new int[approximateVCList.size()];

        for (int i = 0; i < approximateVCList.size(); i++) {
            vertexList[i] = approximateVCList.get(i);
        }

        return vertexList;
    }
    
    // return an array containing the vertex numbers of an optimal IS.
    public static int[] optimalIS(Graph inputGraph) {
        int bestIS = 0;
        List<Integer> bestSet = new ArrayList<>();
        List<Integer> nodeList = new ArrayList<>();
        int[][] actualGraph = inputGraph.getGraph();
        int counter = 0;
        for (int[] node : actualGraph) {
            nodeList.add(counter);
            counter++;
        }

        List<List<Integer>> powerSet = powerset(nodeList);


        for (List<Integer> set : powerSet) {
            if (verifierIS(inputGraph, set)) {
                if (set.size() > bestIS) {
                    bestIS = set.size();
                    bestSet = set;
//                    System.out.println(set.toString());
                }
            }
        }

        int[] vertexLabels = new int[bestSet.size()];
        int i = 0;
        for (Integer value : bestSet) {
            vertexLabels[i++] = value;
        }

        return vertexLabels;
    }
    
    // return (in polynomial time) an array containing the vertex numbers of an IS.
    public static int[] inexactIS(Graph inputGraph) {

        int[][] graph = inputGraph.getGraph();
        List<Integer> baseIS = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            boolean isAdjacent = false;
            for (int j = 0; j < graph[i].length; j++) {
                if (baseIS.contains(graph[i][j])) {
                    isAdjacent = true;
                }
            }
            if (!isAdjacent) {
                baseIS.add(i);
            }
        }

        int optimalSize = baseIS.size();
        List<Integer> bestSet = baseIS;
        boolean improved = true;

        while (improved) {
            int pastSize = optimalSize;
            for (int i = 0; i < graph.length; i++) {
                if (!bestSet.contains(i)) {
//                    List<Integer> newSet = bestSet;
                    List<Integer> newSet = new ArrayList<>(bestSet);
                    newSet.add(i);

                    if (verifierIS(inputGraph, newSet)) {
                        if (newSet.size() > optimalSize) {
                            bestSet = newSet;
                            optimalSize = newSet.size();
                        }
                    }
                }
            }

            if (pastSize == optimalSize) {
                improved = false;
            }
        }

        int[] vertexList = new int[bestSet.size()];
        for (int i = 0; i < bestSet.size(); i++) {
            vertexList[i] = bestSet.get(i);
        }

        return vertexList;
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
