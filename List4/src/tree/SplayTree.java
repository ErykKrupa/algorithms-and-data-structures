package tree;

import java.util.Comparator;

public class SplayTree extends Tree {
  public SplayTree(Comparator<String> comparator) {
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
      comparisonCount += 2;
      parent = node;
      if (c.compare(element, node.key) < 0) {
        comparisonCount--;
        node = node.left;
      } else if (c.compare(element, node.key) > 0) {
        node = node.right;
      } else {
        return;
      }
    }
    modificationCount++;
    node = new Node(element, parent);
    insert(node, parent);
    splay(node);
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  @Override
  Node find(String element) {
    Node node = root;
    Node parent = null;
    while (node != null) {
      comparisonCount++;
      if (c.compare(node.key, element) == 0) {
        break;
      }
      parent = node;
      comparisonCount++;
      node = (c.compare(element, node.key) < 0) ? node.left : node.right;
    }
    if (node != null) {
      splay(node);
      return node;
    } else {
      if (parent != null) {
        splay(parent);
      }
      return null;
    }
  }

  @Override
  public void delete(String element) {
    Node node = prepareToDelete(element);
    if (node == null) {
      return;
    }
    modificationCount++;
    join(node.left, node.right);
    elementsCount--;
  }

  private void splay(Node node) {
    while (node != root) {
      comparisonCount += 4;
      if (node.parent == root) {
        comparisonCount -= 2;
        zig(node);
      } else if (node.parent.parent.left == node.parent) {
        if (node.parent.left == node) {
          zigZig(node);
        } else {
          zigZag(node);
        }
      } else {
        if (node.parent.right == node) {
          zigZig(node);
        } else {
          zigZag(node);
        }
      }
    }
    comparisonCount++;
  }

  private void zig(Node node) {
    modificationCount++;
    comparisonCount++;
    if (node.parent.left == node) {
      rightRotate(node);
    } else {
      leftRotate(node);
    }
  }

  private void zigZig(Node node) {
    zig(node.parent);
    zig(node);
  }

  private void zigZag(Node node) {
    zig(node);
    zig(node);
  }

  private void leftRotate(Node node) {
    Node grandParent = node.parent.parent;
    Node parent = node.parent;
    Node child = node.left;
    rotate(node, parent, grandParent);
    node.left = parent;
    parent.parent = node;
    if (child != null) {
      child.parent = parent;
    }
    parent.right = child;
  }

  private void rightRotate(Node node) {
    Node grandParent = node.parent.parent;
    Node parent = node.parent;
    Node child = node.right;
    rotate(node, parent, grandParent);
    node.right = parent;
    parent.parent = node;
    if (child != null) {
      child.parent = parent;
    }
    parent.left = child;
  }

  private void rotate(Node node, Node parent, Node grandParent) {
    if (grandParent != null) {
      comparisonCount++;
      if (grandParent.left == parent) {
        grandParent.left = node;
      } else {
        grandParent.right = node;
      }
      node.parent = grandParent;
    } else {
      root = node;
      node.parent = null;
    }
  }

  private void join(Node left, Node right) {
    if (left == null && right == null) {
      root = null;
      return;
    }
    if (left == null) {
      root = right;
      right.parent = null;
      return;
    }
    if (right == null) {
      root = left;
      left.parent = null;
      return;
    }
    Node node = left;
    while (node.right != null) {
      node = node.right;
    }
    root = left;
    left.parent = null;
    splay(node);
    node.right = right;
    right.parent = node;
    node.parent = null;
  }
}
