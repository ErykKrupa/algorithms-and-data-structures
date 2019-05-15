package tree;

import java.util.Comparator;

public class BinarySearchTree extends Tree {

  public BinarySearchTree(Comparator<String> comparator) {
    super(comparator);
    root = null;
  }

  @Override
  Node getSentinel() {
    return null;
  }

  @Override
  public void insert(String element) {
    element = prepareInsert(element);
    if (element.equals("")) {
      return;
    }
    Node node = root;
    Node parent = null;
    while (node != null) {
      parent = node;
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    node = new Node(element, parent);
    if (parent == null) {
      root = node;
    } else if (c.compare(element, parent.key) < 0) {
      parent.left = node;
    } else {
      parent.right = node;
    }
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  @Override
  public void delete(String element) {
    Node node = prepareDelete(element);
    if (node == null) {
      return;
    }
    if (node.left != null && node.right != null) {
      Node successor = findSuccessor(node);
      node.key = successor.key;
      replace(successor, successor.right);
    } else if (node.left != null) {
      replace(node, node.left);
    } else if (node.right != null) {
      replace(node, node.right);
    } else {
      replace(node, null);
    }
    elementsCount--;
  }

  private void replace(Node node, Node newNode) {
    if (newNode != null) {
      newNode.parent = node.parent;
    }
    if (node.parent != null) {
      if (node == node.parent.left) {
        node.parent.left = newNode;
      } else {
        node.parent.right = newNode;
      }
    } else {
      root = newNode;
    }
  }
}
