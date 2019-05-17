package tree;

import java.util.Comparator;

public class SplayTree extends Tree {
  public SplayTree(Comparator<String> comparator) {
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
    Node node = root;
    Node parent = null;
    modificationCount += 2;
    while (node != null) {
      comparisonCount += 3;
      modificationCount += 2;
      parent = node;
      if (c.compare(element, node.key) < 0) {
        comparisonCount--;
        node = node.left;
      } else if (c.compare(element, node.key) > 0) {
        node = node.right;
      } else {
        modificationCount--;
        return;
      }
    }
    comparisonCount++;
    modificationCount++;
    node = new Node(element, parent);
    insert(node, parent);
    splay(node);
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  @Override
  Node find(String element) {
    modificationCount += 2;
    Node node = root;
    Node parent = null;
    while (node != null) {
      comparisonCount += 2;
      if (c.compare(node.key, element) == 0) {
        break;
      }
      modificationCount += 2;
      parent = node;
      comparisonCount++;
      node = (c.compare(element, node.key) < 0) ? node.left : node.right;
    }
    comparisonCount += 2;
    if (node != null) {
      splay(node);
      return node;
    } else {
      comparisonCount++;
      if (parent != null) {
        splay(parent);
      }
      return null;
    }
  }

  @Override
  public void delete(String element) {
    modificationCount++;
    Node node = prepareToDelete(element);
    comparisonCount++;
    if (node == null) {
      return;
    }
    join(node.left, node.right);
    elementsCount--;
  }

  private void splay(Node node) {
    while (node != root) {
      modificationCount += 3;
      if (node.parent == root) {
        modificationCount -= 2;
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
    modificationCount += 6;
    Node grandParent = node.parent.parent;
    Node parent = node.parent;
    Node child = node.left;
    rotate(node, parent, grandParent);
    node.left = parent;
    parent.parent = node;
    comparisonCount++;
    if (child != null) {
      modificationCount++;
      child.parent = parent;
    }
    parent.right = child;
  }

  private void rightRotate(Node node) {
    modificationCount += 6;
    Node grandParent = node.parent.parent;
    Node parent = node.parent;
    Node child = node.right;
    rotate(node, parent, grandParent);
    node.right = parent;
    parent.parent = node;
    comparisonCount++;
    if (child != null) {
      modificationCount++;
      child.parent = parent;
    }
    parent.left = child;
  }

  private void rotate(Node node, Node parent, Node grandParent) {
    comparisonCount++;
    if (grandParent != null) {
      comparisonCount++;
      modificationCount++;
      if (grandParent.left == parent) {
        grandParent.left = node;
      } else {
        grandParent.right = node;
      }
      node.parent = grandParent;
    } else {
      modificationCount += 2;
      root = node;
      node.parent = null;
    }
  }

  private void join(Node left, Node right) {
    comparisonCount += 2;
    if (left == null && right == null) {
      modificationCount++;
      root = null;
      return;
    }
    comparisonCount++;
    modificationCount += 2;
    if (left == null) {
      root = right;
      right.parent = null;
      return;
    }
    comparisonCount++;
    if (right == null) {
      root = left;
      left.parent = null;
      return;
    }
    modificationCount += 4;
    Node node = left;
    while (node.right != null) {
      comparisonCount++;
      modificationCount++;
      node = node.right;
    }
    comparisonCount++;
    root = left;
    left.parent = null;
    splay(node);
    node.right = right;
    right.parent = node;
    node.parent = null;
  }
}
