package tree;

import java.util.Comparator;

public class BinarySearchTree extends Tree {
  public BinarySearchTree(Comparator<String> comparator) {
    super(comparator);
  }

  @Override
  public void insert(String element) {
    insertCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    if (element.equals("")) {
      return;
    }
    element = pure(element);
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
    deleteCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    delete(root, element);
  }

  private void delete(Node node, String element) {
    if (node == null) {
      return;
    }
    if (c.compare(element, node.key) < 0) {
      delete(node.left, element);
      return;
    }
    if (c.compare(element, node.key) > 0) {
      delete(node.right, element);
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

  @Override
  public boolean search(String element) {
    searchCount++;
    if (element == null) {
      throw new NullPointerException("Element cannot be null");
    }
    Node node = root;
    while (node != null) {
      if (c.compare(node.key, element) == 0) {
        return true;
      }
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
    }
    return false;
  }

  @Override
  public boolean empty() {
    emptyCount++;
    return root == null;
  }

  @Override
  Node findMin(Node node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  @Override
  Node findSuccessor(Node node) {
    if (node.right != null) {
      return findMin(node.right);
    }
    Node parent = node.parent;
    while (parent != null && node == parent.right) {
      node = parent;
      parent = node.parent;
    }
    return parent;
  }
}
