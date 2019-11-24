import java.util.*;
import java.util.function.Consumer;

public class Graph<T> {

    private Map<Node<T>, Set<Node>> nodes = new HashMap<>();

    private Graph() {
    }

    public static Graph emptyGraph() {
        return new Graph<>();
    }

    void addEdge(T v, T w) {
        Node sourceNode = new Node<>(v);
        Node destinationNode = new Node<>(w);
        addEdge(sourceNode, destinationNode);
        addEdge(destinationNode, sourceNode);

    }

    private void addEdge(Node firstNode, Node secondNode) {
        nodes.merge(firstNode, new HashSet<>(Collections.singleton(secondNode)), (currentNode, newNode) -> {
            currentNode.addAll(newNode);
            return currentNode;
        });
    }

    Map depthFirstSearch(T source, Consumer consumer) {
        Set visited = new HashSet<T>();
        Map path = new HashMap();
        depthFirstSearch(consumer, visited, path, new Node<>(source));
        return path;
    }

    private void depthFirstSearch(Consumer<T> consumer, Set<T> visited, Map<T, T> path, Node<T> node) {
        if (!visited.contains(node.value)) {
            visited.add(node.value);
            consumer.accept(node.value);
            nodes.get(node)
                    .forEach(edgeNode -> {
                                if (path.values().stream().noneMatch(v -> v == edgeNode.value)) {
                                    path.put((T) edgeNode.value, node.value);
                                }
                                Node nextNode = this.nodes.keySet().stream()
                                        .filter(nodeFilter -> nodeFilter.value == edgeNode.value)
                                        .findFirst()
                                        .get();
                                if (node.value != nextNode.value) {
                                    depthFirstSearch(consumer, visited, path, nextNode);
                                }
                            }
                    );
        }
    }


    Boolean hasPathTo(T source, T destination) {
        Map path = this.depthFirstSearch(source, noop -> {
        });
        path.forEach((x, y) -> System.out.println("key : " + x + " value : " + y));
        if (path.containsKey(destination)) {
            System.out.print(destination);
            T edge = (T) path.get(destination);
            while (edge != null) {
                System.out.print(" <--- " + edge);
                edge = (T) path.get(edge);
            }
            System.out.println();
        }
        return path.keySet().stream().filter(value -> value == destination).findFirst().isPresent();
    }

    Map breadthFirstSearch(T source, Consumer consumer) {
        Set visited = new HashSet<T>();
        Deque<Node<T>> nodes = new ArrayDeque<>();
        Node sourceNode = new Node<>(source);
        nodes.offer(sourceNode);
        Map path = new HashMap();
        breadthFirstSearch(consumer, nodes, visited, path);
        return path;
    }

    private void breadthFirstSearch(Consumer<T> consumer, Queue<Node<T>> nodesToTraverse, Set<T> visited, Map<T, T> path) {
        while (!nodesToTraverse.isEmpty()) {
            Node<T> node = nodesToTraverse.poll();
            System.out.print("poll " + node.value + " \nQueue: ");
            nodesToTraverse.stream().map(n -> n.value).forEach(System.out::print);
            System.out.println();
            visited.add(node.value);
            consumer.accept(node.value);
            nodes
                    .get(node)
                    .forEach(edgeNode -> {
                        if (!visited.contains(edgeNode.value)) {
                            if (path.keySet().stream().noneMatch(v -> v == edgeNode.value)) {
                                path.put((T) edgeNode.value, node.value);

                                nodesToTraverse.offer(nodes.keySet().stream()
                                        .filter(nodeFilter -> nodeFilter.value == edgeNode.value)
                                        .findFirst()
                                        .get());

                                System.out.print("offer " + edgeNode.value + " \nQueue: ");
                                nodesToTraverse.stream().map(n -> n.value).forEach(System.out::print);
                                System.out.println();
                            }
                        }
                    });
        }
    }

    Optional<Set<T>> getEdges(T value) {
        return Optional.of((Set) nodes.get(new Node<>(value)));
    }

    private class Node<T> {
        private T value;

        Node(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this.value == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}


