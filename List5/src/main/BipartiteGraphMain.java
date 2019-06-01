package main;

import graph.BipartiteGraph;

public class BipartiteGraphMain {

  private static void error() {
    System.err.println("Incorrect input");
    System.exit(1);
  }

  public static void main(String[] args) {
    int size = 0;
    int degree = 0;
    if (args.length == 4) {
      try {
        if (args[0].equals("--size") || args[0].equals("-s")) {
          size = Integer.parseInt(args[1]);
        } else if (args[2].equals("--size") || args[2].equals("-s")) {
          size = Integer.parseInt(args[3]);
        } else {
          error();
        }
        if (args[0].equals("--degree") || args[0].equals("-d")) {
          degree = Integer.parseInt(args[1]);
        } else if (args[2].equals("--degree") || args[2].equals("-d")) {
          degree = Integer.parseInt(args[3]);
        } else {
          error();
        }
      } catch (NumberFormatException ex) {
        error();
      }
    } else {
      error();
    }

    BipartiteGraph graph = new BipartiteGraph(size, degree);
    System.out.println("Size: " + size);
    System.out.println("Degree: " + degree);
    System.out.println("Maximal Matching: " + graph.algorithm());
    System.err.println("Time: " + graph.getTime() + " [ms]");
    //    System.out.println(graph.toString());
  }
}
