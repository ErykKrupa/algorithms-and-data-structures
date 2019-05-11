package main;

import graph.DirectedGraph;

public class MainDirectedGraph extends Main {
  public static void main(String[] args) {
    if (args.length != 1 && args.length != 2) {
      System.err.println("One or two arguments required");
      return;
    }
    boolean dijkstra = false, kosaraju = false;
    if (!args[0].equals("-d") && !args[0].equals("-k")
        || (args.length == 2 && !args[1].equals("-d") && !args[1].equals("-k"))) {
      System.err.println("Argument should be \"-d\" or \"-k\"");
      return;
    }
    if (args[0].equals("-d") || (args.length == 2 && args[1].equals("-d"))) {
      dijkstra = true;
    }
    if (args[0].equals("-k") || (args.length == 2 && args[1].equals("-k"))) {
      kosaraju = true;
    }

    DirectedGraph<Integer> graph = new DirectedGraph<>();
    inputGraph(graph);

    if (dijkstra) {
      System.out.print("Source node: ");
      graph.dijkstra(inputPositiveInteger());
    }
    if (kosaraju) {
//      TODO Kosaraju's algorithm
    }
  }
}
