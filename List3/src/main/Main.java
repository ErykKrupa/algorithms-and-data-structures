package main;

import graph.Graph;

import java.util.Scanner;

abstract class Main {
  static Scanner scanner = new Scanner(System.in);

  static int inputPositiveInteger() {
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

  static void inputGraph(Graph<Integer> graph) {
    System.out.print("Nodes quantity: ");
    int nodesQuantity = inputPositiveInteger();
    System.out.print("Edges quantity: ");
    int edgesQuantity = inputPositiveInteger();

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
          Double weightNode = Double.parseDouble(edge[2]);
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
  }
}
