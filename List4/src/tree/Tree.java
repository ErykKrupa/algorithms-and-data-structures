package tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public abstract class Tree {
  Node root;
  Comparator<String> c;
  private long insertCount;
  private long deleteCount;
  private long searchCount;
  private long emptyCount;
  private long loadCount;
  private long inorderCount;
  long elementsCount;
  long elementsCountMax;
  long comparisonCount;
  long modificationCount;

  abstract Node getSentinel();

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

  enum Color {
    RED,
    BLACK
  }

  class Node {
    String key;
    Node left = getSentinel(), right = getSentinel();
    Node parent;
    Color color = Color.RED;

    Node(String key, Node parent) {
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

  String prepareInsert(String element) {
    insertCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    while (element.length() >= 1 && !(element.substring(0, 1).matches("[a-zA-Z]"))) {
      element = element.substring(1);
    }
    while (element.length() >= 1
        && !(element.substring(element.length() - 1).matches("[a-zA-Z]"))) {
      element = element.substring(0, element.length() - 1);
    }
    return element;
  }

  public abstract void delete(String element);

  Node prepareDelete(String element) {
    deleteCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    return find(element);
  }

  public boolean search(String element) {
    searchCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    return find(element) != getSentinel();
  }

  private Node find(String element) {
    Node node = root;
    while (node != getSentinel()) {
      if (c.compare(node.key, element) == 0) {
        return node;
      }
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    return getSentinel();
  }

  public boolean empty() {
    emptyCount++;
    return root == getSentinel();
  }

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
    while (node != getSentinel()) {
      System.out.print(node.key + " ");
      node = findSuccessor(node);
    }
    System.out.println();
  }

  private Node findMin(Node node) {
    if (node == getSentinel()) {
      return getSentinel();
    }
    while (node.left != getSentinel()) {
      node = node.left;
    }
    return node;
  }

  Node findSuccessor(Node node) {
    if (node.right != getSentinel()) {
      return findMin(node.right);
    }
    Node parent = node.parent;
    while (parent != getSentinel() && node == parent.right) {
      node = parent;
      parent = node.parent;
    }
    return parent;
  }
}
