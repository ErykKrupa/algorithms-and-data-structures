package main;

import tree.BinarySearchTree;

import java.util.Scanner;

public class Main {
  private static int operations;
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

  private static void onceAgain() {
    System.err.println("Once again: ");
    operations++;
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    if (args.length != 2) {
      System.err.println("Two argument required");
      return;
    }
    if (!(args[0].equals("--type")
        && (args[1].equals("bst") || args[1].equals("rbt") || args[1].equals("splay")))) {
      System.err.println("Type (--type) should be \"bst\", \"rbt\" or \"splay\"");
      return;
    }

    BinarySearchTree tree = null;
    if (args[1].equals("bst")) {
      tree = new BinarySearchTree(String::compareTo);
    } else if (args[1].equals("rbt")) {
      //      tree = new RedBlackTree(String::compareTo);
    } else {
      //      tree = new SplayTree(String::compareTo);
    }

    System.out.print("Amount of operations: ");
    operations = inputPositiveInteger();
    for (; 0 < operations; operations--) {
      String[] operation = scanner.nextLine().split(" ");
      if (operation.length == 1) {
        switch (operation[0]) {
          case "empty":
            System.out.println(tree.empty());
            break;
          case "inorder":
            tree.inorder();
            break;
          default:
            onceAgain();
            break;
        }
      } else if (operation.length == 2) {
        switch (operation[0]) {
          case "insert":
            tree.insert(operation[1]);
            break;
          case "delete":
            tree.delete(operation[1]);
            break;
          case "search":
            System.out.println(tree.search(operation[1]));
            break;
          case "load":
            tree.load(operation[1]);
            break;
          default:
            onceAgain();
            break;
        }
      } else {
        onceAgain();
      }
    }
    System.err.println("Time: " + (System.currentTimeMillis() - startTime) + "[ms]");
    System.err.println("Operations counts:");
    System.err.println("Insert: " + tree.getInorderCount());
    System.err.println("Delete: " + tree.getDeleteCount());
    System.err.println("Search: " + tree.getSearchCount());
    System.err.println("Empty: " + tree.getEmptyCount());
    System.err.println("Load: " + tree.getLoadCount());
    System.err.println("Inorder: " + tree.getInorderCount());
    System.err.println("Maximal size: " + tree.getElementsCountMax());
    System.err.println("Final size: " + tree.getElementsCount());
    System.err.println();
    System.err.println();
  }
}
