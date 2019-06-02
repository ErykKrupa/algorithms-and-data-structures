package graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BipartiteGraph {
  private int length;
  private ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
  private long time;

  public BipartiteGraph(int size, int degree) {
    if (size < 1 || size > 16) {
      throw new IllegalArgumentException("Size must be in range (1..16).");
    }
    if (degree < 1 || degree > size) {
      throw new IllegalArgumentException("Degree must be greater than 0 and less than size.");
    }
    this.length = 1 << size;
    for (int i = 0; i < 2 * length + 2; i++) {
      graph.add(new ArrayList<>());
    }
    for (int i = 1; i <= length; i++) {
      graph.get(0).add(i);
      graph.get(i).add(0);
    }
    Random random = new Random();
    for (int i = 1; i <= length; i++) {
      Set<Integer> set = new HashSet<>();
      for (int j = 0; j < degree; j++) {
        Integer vertex;
        do {
          vertex = random.nextInt(length) + length + 1;
        } while (set.contains(vertex));
        set.add(vertex);
        graph.get(i).add(vertex);
        graph.get(vertex).add(i);
      }
    }
    for (int i = length + 1; i <= 2 * length; i++) {
      graph.get(i).add(2 * length + 1);
      graph.get(2 * length + 1).add(i);
    }
  }

  public int maxMatching() {
    long startTime = System.nanoTime();
    int maximalMatching = 0;
    ArrayList<HashSet<Integer>> residualNetwork = new ArrayList<>(2 * length + 2);
    for (int i = 0; i < 2 * length + 2; i++) {
      residualNetwork.add(new HashSet<>());
    }
    for (int i = 1; i <= length; i++) {
      residualNetwork.get(0).add(i);
    }
    for (int i = 1; i <= length; i++) {
      for (int j = 1; j < graph.get(i).size(); j++) {
        residualNetwork.get(i).add(graph.get(i).get(j));
      }
    }
    for (int i = length + 1; i <= 2 * length; i++) {
      residualNetwork.get(i).add(2 * length + 1);
    }
    int source = 0;
    int sink = 2 * length + 1;
    loop:
    while (true) {
      boolean[] visited = new boolean[2 * length + 2];
      int[] parent = new int[2 * length + 2];
      Queue<Integer> queue = new LinkedList<>();
      visited[source] = true;
      queue.add(source);
      while (!queue.isEmpty()) {
        Integer vertex = queue.poll();
        if (vertex == sink) {
          while (vertex != source) {
            Integer nextVertex = parent[vertex];
            residualNetwork.get(nextVertex).remove(vertex);
            residualNetwork.get(vertex).add(nextVertex);
            vertex = nextVertex;
          }
          maximalMatching++;
          continue loop;
        }
        for (Integer nextVertex : graph.get(vertex)) {
          if (!visited[nextVertex] && residualNetwork.get(vertex).contains(nextVertex)) {
            visited[nextVertex] = true;
            parent[nextVertex] = vertex;
            queue.add(nextVertex);
          }
        }
      }
      time = System.nanoTime() - startTime;
      return maximalMatching;
    }
  }

  public long getTime() {
    return time;
  }

  public void glpk(String file) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("w64\\" + file + ".mod"))) {
      writer.write(
          Graph.glpkContextFile
              + "printf \"Maximal matching is %g\\n\\n\", flow;\n"
              + "\n"
              + "data;\n"
              + "\n"
              + "param n := "
              + (2 * length + 2)
              + ";\n"
              + "\n"
              + "param : E :   a :=");
      for (int i = 2; i <= length + 1; i++) {
        writer.write("\n\t" + 1 + "\t" + i + "\t\t" + 1);
      }
      for (int i = 1; i <= length; i++) {
        for (int j = 1; j < graph.get(i).size(); j++) {
          writer.write("\n\t" + (i + 1) + "\t" + (graph.get(i).get(j) + 1) + "\t\t" + 1);
        }
      }
      for (int i = length + 2; i <= 2 * length + 1; i++) {
        writer.write("\n\t" + i + "\t" + (2 * length + 2) + "\t\t" + 1);
      }
      writer.write(";\n\n" + "end;\n");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 2 * length + 2; i++) {
      stringBuilder.append(i).append("\t->\t");
      for (int j = 0; j < graph.get(i).size(); j++) {
        stringBuilder.append(graph.get(i).get(j)).append("\t");
      }
      stringBuilder.append(System.lineSeparator());
    }
    return stringBuilder.toString();
  }
}
