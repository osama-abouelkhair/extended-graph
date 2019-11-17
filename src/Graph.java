import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Graph<T> {

    private Node<T> source;
    private HashSet<Node<T>> vertices;

    Graph(T source) {
        this.source = new Node<T>(source);
        vertices = new HashSet<>();
        vertices.add(this.source);
    }

    void addEdge(T v, T w) {
        vertices
                .stream()
                .filter(node -> node.value == v)
                .findFirst()
                .map(node -> {
                    node.addEdge(w);
                    if (this.vertices.stream().noneMatch(edgeNode -> edgeNode.value == w)) {
                        Node<T> edgeNode = new Node(w);
                        edgeNode.addEdge(v);
                        this.vertices.add(edgeNode);
                    } else {
                        vertices
                                .stream()
                                .filter(wNode -> wNode.value == w)
                                .findFirst()
                                .map(wNode -> {
                                    wNode.addEdge(v);
                                    return vertices;
                                });
                    }
                    return vertices;
                })
                .orElseGet(() -> {
                    Node node = new Node(v);
                    node.addEdge(w);
                    vertices.add(node);
                    Node edgeNode = new Node(w);
                    vertices.add(edgeNode);
                    edgeNode.addEdge(v);
                    return vertices;
                });
    }

    Map depthFirstSearch(Consumer consumer) {
        Map visited = new HashMap<T, Boolean>();
        Map path = new HashMap();
        //this.vertices.forEach(node -> visited.put(node.value, false));
        depthFirstSearch(consumer, visited, path, this.source);
        return path;
    }

    private void depthFirstSearch(Consumer consumer, Map visited, Map path, Node<T> node) {
        if (visited.get(node.value) == null || visited.get(node.value).equals(false)) {
            visited.put(node.value, true);
            consumer.accept(node.value);
            node
                    .getEdges()
                    .forEach(edgeNode -> {
                        if (!path.values().stream().filter(v -> v == edgeNode.value).findFirst().isPresent()) {
                            path.put(edgeNode.value, node.value);
                        }
                        Node nextNode = this.vertices.stream()
                                .filter(nodeFilter -> nodeFilter.value == edgeNode.value)
                                .findFirst()
                                .get();
                        if (node.value != nextNode.value) {
                            depthFirstSearch(consumer, visited, path, nextNode);
                        }
                    });
        }
    }

    Boolean hasPathTo(T v) {
        Map<T, T> path = this.depthFirstSearch(noop -> {
        });
        path.forEach((x, y) -> System.out.println("key : " + x + " value : " + y));
        if (path.containsKey(v)) {
            System.out.print(v);
            T edge = path.get(v);
            while (edge != null) {
                System.out.print(" <--- " + edge);
                edge = path.get(edge);
            }
            System.out.println();
        }
        return path.keySet().stream().filter(value -> value == v).findFirst().isPresent();
    }

    Map breadthFirstSearch(Consumer consumer) {
        Map visited = new HashMap<T, Boolean>();
        Deque<Node<T>> nodes = new ArrayDeque<>();
        nodes.offer(this.source);
        Map path = new HashMap();
        //this.vertices.forEach(node -> visited.put(node.value, false));
        breadthFirstSearch(consumer, nodes, visited, path);
        return path;
    }

    private void breadthFirstSearch(Consumer<T> consumer, Queue<Node<T>> nodes, Map<T, Boolean> visited, Map<T, T> path) {
        while (!nodes.isEmpty()) {
            Node<T> node = nodes.poll();
            System.out.print("poll " + node.value + " \nQueue: ");
            nodes.stream().map(n -> n.value).forEach(System.out::print);
            System.out.println();
            visited.put(node.value, true);
            consumer.accept(node.value);
            node
                    .getEdges()
                    .forEach(edgeNode -> {
                        if (path.values().stream().noneMatch(v -> v == edgeNode.value)) {
                            path.put(edgeNode.value, node.value);
                        }
                        if (visited.get(edgeNode.value) == null || visited.get(edgeNode.value).equals(false)) {
                            nodes.offer(vertices.stream()
                                    .filter(nodeFilter -> nodeFilter.value == edgeNode.value)
                                    .findFirst()
                                    .get());
                            System.out.print("offer " + edgeNode.value + " \nQueue: ");
                            nodes.stream().map(n -> n.value).forEach(System.out::print);
                            System.out.println();
                        }
//                            Node<T> nextNode = this.vertices.stream()
//                                    .filter(nodeFilter -> nodeFilter.value == edgeNode.value)
//                                    .findFirst().orElse(null);
//                            if (nextNode!= null && node.value != nextNode.value) {
//                                breadthFirstSearch(consumer, nodes, visited, path);
//                            }
                    });
        }
    }

    Set<T> getEdges(T value) {
        Node node = this.vertices.stream().filter(foundNode -> foundNode.value == value).findFirst().orElseGet(() -> null);
        if (node != null)
            return (Set<T>) node.getEdges().stream().map(e -> ((Node) e).value).collect(Collectors.toSet());

        return new HashSet<>();
    }

    private class Node<T> {
        private T value;
        private Set<Node<T>> edges;

        Node(T value) {
            this.value = value;
            edges = new HashSet<>();
        }

        Set<Node<T>> addEdge(T value) {
            Node node = new Node(value);
            this.edges.add(node);
            return edges;
        }

        Set<Node<T>> getEdges() {
            return edges;
        }
    }
}


