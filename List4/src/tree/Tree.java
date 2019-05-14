package tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public abstract class Tree {
  Node root = null;
  Comparator<String> c;
  long insertCount;
  long deleteCount;
  long searchCount;
  long emptyCount;
  private long loadCount;
  private long inorderCount;
  long elementsCount;
  long elementsCountMax;
  long comparisonCount;
  long modificationCount;

  public long getInsertCount() {
    return insertCount;
  }

  public long getDeleteCount() {
    return deleteCount;
  }

  public long getSearchCount() {
    return searchCount;
  }

  public long getEmptyCount() {
    return emptyCount;
  }

  public long getLoadCount() {
    return loadCount;
  }

  public long getInorderCount() {
    return inorderCount;
  }

  public long getElementsCount() {
    return elementsCount;
  }

  public long getElementsCountMax() {
    return elementsCountMax;
  }

  class Node {
    String key;
    Node left = null, right = null;
    Node parent;

    Node(String key, Node parent) {
      if (key == null) {
        throw new NullPointerException("Node's key cannot be null");
      }
      this.key = key;
      this.parent = parent;
    }
  }

  Tree(Comparator<String> comparator) {
    if (comparator == null) {
      throw new NullPointerException("Comparator cannot be null");
    }
    c = comparator;
  }

  public abstract void insert(String element);

  String pure(String element) {
    if (!(element.substring(0, 1).matches("[a-zA-Z]"))) {
      element = element.substring(1);
    }
    if (!(element.substring(element.length() - 1).matches("[a-zA-Z]"))) {
      element = element.substring(0, element.length() - 1);
    }
    return element;
  }

  public abstract void delete(String element);

  public abstract boolean search(String element);

  public abstract boolean empty();

  public void load(String file) {
    loadCount++;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        for (String word : line.split("[ \t,.;:?!\\-()\"]")) {
          insert(word);
        }
        line = reader.readLine();
      }
    } catch (FileNotFoundException ex) {
      System.err.println("File not found");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void inorder() {
    inorderCount++;
    Node node = findMin(root);
    while (node != null) {
      System.out.print(node.key + " ");
      node = findSuccessor(node);
    }
    System.out.println();
  }

  abstract Node findMin(Node node);

  abstract Node findSuccessor(Node node);
}
