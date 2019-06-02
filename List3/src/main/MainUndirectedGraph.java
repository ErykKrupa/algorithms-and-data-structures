package main;

import graph.UndirectedGraph;

public class MainUndirectedGraph extends Main {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("One argument required");
      return;
    }
    String algorithm = args[0];
    if (!algorithm.equals("-k") && !algorithm.equals("-p")) {
      System.err.println("Argument should be \"-k\" or \"-p\"");
      return;
    }

    UndirectedGraph<Integer> graph = new UndirectedGraph<>();
    inputGraph(graph);

//    if (algorithm.equals("-k")) {
      graph.kruskal();
//    } else {
      graph.prim();
//    }
  }
}
