package graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BipartiteGraph {
  private int degree;
  private int length;
  private int[][] vertices1;
  private int[][] vertices2;
  private long time;

  private ArrayList<Integer> list;
  private int number;
  private int index = -1;

  private int getNextVertex() {
    number++;
    if (number >= length) {
      number = 0;
      Collections.shuffle(list);
      index++;
    }
    return list.get(number);
  }

  public BipartiteGraph(int size, int degree) {
    if (size < 1 || size > 16) {
      throw new IllegalArgumentException("Size must be in range (1..16).");
    }
    if (degree < 1 || degree > size) {
      throw new IllegalArgumentException("Degree must be greater than 0 and less than size.");
    }
    this.degree = degree;
    this.length = 1 << size;
    this.number = length;
    vertices1 = new int[length][degree];
    vertices2 = new int[length][degree];
    list = new ArrayList<>(length);
    for (int i = 0; i < length; i++) {
      list.add(i);
    }
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < degree; j++) {
        int vertex = getNextVertex();
        vertices1[i][j] = vertex;
        vertices2[vertex][index] = i;
      }
    }
  }

  public int maxMatching() {
    long startTime = System.nanoTime();
    int maximalMatching = 0;
    ArrayList<ArrayList<Integer>> vertices = new ArrayList<>(2 * length + 2);
    ArrayList<HashSet<Integer>> residualCapacities = new ArrayList<>(2 * length + 2);
    for (int i = 0; i < 2 * length + 2; i++) {
      vertices.add(new ArrayList<>());
      residualCapacities.add(new HashSet<>());
    }
    for (int i = 1; i <= length; i++) {
      for (int j = 0; j < degree; j++) {
        vertices.get(i).add(vertices1[i - 1][j] + length + 1);
        residualCapacities.get(i).add(vertices1[i - 1][j] + length + 1);
      }
      vertices.get(0).add(i);
      residualCapacities.get(0).add(i);
      vertices.get(i).add(0);
    }
    for (int i = length + 1; i <= 2 * length; i++) {
      for (int j = 0; j < degree; j++) {
        vertices.get(i).add(vertices2[i - length - 1][j] + 1);
      }
      vertices.get(i).add(2 * length + 1);
      residualCapacities.get(i).add(2 * length + 1);
      vertices.get(2 * length + 1).add(i);
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
            residualCapacities.get(nextVertex).remove(vertex);
            residualCapacities.get(vertex).add(nextVertex);
            vertex = nextVertex;
          }
          maximalMatching++;
          continue loop;
        }
        for (Integer nextVertex : vertices.get(vertex)) {
          if (!visited[nextVertex] && residualCapacities.get(vertex).contains(nextVertex)) {
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
      writer.write(Graph.glpkContextFile + "printf \"Maximal matching is %g\\n\\n\", flow;\n" + "\n" + "data;\n" + "\n"
              + "param n := " + (2 * length + 2) + ";\n" + "\n" + "param : E :   a :=");
      for (int i = 2; i <= length + 1; i++) {
        writer.write("\n\t" + 1 + "\t" + i + "\t\t" + 1);
      }
      for (int i = 2; i <= length + 1; i++) {
        for (int j = 0; j < degree; j++) {
          writer.write("\n\t" + i + "\t" + (vertices1[i - 2][j] + length + 2) + "\t\t" + 1);
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
    return toString(vertices1) + System.lineSeparator() + toString(vertices2);
  }

  private String toString(int[][] vertices) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < degree; j++) {
        stringBuilder.append("[").append(vertices[i][j]).append("]");
      }
      stringBuilder.append(System.lineSeparator());
    }
    return stringBuilder.toString();
  }
}
