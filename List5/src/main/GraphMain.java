package main;

import graph.Graph;

public class GraphMain {

  private static void error() {
    System.err.println("Incorrect input");
    System.exit(1);
  }

  public static void main(String[] args) {
    int size = 0;
    String file = "";
    boolean glpk = false;
    try {
      if (args.length == 2 && (args[0].equals("--size") || args[0].equals("-s"))) {
        size = Integer.parseInt(args[1]);
      } else if (args.length == 4) {
        glpk = true;
        if ((args[0].equals("--size") || args[0].equals("-s"))
            && (args[2].equals("--glpk") || args[2].equals("-g"))) {
          size = Integer.parseInt(args[1]);
          file = args[3];
        } else if ((args[0].equals("--glpk") || args[0].equals("-g"))
            && (args[2].equals("--size") || args[2].equals("-s"))) {
          size = Integer.parseInt(args[3]);
          file = args[1];
        } else {
          error();
        }
      } else {
        error();
      }
    } catch (NumberFormatException ex) {
      error();
    }
    Graph graph = new Graph(size);
    System.out.println("Size: " + size);
    System.out.println("Max Flow: " + graph.edmondsKarp());
    System.err.println("Time: " + graph.getTime() + " [ms]");
    System.err.println("Augmenting Paths: " + graph.getAugmentingPaths());
    if (glpk) {
      graph.glpk(file);
      System.out.println("File " + file + ".mod has been generated");
    }
  }
}
