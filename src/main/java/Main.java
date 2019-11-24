import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        Graph<Integer> graph = Graph.emptyGraph();
//        graph.addEdge(0, 1);
//        graph.addEdge(0, 6);
        //graph.addEdge(1, 2);
//        graph.addEdge(2, 3);
//        graph.addEdge(6, 3);
//        graph.addEdge(6, 7);
//        graph.addEdge(2, 7);
//
//        graph.addEdge(9, 10);
//        graph.addEdge(10, 11);
//        graph.addEdge(11, 12);
//        graph.addEdge(11, 13);
//        graph.addEdge(13, 10);
        Set values = new HashSet<Integer>();
        Supplier<Integer> randomGenerator = () -> {
            Random r = new Random();
            return r.nextInt(10);
        };

        IntStream.range(0, 10).forEach(v -> {
            int rand1 = randomGenerator.get();
            int rand2 = randomGenerator.get();
            values.add(rand1);
            values.add(rand2);
            if (rand1 != rand2 && graph.getEdges(rand1).isPresent() && graph.getEdges(rand1).get().size() < 4)
                System.out.println("add " + rand1 + " and " + rand2);
                graph.addEdge(rand1, rand2);
        });

        graph.breadthFirstSearch((Integer) values.stream().findAny().get(), v -> System.out.println("processing : " + v));
        graph.depthFirstSearch((Integer) values.stream().findAny().get(), System.out::println);
        System.out.println(graph.hasPathTo((Integer) values.stream().findAny().get(), (Integer) values.stream().findAny().get()));
    }
}
