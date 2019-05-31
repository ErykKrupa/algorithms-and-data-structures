package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import static java.lang.Math.*;

public class Graph {
  private int size;
  private int length;
  private int[][] capacities;
  private int augmentingPaths;

  public Graph(int size) {
    if (size < 1 || size > 16) {
      throw new IllegalArgumentException("Size must be in range (1..16).");
    }
    this.size = size;
    this.length = (int) pow(2, size);
    capacities = new int[length][size];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int weight = countHammingWeight(i);
      int max = (int) pow(2, 2 * weight + 1 > size ? weight + 1 : size - weight);
      int multiplier = 1;
      for (int j = 0; j < size; j++) {
        capacities[i][j] = (i & multiplier) == 0 ? random.nextInt(max) + 1 : 0;
        multiplier = multiplier << 1;
      }
    }
  }

  private int countHammingWeight(int vertex) {
    int count = 0;
    while (vertex != 0) {
      if (vertex % 2 == 1) {
        count++;
      }
      vertex = vertex >> 1;
    }
    return count;
  }

  public int edmondsKarp() {
    augmentingPaths = 0;
    int[][] residualCapacities = new int[length][size];
    for (int i = 0; i < length; i++) {
      if (size >= 0) System.arraycopy(capacities[i], 0, residualCapacities[i], 0, size);
    }
    while (true) {
      int[] way = breadthFirstSearch(residualCapacities);
      augmentingPaths++;
      if (way != null) {
        int minResidualCapacity = Integer.MAX_VALUE;
        for (int i = 0; i < way.length - 1; i++) {
          int position = (int) Math.round(log(way[i] ^ way[i + 1]) / log(2));
          int residualCapacity = residualCapacities[way[i]][position];
          if (minResidualCapacity > residualCapacity) {
            minResidualCapacity = residualCapacity;
          }
        }
        for (int i = 0; i < way.length - 1; i++) {
          int position = (int) Math.round(log(way[i] ^ way[i + 1]) / log(2));
          int residualCapacity = residualCapacities[way[i]][position];
          residualCapacities[way[i]][position] = residualCapacity - minResidualCapacity;
          residualCapacity = residualCapacities[way[i + 1]][position];
          residualCapacities[way[i + 1]][position] = residualCapacity + minResidualCapacity;
        }
      } else {
        break;
      }
    }
    int maxFlow = 0;
    for (int i = 0; i < size; i++) {
      maxFlow += capacities[0][i] - residualCapacities[0][i];
    }
    return maxFlow;
  }

  private int[] breadthFirstSearch(int[][] residualCapacities) {
    int source = 0;
    int sink = length - 1;
    boolean[] visited = new boolean[length];
    HashMap<Integer, Integer> parent = new HashMap<>();
    ArrayList<Integer> queue = new ArrayList<>();
    visited[source] = true;
    parent.put(source, null);
    queue.add(source);
    while (!queue.isEmpty()) {
      Integer vertex = queue.remove(0);
      if (vertex == sink) {
        LinkedList<Integer> reversedWay = new LinkedList<>();
        while (vertex != null) {
          reversedWay.add(vertex);
          vertex = parent.get(vertex);
        }
        int[] way = new int[reversedWay.size()];
        for (int i = 0; i < way.length; i++) {
          way[i] = reversedWay.removeLast();
        }
        return way;
      }
      for (int nextVertex : getNeighbouringVertices(vertex)) {
        int position = (int) Math.round(log(vertex ^ nextVertex) / log(2));
        if (!visited[nextVertex] && residualCapacities[vertex][position] > 0) {
          visited[nextVertex] = true;
          parent.put(nextVertex, vertex);
          queue.add(nextVertex);
        }
      }
    }
    return null;
  }

  private int[] getNeighbouringVertices(int vertex) {
    int[] neighbours = new int[size];
    int multiplier = 1;
    for (int i = 0; i < size; i++) {
      neighbours[i] = vertex ^ multiplier;
      multiplier = multiplier << 1;
    }
    return neighbours;
  }

  public int getAugmentingPaths() {
    return augmentingPaths;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int[] capacity : capacities) {
      for (int i = size - 1; i >= 0; i--) {
        stringBuilder.append("[").append(capacity[i]).append("]");
      }
      stringBuilder.append(System.lineSeparator());
    }
    return stringBuilder.toString();
  }
}
