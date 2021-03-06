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
    element = prepareToInsert(element);
    if (element.equals("")) {
      return;
    }
    Node node = root;
    Node parent = null;
    while (node != null) {
      comparisonCount++;
      parent = node;
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    modificationCount++;
    node = new Node(element, parent);
    insert(node, parent);
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  @Override
  public void delete(String element) {
    Node node = prepareToDelete(element);
    if (node == null) {
      return;
    }
    modificationCount++;
    if (node.left != null && node.right != null) {
      modificationCount++;
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
      comparisonCount++;
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
