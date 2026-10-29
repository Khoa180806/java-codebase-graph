package dev.khoa.codegraph.warmup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class GraphBfsTest {

    @Test
    void shouldTraverseAllNodesWhenGraphHasCycles() {
        Map<String, Set<String>> graph = new HashMap<>();
        addEdge(graph, "A", "B");
        addEdge(graph, "B", "C");
        addEdge(graph, "C", "A");
        addEdge(graph, "B", "D");

        List<String> result = bfs(graph, "A");

        assertThat(result).hasSize(4).containsExactlyInAnyOrder("A", "B", "C", "D");
        assertThat(result.get(0)).isEqualTo("A");
    }

    List<String> bfs(Map<String, Set<String>> graph, String startNode) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            result.add(current);

            Set<String> neighbor = graph.getOrDefault(current, Collections.emptySet());
            for (String node : neighbor) {
                if (visited.add(node)) {
                    queue.add(node);
                }
            }
        }
        return result;
    }

    void addEdge(Map<String, Set<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }
}
