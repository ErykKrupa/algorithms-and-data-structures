package main;

import tree.Tree;
import tree.BinarySearchTree;
import tree.RedBlackTree;
import tree.SplayTree;

import java.util.LinkedList;
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
    if (args.length != 2) {
      System.err.println("Two argument required");
      return;
    }
    if (!(args[0].equals("--type")
        && (args[1].equals("bst") || args[1].equals("rbt") || args[1].equals("splay")))) {
      System.err.println("Type (--type) should be \"bst\", \"rbt\" or \"splay\"");
      return;
    }

    Tree tree;
    if (args[1].equals("bst")) {
      tree = new BinarySearchTree(String::compareToIgnoreCase);
    } else if (args[1].equals("rbt")) {
      tree = new RedBlackTree(String::compareToIgnoreCase);
    } else {
      tree = new SplayTree(String::compareToIgnoreCase);
    }
    LinkedList<String> tasks = new LinkedList<>();

    System.out.print("Amount of operations: ");
    operations = inputPositiveInteger();
    for (; 0 < operations; operations--) {
      String[] operation = scanner.nextLine().split(" ");
      if (operation.length == 1) {
        switch (operation[0]) {
          case "empty":
          case "inorder":
            tasks.add(operation[0]);
            break;
          default:
            onceAgain();
            break;
        }
      } else if (operation.length == 2) {
        switch (operation[0]) {
          case "insert":
          case "delete":
          case "search":
          case "load":
            tasks.add(operation[0]);
            tasks.add(operation[1]);
            break;
          default:
            onceAgain();
            break;
        }
      } else {
        onceAgain();
      }
    }
    long startTime = System.currentTimeMillis();
    int i = 0;
    while (!tasks.isEmpty()) {
      String task = tasks.remove();
      System.out.print(++i + ". " + task);
      if (task.equals("empty")) {
        System.out.println("(): " + tree.empty());
      } else if (task.equals("inorder")) {
        System.out.print("(): ");
        tree.inorder();
      } else {
        String word = tasks.remove();
        System.out.print("(" + word + ") ");
        switch (task) {
          case "insert":
            tree.insert(word);
            break;
          case "delete":
            tree.delete(word);
            break;
          case "search":
            System.out.print(": " + tree.search(word));
            break;
          case "load":
            tree.load(word);
            break;
        }
        System.out.println();
      }
    }
    System.err.println("Time: " + (System.currentTimeMillis() - startTime) + "[ms]");
    System.err.println("Insert: " + tree.getInsertCount());
    System.err.println("Delete: " + tree.getDeleteCount());
    System.err.println("Search: " + tree.getSearchCount());
    System.err.println("Empty: " + tree.getEmptyCount());
    System.err.println("Load: " + tree.getLoadCount());
    System.err.println("Inorder: " + tree.getInorderCount());
    System.err.println("Maximal size: " + tree.getElementsCountMax());
    System.err.println("Final size: " + tree.getElementsCount());
    System.err.println("Comparisons: " + tree.getComparisonCount());
    System.err.println("Modifications: " + tree.getModificationCount());
  }
}
