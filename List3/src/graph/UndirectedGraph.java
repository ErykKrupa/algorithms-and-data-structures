package graph;

import priorityqueue.PriorityQueue;
import priorityqueue.PriorityQueueOnBinaryHeap;

import java.util.*;

public class UndirectedGraph<T> extends Graph<T> {
  private Map<T, T> sets;

  public boolean addEdge(T sourceNode, T sinkNode, Double weight) {
    if (graph.containsKey(sourceNode)
        && graph.containsKey(sinkNode)
        && weight >= 0
        && !graph.get(sourceNode).containsKey(sinkNode)
        && !graph.get(sinkNode).containsKey(sourceNode)) {
      graph.get(sourceNode).put(sinkNode, weight);
      graph.get(sinkNode).put(sourceNode, weight);
      return true;
    } else {
      return false;
    }
  }

  private class Pair {
    private T firstNode;
    private T secondNode;

    private T getFirstNode() {
      return firstNode;
    }

    private T getSecondNode() {
      return secondNode;
    }

    private Pair(T firstNode, T secondNode) {
      this.firstNode = firstNode;
      this.secondNode = secondNode;
    }
  }

  private void makeSet(T node) {
    sets.put(node, null);
  }

  private T find(T node) {
    if (!sets.containsKey(node)) {
      return null;
    } else if (sets.get(node) == null) {
      return node;
    } else {
      return find(sets.get(node));
    }
  }

  private void union(T firstNode, T secondNode) {
    T firstNodeRepresentative = find(firstNode);
    T secondNodeRepresentative = find(secondNode);
    if (firstNodeRepresentative != secondNodeRepresentative) {
      Stack<T> stack = new Stack<>();
      T node = firstNode;
      while (node != null) {
        stack.push(node);
        node = sets.get(node);
      }
      node = stack.pop();
      while (true) {
        T nextNode;
        if (!stack.empty()) {
          nextNode = stack.pop();
          sets.put(node, nextNode);
        } else {
          break;
        }
        node = nextNode;
      }
      sets.put(firstNode, secondNode);
    }
  }

  public void kruskal() {
    sets = new HashMap<>();
    for (Map.Entry<T, Map<T, Double>> nodeWithEdges : graph.entrySet()) {
      makeSet(nodeWithEdges.getKey());
    }
    PriorityQueue<Pair> queue = new PriorityQueueOnBinaryHeap<>();
    for (Map.Entry<T, Map<T, Double>> nodeWithEdges : graph.entrySet()) {
      for (Map.Entry<T, Double> edge : nodeWithEdges.getValue().entrySet()) {
        queue.insert(new Pair(nodeWithEdges.getKey(), edge.getKey()), edge.getValue());
      }
    }
    while (!queue.empty()) {
      Pair pair = queue.pop();
      union(pair.getFirstNode(), pair.getSecondNode());
    }
    print();
  }

  public void prim() {
    if (graph.size() == 0) {
      print();
      return;
    }
    sets = new HashMap<>();
    @SuppressWarnings("unchecked")
    T startNode = (T) graph.keySet().toArray()[0];
    makeSet(startNode);
    PriorityQueue<Pair> queue = new PriorityQueueOnBinaryHeap<>();
    for (Map.Entry<T, Double> edge : graph.get(startNode).entrySet()) {
      T nextNode = edge.getKey();
      if (nextNode != startNode) {
        queue.insert(new Pair(nextNode, startNode), edge.getValue());
      }
    }
    while (sets.size() < graph.size()) {
      Pair pair = queue.pop();
      if (sets.containsKey(pair.firstNode)) {
        continue;
      }
      sets.put(pair.firstNode, pair.secondNode);
      for (Map.Entry<T, Double> edge : graph.get(pair.firstNode).entrySet()) {
        T key = edge.getKey();
        if (!sets.containsKey(key)) {
          queue.insert(new Pair(key, pair.firstNode), edge.getValue());
        }
      }
    }
    print();
  }

  private void print() {
    System.out.println("Spanning tree: ");
    Double weightSum = 0.0;
    for (Map.Entry<T, Map<T, Double>> nodeWithEdges : graph.entrySet()) {
      T firstNode = nodeWithEdges.getKey();
      T secondNode = sets.get(firstNode); // node.getValue();
      if (secondNode == null) {
        continue;
      }
      Double weight = nodeWithEdges.getValue().get(secondNode);
      weightSum += weight;
      System.out.println(firstNode + " <---(" + weight + ")---> " + secondNode);
    }
    System.out.println("Weight sum: " + weightSum);
  }
}
