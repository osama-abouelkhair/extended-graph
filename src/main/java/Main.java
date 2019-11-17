import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        Graph<Integer> graph = new Graph(0);
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
        Supplier<Integer> randomGenerator = () -> {
            Random r = new Random();
            return r.nextInt(10);
        };

        IntStream.range(0, 10).forEach(v -> {
            int rand1 = randomGenerator.get();
            int rand2 = randomGenerator.get();
            if (rand1 != rand2 && graph.getEdges(rand1).size() < 4)
                System.out.println("add " + rand1 + " and " + rand2);
                graph.addEdge(rand1, rand2);
        });

        graph.breadthFirstSearch(v -> System.out.println("processing : " + v));
        graph.depthFirstSearch(System.out::println);
        System.out.println(graph.hasPathTo(90));
    }
}

//class Super { }
//
//class SubType extends Super { }
//
//class SubSubType extends SubType { }
//
//class Client<T> {
//
//    List<T> get(T val) {
//        List<T> l = new ArrayList<>();
//        l.add(val);
//        return l;
//    }
//
//}