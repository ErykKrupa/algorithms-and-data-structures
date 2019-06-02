package datagenerator;

import graph.Graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraphDataGenerator {
  private static final int TESTS = 100;

  public static void main(String[] args) {
    int[][] maxFlows = new int[16][TESTS];
    int[][] augmentingPaths = new int[16][TESTS];
    long[][] times = new long[16][TESTS];
    for (int i = 0; i < TESTS; i++) {
      for (int j = 0; j < 16; j++) {
        Graph graph = new Graph(j + 1);
        maxFlows[j][i] = graph.edmondsKarp();
        augmentingPaths[j][i] = graph.getAugmentingPaths();
        times[j][i] = graph.getTime();
      }
      System.out.println(i + 1 + "/" + TESTS);
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("graph-results.csv"))) {
      for (int i = 0; i < 3; i++) {
        for (int j = 1; j <= 16; j++) {
          writer.write(j + ";");
        }
        writer.write(";");
      }
      writer.newLine();
      writer.write("Max Flows:;;;;;;;;;;;;;;;;;");
      writer.write("Augmenting Paths:;;;;;;;;;;;;;;;;;");
      writer.write("Times:;;;;;;;;;;;;;;;;;");
      writer.newLine();
      for (int i = 0; i < TESTS; i++) {
        for (int j = 0; j < 16; j++) {
          writer.write(maxFlows[j][i] + ";");
        }
        writer.write(";");
        for (int j = 0; j < 16; j++) {
          writer.write(augmentingPaths[j][i] + ";");
        }
        writer.write(";");
        for (int j = 0; j < 16; j++) {
          writer.write(times[j][i] / 1_000_000.0 + ";");
        }
        writer.newLine();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
