package dev.khoa.codegraph.warmup;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GraphAdjacencyMapTest {

    @Test
    void shouldAddEdgesAndIgnoreDuplicates() {
        Map<String, Set<String>> graph = new HashMap<>();

        addEdge(graph, "A", "B");
        addEdge(graph, "A", "C");
        addEdge(graph, "A", "B");

        assertThat(graph.get("A")).containsExactlyInAnyOrder("B", "C");
    }

    void addEdge(Map<String, Set<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }
}
