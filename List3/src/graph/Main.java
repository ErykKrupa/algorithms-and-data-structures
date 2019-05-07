package graph;

import java.util.Scanner;

public class Main {
  private static Scanner scanner = new Scanner(System.in);

  private static int inputPositiveInteger() {
    int positive;
    while (true) {
      try {
        positive = Integer.parseInt(scanner.nextLine());
        if (positive > 0) {
          return positive;
        }
      } catch (NumberFormatException ignored) {
      }
      System.err.print("Once again: ");
    }
  }

  public static void main(String[] args) {
    System.out.print("Nodes quantity: ");
    int nodesQuantity = inputPositiveInteger();
    System.out.print("Edges quantity: ");
    int edgesQuantity = inputPositiveInteger();

    Graph<Integer> graph = new Graph<>();
    for (int i = 1; i <= nodesQuantity; i++) {
      graph.addNode(i);
    }

    System.out.println("Edges: ");
    for (int i = 1; i <= edgesQuantity; i++) {
      String[] edge = scanner.nextLine().split(" ");
      try {
        if (edge.length == 3) {
          Integer sourceNode = Integer.parseInt(edge[0]);
          Integer sinkNode = Integer.parseInt(edge[1]);
          Integer weightNode = Integer.parseInt(edge[2]);
          if (graph.addEdge(sourceNode, sinkNode, weightNode)) {
            System.out.println(i + ". " + sourceNode + " ---(" + weightNode + ")---> " + sinkNode);
            continue;
          }
        }
      } catch (NumberFormatException ignored) {
      }
      i--;
      System.err.print("Once again: ");
    }
    System.out.println("Source node: ");
    graph.dijkstra(inputPositiveInteger());
  }
}
