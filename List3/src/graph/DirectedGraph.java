package graph;

import priorityqueue.PriorityQueue;
import priorityqueue.PriorityQueueOnBinaryHeap;
import java.util.Map;
import java.util.HashMap;

class DirectedGraph<T> extends Graph<T> {

  boolean addEdge(T sourceNode, T sinkNode, Integer weight) {
    if (graph.containsKey(sourceNode)
        && graph.containsKey(sinkNode)
        && weight >= 0
        && !graph.get(sourceNode).containsKey(sinkNode)) {
      graph.get(sourceNode).put(sinkNode, weight);
      return true;
    } else {
      return false;
    }
  }

  boolean dijkstra(T sourceNode) {
    if (!graph.containsKey(sourceNode)) {
      return false;
    }
    long timeStart = System.currentTimeMillis();
    Map<T, Integer> weights = new HashMap<>();
    Map<T, T> precursors = new HashMap<>();
    PriorityQueue<T> queue = new PriorityQueueOnBinaryHeap<>();
    for (Map.Entry<T, Map<T, Integer>> node : graph.entrySet()) {
      weights.put(node.getKey(), Integer.MAX_VALUE);
      precursors.put(node.getKey(), null);
      queue.insert(node.getKey(), Integer.MAX_VALUE);
    }
    weights.put(sourceNode, 0);
    queue.priority(sourceNode, 0);
    while (!queue.empty()) {
      T node = queue.pop();
      for (Map.Entry<T, Integer> edge : graph.get(node).entrySet()) {
        int newWeight = weights.get(node) + edge.getValue();
        if (weights.get(edge.getKey()) > newWeight) {
          weights.put(edge.getKey(), newWeight);
          precursors.put(edge.getKey(), node);
          queue.priority(edge.getKey(), newWeight);
        }
      }
    }
    long time = System.currentTimeMillis() - timeStart;
    for (Map.Entry<T, Integer> weight : weights.entrySet()) {
      System.out.println(
          ""
              + weight.getKey()
              + " "
              + (weight.getValue() == Integer.MAX_VALUE ? "-" : weight.getValue()));
      printWay(weight.getKey(), precursors);
    }
    System.err.println(time + " [ms]");
    return true;
  }

  private boolean printWay(T sinkNode, Map<T, T> precursors) {
    if (sinkNode != null) {
      T node = precursors.get(sinkNode);
      if (printWay(node, precursors)) {
        System.err.println("" + sinkNode + " " + graph.get(node).get(sinkNode));
      }
      return true;
    } else {
      return false;
    }
  }
}
