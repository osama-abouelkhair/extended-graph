import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class GraphTest {

    Graph<Integer> graph;

    @BeforeEach
    void setUp() {
        graph = Graph.emptyGraph();
    }

    @Test
    void addEdge() {
        graph.addEdge(0, 5);
        assertThat(graph.getEdges(0)).hasValueSatisfying(edges -> assertThat(edges).contains(5));
        assertThat(graph.getEdges(5)).hasValueSatisfying(edges -> assertThat(edges).contains(0));
    }

    @Test
    void depthFirstSearch() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 7);
        graph.addEdge(0, 7);
        graph.addEdge(7, 8);
        Map nodes = graph.depthFirstSearch(0, (node) -> {});
        assertThat(nodes)
                .hasSize(3)
                .containsEntry(5, 0)
                .containsEntry(7, 5)
                .containsEntry(8, 7);
    }

    @Test
    void hasPathTo() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 6);
        assertThat(graph.hasPathTo(0, 6)).isTrue();
    }

    @Test
    void breadthFirstSearch() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 7);
        graph.addEdge(0, 7);
        graph.addEdge(7, 8);
        Map nodes = graph.breadthFirstSearch(0, (node) -> {});
        assertThat(nodes)
                .hasSize(3)
                .containsEntry(5, 0)
                .containsEntry(7, 0)
                .containsEntry(8, 7);
    }

    @Test
    void getEdges() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 6);
        graph.addEdge(0, 7);
        assertThat(graph.getEdges(0)).hasValueSatisfying(edges -> assertThat(edges).contains(5).contains(7));
    }
}