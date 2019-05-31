package main;

import graph.Graph;

public class Main {
  public static void main(String[] args) {
    for (int i = 1; i <= 16 ; i++) {
      long time = System.currentTimeMillis();
      Graph graph = new Graph(i);
      System.out.print(i + " : ");
      System.out.print(graph.edmondsKarp() + " : ");
      System.out.print(graph.getAugmentingPaths() + " : ");
      System.out.println(System.currentTimeMillis() - time + " [ms]");
    }
  }
}
