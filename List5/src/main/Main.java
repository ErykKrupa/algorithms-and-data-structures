package main;

import graph.Graph;

public class Main {
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
    System.out.print(size + " : ");
    System.out.print(graph.edmondsKarp() + " : ");
    System.out.print(graph.getAugmentingPaths() + " : ");
    System.out.println(graph.getTime() + " [ms]");
  }
}
