package graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    long startTime = System.nanoTime();
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
      time = System.nanoTime() - startTime;
      return maxFlow;
    }
  }

  public int getAugmentingPaths() {
    return augmentingPaths;
  }

  public long getTime() {
    return time;
  }

  public void glpk(String file) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("w64\\" + file + ".mod"))) {
      writer.write(glpkContextFile + "printf \"Maximum flow is %g\\n\\n\", flow;\n" + "\n" + "data;\n" + "\n"
              + "param n := " + length + ";\n" + "\n" + "param : E :   a :=");
      for (int i = 0; i < length; i++) {
        for (int j = 0; j < size; j++) {
          if (capacities[i][j] != 0) {
            writer.write("\n\t" + (i + 1) + "\t" + (i + (1 << j) + 1) + "\t\t" + capacities[i][j]);
          }
        }
      }
      writer.write(";\n\n" + "end;\n");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
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

  static final String glpkContextFile =
      "param n, integer, >= 2;\n"
          + "/* number of nodes */\n"
          + "\n"
          + "set V, default {1..n};\n"
          + "/* set of nodes */\n"
          + "\n"
          + "set E, within V cross V;\n"
          + "/* set of arcs */\n"
          + "\n"
          + "param a{(i,j) in E}, > 0;\n"
          + "/* a[i,j] is capacity of arc (i,j) */\n"
          + "\n"
          + "param s, symbolic, in V, default 1;\n"
          + "/* source node */\n"
          + "\n"
          + "param t, symbolic, in V, != s, default n;\n"
          + "/* sink node */\n"
          + "\n"
          + "var x{(i,j) in E}, >= 0, <= a[i,j];\n"
          + "/* x[i,j] is elementary flow through arc (i,j) to be found */\n"
          + "\n"
          + "var flow, >= 0;\n"
          + "/* total flow from s to t */\n"
          + "\n"
          + "s.t. node{i in V}:\n"
          + "/* node[i] is conservation constraint for node i */\n"
          + "\n"
          + "   sum{(j,i) in E} x[j,i] + (if i = s then flow)\n"
          + "   /* summary flow into node i through all ingoing arcs */\n"
          + "\n"
          + "   = /* must be equal to */\n"
          + "\n"
          + "   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n"
          + "   /* summary flow from node i through all outgoing arcs */\n"
          + "\n"
          + "maximize obj: flow;\n"
          + "/* objective is to maximize the total flow through the network */\n"
          + "\n"
          + "solve;\n"
          + "\n"
          + "printf{1..56} \"=\"; printf \"\\n\";\n";
}
