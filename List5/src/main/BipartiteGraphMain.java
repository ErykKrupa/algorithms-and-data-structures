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
    String file = "";
    boolean glpk = false;
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
    } else if (args.length == 6) {
      try {
        glpk = true;
        if (args[0].equals("--size") || args[0].equals("-s")) {
          size = Integer.parseInt(args[1]);
        } else if (args[2].equals("--size") || args[2].equals("-s")) {
          size = Integer.parseInt(args[3]);
        } else if (args[4].equals("--size") || args[4].equals("-s")) {
          size = Integer.parseInt(args[5]);
        } else {
          error();
        }
        if (args[0].equals("--degree") || args[0].equals("-d")) {
          degree = Integer.parseInt(args[1]);
        } else if (args[2].equals("--degree") || args[2].equals("-d")) {
          degree = Integer.parseInt(args[3]);
        } else if (args[4].equals("--degree") || args[4].equals("-d")) {
          degree = Integer.parseInt(args[5]);
        } else {
          error();
        }
        if (args[0].equals("--glpk") || args[0].equals("-g")) {
          file = args[1];
        } else if (args[2].equals("--glpk") || args[2].equals("-g")) {
          file = args[3];
        } else if (args[4].equals("--glpk") || args[4].equals("-g")) {
          file = args[5];
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
    System.out.println("Maximal Matching: " + graph.maxMatching());
    System.err.println("Time: " + graph.getTime() / 1000000.0 + " [ms]");
    if (glpk) {
      graph.glpk(file);
      System.out.println("File " + file + ".mod has been generated");
    }
  }
}
