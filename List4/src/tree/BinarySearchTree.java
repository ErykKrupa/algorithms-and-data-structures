package tree;

import java.util.Comparator;

public class BinarySearchTree extends Tree {

  public BinarySearchTree(Comparator<String> comparator) {
    super(comparator);
    modificationCount++;
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
    modificationCount += 2;
    Node node = root;
    Node parent = null;
    while (node != null) {
      comparisonCount += 2;
      modificationCount += 2;
      parent = node;
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    comparisonCount++;
    modificationCount++;
    node = new Node(element, parent);
    insert(node, parent);
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  @Override
  public void delete(String element) {
    modificationCount++;
    Node node = prepareToDelete(element);
    comparisonCount++;
    if (node == null) {
      return;
    }
    comparisonCount += 4;
    if (node.left != null && node.right != null) {
      comparisonCount -= 2;
      modificationCount += 2;
      Node successor = findSuccessor(node);
      node.key = successor.key;
      replace(successor, successor.right);
    } else if (node.left != null) {
      comparisonCount--;
      replace(node, node.left);
    } else if (node.right != null) {
      replace(node, node.right);
    } else {
      replace(node, null);
    }
    elementsCount--;
  }

  private void replace(Node node, Node newNode) {
    comparisonCount++;
    if (newNode != null) {
      modificationCount++;
      newNode.parent = node.parent;
    }
    comparisonCount++;
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
    modificationCount++;
  }
}
