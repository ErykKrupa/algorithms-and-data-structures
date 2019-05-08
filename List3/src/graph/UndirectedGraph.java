package graph;

import priorityqueue.PriorityQueue;
import priorityqueue.PriorityQueueOnBinaryHeap;

import java.util.*;

class UndirectedGraph<T> extends Graph<T> {
  private Map<T, T> sets;

  boolean addEdge(T sourceNode, T sinkNode, Integer weight) {
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
  class Pair {
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

  void kruskal() {
    sets = new HashMap<>();
    for (Map.Entry<T, Map<T, Integer>> node : graph.entrySet()) {
      makeSet(node.getKey());
    }
    PriorityQueue<Pair> queue = new PriorityQueueOnBinaryHeap<>();
    for (Map.Entry<T, Map<T, Integer>> node : graph.entrySet()) {
      for (Map.Entry<T, Integer> edge : node.getValue().entrySet()) {
        queue.insert(new Pair(node.getKey(), edge.getKey()), edge.getValue());
      }
    }
    while (!queue.empty()) {
      Pair pair = queue.pop();
      union(pair.getFirstNode(), pair.getSecondNode());
    }
    print();
  }

  private void print() {
    System.out.println("Spanning tree: ");
    Integer weightSum = 0;
    for (Map.Entry<T, Map<T, Integer>> node : graph.entrySet()) {
      T firstNode = node.getKey();
      T secondNode = sets.get(firstNode); // node.getValue();
      if (secondNode == null) {
        continue;
      }
      Integer weight = node.getValue().get(secondNode);
      weightSum += weight;
      System.out.println(firstNode + " <---(" + weight + ")---> " + secondNode);
    }
    System.out.println("Weight sum: " + weightSum);
  }


}
