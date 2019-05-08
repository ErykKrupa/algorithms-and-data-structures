package graph;

import java.util.HashMap;
import java.util.Map;

abstract class Graph<T> {
  Map<T, Map<T, Integer>> graph = new HashMap<>();

  boolean addNode(T node) {
    if (!graph.containsKey(node)) {
      Map<T, Integer> edges = new HashMap<>();
      edges.put(node, 0);
      graph.put(node, edges);
      return true;
    }
    return false;
  }

  abstract boolean addEdge(T sourceNode, T sinkNode, Integer weight);
}
