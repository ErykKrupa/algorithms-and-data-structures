package datagenerator;

import graph.BipartiteGraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BipartiteGraphDataGenerator {
  private static int TESTS = 100;

  public static void main(String[] args) {
    long[][] maximalMatching = new long[8][];
    long[][] times = new long[8][];
    for (int i = 0; i < 8; i++) {
      maximalMatching[i] = new long[i + 3];
      times[i] = new long[i + 3];
    }
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < i + 3; j++) {
        for (int k = 0; k < TESTS; k++) {
          BipartiteGraph graph = new BipartiteGraph(i + 3, j + 1);
          maximalMatching[i][j] += graph.maxMatching();
          times[i][j] += graph.getTime();
        }
      }
    }
    try (BufferedWriter writer =
        new BufferedWriter(new FileWriter("bipartite-graph-results.csv"))) {
      writer.write(";Degree:;");
      for (int i = 1; i <= 10; i++) {
        writer.write(i + ";");
      }
      writer.newLine();
      writer.newLine();
      writer.write("Size:;;Maximal Matching:");
      write(writer, maximalMatching, 1.0);
      writer.newLine();
      writer.write(";;Times:");
      write(writer, times, 1_000_000.0);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private static void write(BufferedWriter writer, long[][] array, double divisor)
      throws IOException {
    writer.newLine();
    for (int i = 0; i < 8; i++) {
      writer.write(i + 3 + ";;");
      for (int j = 0; j < i + 3; j++) {
        writer.write((array[i][j] / divisor) / TESTS + ";");
      }
      writer.newLine();
    }
  }
}
