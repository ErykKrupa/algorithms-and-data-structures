package main;

import graph.Graph;

public class GraphMain {
  public static void main(String[] args) {
    int size;
    if (args.length == 2 && (args[0].equals("--size") || args[0].equals("-s"))) {
      try {
        size = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        System.err.println("Incorrect input");
        return;
      }
    } else {
      System.err.println("Incorrect input");
      return;
    }
    Graph graph = new Graph(size);
    System.out.println("Size: " + size);
    System.out.println("Max Flow: " + graph.edmondsKarp());
    System.err.println("Time: " + graph.getTime() + " [ms]");
    System.err.println("Augmenting Paths: " + graph.getAugmentingPaths());
  }
}
