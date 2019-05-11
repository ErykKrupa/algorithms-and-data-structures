package graph;

import priorityqueue.PriorityQueue;
import priorityqueue.PriorityQueueOnBinaryHeap;

import java.util.*;

public class DirectedGraph<T> extends Graph<T> {

  public boolean addEdge(T sourceNode, T sinkNode, Double weight) {
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

  public void dijkstra(T sourceNode) {
    if (!graph.containsKey(sourceNode)) {
      System.err.println("Graph doesn't contain that node");
      return;
    }
    long timeStart = System.currentTimeMillis();
    Map<T, Double> weights = new HashMap<>();
    Map<T, T> precursors = new HashMap<>();
    PriorityQueue<T> queue = new PriorityQueueOnBinaryHeap<>();
    for (Map.Entry<T, Map<T, Double>> nodeWithEdges : graph.entrySet()) {
      weights.put(nodeWithEdges.getKey(), Double.MAX_VALUE);
      precursors.put(nodeWithEdges.getKey(), null);
      queue.insert(nodeWithEdges.getKey(), Double.MAX_VALUE);
    }
    weights.put(sourceNode, 0.0);
    queue.priority(sourceNode, 0.0);
    while (!queue.empty()) {
      T node = queue.pop();
      for (Map.Entry<T, Double> edge : graph.get(node).entrySet()) {
        double newWeight = weights.get(node) + edge.getValue();
        if (weights.get(edge.getKey()) > newWeight) {
          weights.put(edge.getKey(), newWeight);
          precursors.put(edge.getKey(), node);
          queue.priority(edge.getKey(), newWeight);
        }
      }
    }
    long time = System.currentTimeMillis() - timeStart;
    for (Map.Entry<T, Double> weight : weights.entrySet()) {
      System.out.println(
          ""
              + weight.getKey()
              + " "
              + (weight.getValue() == Double.MAX_VALUE ? "-" : weight.getValue()));
      printWay(weight.getKey(), precursors);
    }
    System.err.println(time + " [ms]");
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
