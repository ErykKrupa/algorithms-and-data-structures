package tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

  public long getComparisonCount() {
    return comparisonCount;
  }

  public long getModificationCount() {
    return modificationCount;
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
      modificationCount += 5;
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

  void insert(Node node, Node parent) {
    comparisonCount += 2;
    modificationCount++;
    if (parent == null) {
      comparisonCount--;
      root = node;
    } else if (c.compare(node.key, parent.key) < 0) {
      parent.left = node;
    } else {
      parent.right = node;
    }
  }

  String prepareToInsert(String element) {
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

  Node prepareToDelete(String element) {
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

  Node find(String element) {
    modificationCount++;
    Node node = root;
    while (node != getSentinel()) {
      comparisonCount += 2;
      if (c.compare(node.key, element) == 0) {
        return node;
      }
      comparisonCount++;
      modificationCount++;
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    comparisonCount++;
    return getSentinel();
  }

  public boolean empty() {
    emptyCount++;
    comparisonCount++;
    return root == getSentinel();
  }

  public void load(String file) {
    loadCount++;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      //      List<String> list = new LinkedList<>();
      String line = reader.readLine();
      while (line != null) {
        //        list.addAll(Arrays.asList(line.split("[ \t,.;:?!\\-()\"]")));
        for (String word : line.split("[^\\w']+")) {
          insert(word);
        }
        line = reader.readLine();
      }
      //      Collections.shuffle(list);
      //      long startTime = System.currentTimeMillis();

      //      System.out.println(System.currentTimeMillis() - startTime + "[ms]");
    } catch (FileNotFoundException ex) {
      System.err.print("File not found");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void inorder() {
    inorderCount++;
    modificationCount++;
    Node node = findMin(root);
    while (node != getSentinel()) {
      comparisonCount++;
      modificationCount++;
      System.out.print(node.key + " ");
      node = findSuccessor(node);
    }
    comparisonCount++;
    System.out.println();
  }

  private Node findMin(Node node) {
    comparisonCount++;
    if (node == getSentinel()) {
      return getSentinel();
    }
    while (node.left != getSentinel()) {
      comparisonCount++;
      modificationCount++;
      node = node.left;
    }
    comparisonCount++;
    return node;
  }

  Node findSuccessor(Node node) {
    comparisonCount++;
    if (node.right != getSentinel()) {
      return findMin(node.right);
    }
    modificationCount++;
    Node parent = node.parent;
    while (parent != getSentinel() && node == parent.right) {
      comparisonCount += 2;
      modificationCount += 2;
      node = parent;
      parent = node.parent;
    }
    comparisonCount += 2;
    return parent;
  }
}
