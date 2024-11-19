public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph("Graphs\\graph1.txt");
        System.out.println("Test");
        System.out.println("nathan IS STUPID");
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




    }

    public static void inexactVC(Graph graph) {
        int bestVC = Integer.MAX_VALUE;
    }

    public static void exactIS(Graph graph) {
        int bestIS = Integer.MAX_VALUE;
    }

    public static void inexactIS(Graph graph) {
        int bestIS = Integer.MAX_VALUE;
    }
}
