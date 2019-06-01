package graph;

import java.util.*;

public class Graph {
  private int size;
  private int length;
  private int[][] capacities;
  private int augmentingPaths;
  private long time;

  public Graph(int size) {
    if (size < 1 || size > 16) {
      throw new IllegalArgumentException("Size must be in range (1..16).");
    }
    this.size = size;
    this.length = 1 << size;
    capacities = new int[length][size];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int weight = countHammingWeight(i);
      int max = 1 << (2 * weight + 1 > size ? weight + 1 : size - weight);
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
    long startTime = System.currentTimeMillis();
    augmentingPaths = 0;
    int maxFlow = 0;
    int[][] residualCapacities = new int[length][size];
    for (int i = 0; i < length; i++) {
      if (size >= 0) System.arraycopy(capacities[i], 0, residualCapacities[i], 0, size);
    }
    int source = 0;
    int sink = length - 1;
    loop:
    while (true) {
      augmentingPaths++;
      boolean[] visited = new boolean[length];
      int[] parent = new int[length];
      Queue<Integer> queue = new LinkedList<>();
      visited[source] = true;
      queue.add(source);
      while (!queue.isEmpty()) {
        Integer vertex = queue.poll();
        if (vertex == sink) {
          int position;
          int minResidualCapacity = Integer.MAX_VALUE;
          while (vertex != source) {
            position = parent[vertex];
            vertex = vertex ^ (1 << position);
            int residualCapacity = residualCapacities[vertex][position];
            if (minResidualCapacity > residualCapacity) {
              minResidualCapacity = residualCapacity;
            }
          }
          vertex = sink;
          while (vertex != source) {
            position = parent[vertex];
            residualCapacities[vertex][position] += minResidualCapacity;
            vertex = vertex ^ (1 << position);
            residualCapacities[vertex][position] -= minResidualCapacity;
          }
          maxFlow += minResidualCapacity;
          continue loop;
        }
        for (int i = 0; i < size; i++) {
          int nextVertex = vertex ^ (1 << i);
          if (!visited[nextVertex] && residualCapacities[vertex][i] > 0) {
            visited[nextVertex] = true;
            parent[nextVertex] = i;
            queue.add(nextVertex);
          }
        }
      }
      time = System.currentTimeMillis() - startTime;
      return maxFlow;
    }
  }

  public int getAugmentingPaths() {
    return augmentingPaths;
  }

  public long getTime() {
    return time;
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
