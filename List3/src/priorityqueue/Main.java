package priorityqueue;

import java.util.Scanner;

public class Main {
  private static int operationsCount = 1;

  private static boolean checkWithParameters(String[] operation) {
    if (operation.length == 3) {
      try {
        Integer.parseInt(operation[1]);
        Integer.parseInt(operation[2]);
        return true;
      } catch (NumberFormatException ignored) {
      }
    }
    return false;
  }

  private static boolean checkWithoutParameters(String[] operation) {
    return operation.length == 1;
  }

  private static void onceAgain() {
    System.err.println("Once again: ");
    operationsCount--;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Put amount of operations: ");
    int operationsQuantity;
    while (true) {
      try {
        operationsQuantity = Integer.parseInt(scanner.nextLine());
        if (operationsQuantity > 0) {
          break;
        }
      } catch (NumberFormatException ignored) {
      }
      System.err.print("Once again: ");
    }

    PriorityQueue<Integer> queue = new PriorityQueueOnBinaryHeap<>();
    System.out.println("Put " + operationsQuantity + " operations:");
    for (; operationsCount <= operationsQuantity; operationsCount++) {
      String[] operation = scanner.nextLine().split(" ");
      if (operation.length == 0) {
        onceAgain();
        continue;
      }
      switch (operation[0]) {
        case "insert":
          if (checkWithParameters(operation)) {
            try {
              queue.insert(Integer.parseInt(operation[1]), Integer.parseInt(operation[2]));
              System.out.println(
                  operationsCount
                      + ". Insert: ("
                      + Integer.parseInt(operation[1])
                      + ", "
                      + Integer.parseInt(operation[2])
                      + ")");
            } catch (IllegalArgumentException ex) {
              onceAgain();
            }
          } else {
            onceAgain();
          }
          break;
        case "empty":
          if (checkWithoutParameters(operation)) {
            System.out.println(operationsCount + ". Empty: " + queue.empty());
          } else {
            onceAgain();
          }
          break;
        case "top":
          if (checkWithoutParameters(operation)) {
            System.out.println(operationsCount + ". Top: " + queue.top());
          } else {
            onceAgain();
          }
          break;
        case "pop":
          if (checkWithoutParameters(operation)) {
            System.out.println(operationsCount + ". Pop: " + queue.pop());
          } else {
            onceAgain();
          }
          break;
        case "priority":
          if (checkWithParameters(operation)) {
            queue.priority(Integer.parseInt(operation[1]), Integer.parseInt(operation[2]));
            System.out.println(
                operationsCount
                    + ". Priority: ("
                    + Integer.parseInt(operation[1])
                    + ", "
                    + Integer.parseInt(operation[2])
                    + ")");
          } else {
            onceAgain();
          }
          break;
        case "print":
          if (checkWithoutParameters(operation)) {
            System.out.print(operationsCount + ". Print: ");
            queue.print();
          } else {
            onceAgain();
          }
          break;
        default:
          onceAgain();
          break;
      }
    }
  }
}
