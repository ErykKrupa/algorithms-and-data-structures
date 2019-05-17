package tree;

import java.util.Comparator;

public class RedBlackTree extends Tree {
  private final Node sentinel = new Node(null, null);

  public RedBlackTree(Comparator<String> comparator) {
    super(comparator);
    modificationCount += 6;
    sentinel.left = sentinel;
    sentinel.right = sentinel;
    sentinel.parent = sentinel;
    sentinel.color = Color.BLACK;
    root = sentinel;
  }

  @Override
  Node getSentinel() {
    return sentinel;
  }

  private void leftRotate(Node node) {
    modificationCount += 5;
    Node nextNode = node.right;
    node.right = nextNode.left;
    nextNode.left.parent = node;
    rotate(node, nextNode);
    nextNode.left = node;
    node.parent = nextNode;
  }

  private void rightRotate(Node node) {
    modificationCount += 5;
    Node nextNode = node.left;
    node.left = nextNode.right;
    nextNode.right.parent = node;
    rotate(node, nextNode);
    nextNode.right = node;
    node.parent = nextNode;
  }

  private void rotate(Node node, Node nextNode) {
    modificationCount += 2;
    nextNode.parent = node.parent;
    comparisonCount += 2;
    if (node.parent == sentinel) {
      comparisonCount--;
      root = nextNode;
    } else if (node == node.parent.left) {
      node.parent.left = nextNode;
    } else {
      node.parent.right = nextNode;
    }
  }

  @Override
  public void insert(String element) {
    element = prepareToInsert(element);
    if (element.equals("")) {
      return;
    }
    modificationCount += 2;
    Node parent = sentinel;
    Node node = root;
    while (node != sentinel) {
      parent = node;
      node = ((c.compare(element, node.key) < 0) ? node.left : node.right);
      comparisonCount += 2;
      modificationCount += 2;
    }
    comparisonCount += 3;
    modificationCount += 2;
    Node newNode = new Node(element, parent);
    if (parent == sentinel) {
      comparisonCount--;
      root = newNode;
    } else if (c.compare(newNode.key, parent.key) < 0) {
      parent.left = newNode;
    } else {
      parent.right = newNode;
    }
    insertFixUp(newNode);
    elementsCount++;
    elementsCountMax = Math.max(elementsCountMax, elementsCount);
  }

  private void insertFixUp(Node node) {
    Node nextNode;
    while (node.parent != sentinel && node.parent.color == Color.RED) {
      comparisonCount += 3;
      modificationCount += 2;
      if (node.parent == node.parent.parent.left) {
        nextNode = node.parent.parent.right;
        if (nextNode.color == Color.RED) {
          node = setColors(node, nextNode);
        } else {
          comparisonCount++;
          if (node == node.parent.right) {
            modificationCount++;
            node = node.parent;
            leftRotate(node);
          }
          modificationCount++;
          node.parent.color = Color.BLACK;
          node.parent.parent.color = Color.RED;
          rightRotate(node.parent.parent);
        }
      } else {
        nextNode = node.parent.parent.left;
        if (nextNode.color == Color.RED) {
          node = setColors(node, nextNode);
        } else {
          comparisonCount++;
          if (node == node.parent.left) {
            modificationCount++;
            node = node.parent;
            rightRotate(node);
          }
          modificationCount++;
          node.parent.color = Color.BLACK;
          node.parent.parent.color = Color.RED;
          leftRotate(node.parent.parent);
        }
      }
    }
    comparisonCount += 2;
    root.color = Color.BLACK;
  }

  private Node setColors(Node node, Node nextNode) {
    modificationCount += 4;
    node.parent.color = Color.BLACK;
    nextNode.color = Color.BLACK;
    node.parent.parent.color = Color.RED;
    node = node.parent.parent;
    return node;
  }

  @Override
  public void delete(String element) {
    modificationCount++;
    Node newNode = prepareToDelete(element);
    comparisonCount++;
    if (newNode == getSentinel()) {
      return;
    }
    comparisonCount += 3;
    modificationCount += 2;
    Node node =
        (newNode.left == sentinel || newNode.right == sentinel) ? newNode : findSuccessor(newNode);
    Node nextNode = node.left != sentinel ? node.left : node.right;
    rotate(node, nextNode);
    comparisonCount += 2;
    if (node != newNode) {
      modificationCount += 6;
      node.left = newNode.left;
      node.right = newNode.right;
      node.parent = newNode.parent;
      node.left.parent = node;
      node.right.parent = node;
      comparisonCount += 2;
      if (newNode == root) {
        comparisonCount--;
        root = node;
      } else if (newNode == newNode.parent.left) {
        newNode.parent.left = node;
      } else {
        newNode.parent.right = node;
      }
    }
    if (node.color == Color.BLACK) {
      deleteFixUp(nextNode);
    }
    elementsCount--;
  }

  private void deleteFixUp(Node node) {
    Node nextNode;
    while (node != root && node.color == Color.BLACK) {
      comparisonCount += 6;
      if (node == node.parent.left) {
        modificationCount++;
        nextNode = node.parent.right;
        if (nextNode.color == Color.RED) {
          modificationCount += 3;
          nextNode.color = Color.BLACK;
          node.parent.color = Color.RED;
          leftRotate(node.parent);
          nextNode = node.parent.right;
        }
        if (nextNode.left.color == Color.BLACK && nextNode.right.color == Color.BLACK) {
          modificationCount += 2;
          nextNode.color = Color.RED;
          node = node.parent;
        } else {
          comparisonCount++;
          if (nextNode.right.color == Color.BLACK) {
            modificationCount += 3;
            nextNode.left.color = Color.BLACK;
            nextNode.color = Color.RED;
            rightRotate(nextNode);
            nextNode = node.parent.right;
          }
          modificationCount += 4;
          nextNode.color = node.parent.color;
          node.parent.color = Color.BLACK;
          nextNode.right.color = Color.BLACK;
          leftRotate(node.parent);
          node = root;
        }
      } else {
        modificationCount++;
        nextNode = node.parent.left;
        if (nextNode.color == Color.RED) {
          modificationCount += 3;
          nextNode.color = Color.BLACK;
          node.parent.color = Color.RED;
          rightRotate(node.parent);
          nextNode = node.parent.left;
        }
        if (nextNode.right.color == Color.BLACK && nextNode.left.color == Color.BLACK) {
          modificationCount += 2;
          nextNode.color = Color.RED;
          node = node.parent;
        } else {
          comparisonCount++;
          if (nextNode.left.color == Color.BLACK) {
            modificationCount += 3;
            nextNode.right.color = Color.BLACK;
            nextNode.color = Color.RED;
            leftRotate(nextNode);
            nextNode = node.parent.left;
          }
          modificationCount += 4;
          nextNode.color = node.parent.color;
          node.parent.color = Color.BLACK;
          nextNode.left.color = Color.BLACK;
          rightRotate(node.parent);
          node = root;
        }
      }
    }
    comparisonCount++;
    modificationCount++;
    node.color = Color.BLACK;
  }
}
