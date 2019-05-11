package graph;

import java.util.HashMap;
import java.util.Map;

public abstract class Graph<T> {
  Map<T, Map<T, Double>> graph = new HashMap<>();

  public boolean addNode(T node) {
    if (!graph.containsKey(node)) {
      Map<T, Double> edges = new HashMap<>();
      edges.put(node, 0.0);
      graph.put(node, edges);
      return true;
    }
    return false;
  }

  public abstract boolean addEdge(T sourceNode, T sinkNode, Double weight);
}
