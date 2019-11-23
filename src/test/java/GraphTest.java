import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class GraphTest {

    Graph<Integer> graph;

    @BeforeEach
    void setUp() {
        graph = new Graph<>(0);
    }

    @Test
    void addEdge() {
        graph.addEdge(0, 5);
        assertThat(graph.getEdges(0).toArray()).isEqualTo(new Integer[]{5});
        assertThat(graph.getEdges(5).toArray()).isEqualTo(new Integer[]{0});
    }

    @Test
    void depthFirstSearch() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 7);
        graph.addEdge(0, 7);
        graph.addEdge(7, 8);
        Map nodes = graph.depthFirstSearch((node) -> {});
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
        assertThat(graph.hasPathTo(6)).isTrue();
    }

    @Test
    void breadthFirstSearch() {
        graph.addEdge(0, 5);
        graph.addEdge(5, 7);
        graph.addEdge(0, 7);
        graph.addEdge(7, 8);
        Map nodes = graph.breadthFirstSearch((node) -> {});
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
        assertThat(graph.getEdges(0).toArray()).isEqualTo(new Integer[]{5, 7});

    }
}