package graph;

import java.util.Random;
import static java.lang.Math.*;

public class Graph {
  private int size;
  private int[][] graph;

  public Graph(int size) {
    if (size < 1 || size > 16) {
      throw new IllegalArgumentException("Size must be in range (1..16).");
    }
    this.size = size;
    graph = new int[(int) pow(2, size)][size];
    Random random = new Random();
    for (int i = 0; i < graph.length; i++) {
      String binary = toBinary(i);
      int hammingWeight = countHammingWeight(binary);
      int max = 2 * hammingWeight + 1 > size ? hammingWeight + 1 : size - hammingWeight;
      for (int j = 0; j < size; j++) {
        graph[i][j] = binary.charAt(j) == '1' ? 0 : random.nextInt((int) pow(2, max)) + 1;
      }
    }
  }

  private String toBinary(int node) {
    StringBuilder binary = new StringBuilder(Integer.toBinaryString(node)).reverse();
    for (int i = binary.length(); i < size; i++) {
      binary.append("0");
    }
    return binary.toString();
  }

  private int countHammingWeight(String binary) {
    int count = 0;
    for (int i = 0; i < binary.length(); i++) {
      if (binary.charAt(i) == '1') {
        count++;
      }
    }
    return count;
  }

  public int getFlow(int source, int sink) {
    int diffBit;
    try {
      diffBit = diffBit(toBinary(source), toBinary(sink));
      return graph[source][diffBit];
    } catch (NoEdgeException ignore) {
      return 0;
    }
  }

  private int diffBit(String vertexFirst, String vertexSecond) throws NoEdgeException {
    int diffBit = -1;
    for (int i = 0; i < size; i++) {
      if (vertexFirst.charAt(i) != vertexSecond.charAt(i)) {
        if (diffBit == -1) {
          diffBit = i;
        } else {
          throw new NoEdgeException("No edge.");
        }
      }
    }
    if (diffBit == -1) {
      throw new NoEdgeException("No edge.");
    }
    return diffBit;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int[] array : graph) {
        stringBuilder.append("[").append(array[i]).append("]");
      }
      stringBuilder.append(System.lineSeparator());
    }
    return stringBuilder.toString();
  }
}
