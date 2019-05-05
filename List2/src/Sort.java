import java.util.Comparator;
import java.util.List;

abstract class Sort {
  List<Integer> list = null;
  Order order;
  boolean silentMode = false;
  int comparisons;
  int swaps;
  private Comparator<Integer> comparator;
  private double timeStart;
  private double timeStop;
  private double time;

  double getTime() {
    return time;
  }

  abstract void sort(List<Integer> list, Order order, boolean silentMode);

  private static Comparator<Integer> ascend =
      (n1, n2) -> {
        if (n1 < n2) {
          return 1;
        } else if (n1.equals(n2)) {
          return 0;
        } else {
          return -1;
        }
      };

  private static Comparator<Integer> descend =
      (n1, n2) -> {
        if (n1 > n2) {
          return 1;
        } else if (n1.equals(n2)) {
          return 0;
        } else {
          return -1;
        }
      };

  void prepare(List<Integer> list, Order order, boolean silentMode) {
    this.list = list;
    this.order = order;
    if (order == Order.ASCEND) {
      this.comparator = ascend;
    } else if (order == Order.DESCEND) {
      this.comparator = descend;
    } else if (order == null) {
      throw new NullPointerException("Order cannot be null.");
    } else {
      throw new RuntimeException("Unexpected exception.");
    }
    this.silentMode = silentMode;
    comparisons = 0;
    swaps = 0;
  }

  private void compareCount(int left, int right) {
    comparisons++;
    if (!silentMode) {
      System.err.println("Comparison: " + left + " " + right);
    }
  }

  int compare(int left, int right) {
    compareCount(left, right);
    return comparator.compare(left, right);
  }

  int ascendCompare(int left, int right) {
    compareCount(left, right);
    return ascend.compare(left, right);
  }

  int descendCompare(int left, int right) {
    compareCount(left, right);
    return descend.compare(left, right);
  }

  void swap(int leftPointer, int rightPointer) {
    swaps++;
    if (!silentMode) {
      System.err.println("Swap: " + list.get(leftPointer) + " " + list.get(rightPointer));
    }
    int tmp = list.get(leftPointer);
    list.set(leftPointer, list.get(rightPointer));
    list.set(rightPointer, tmp);
  }

  void timeStart() {
    timeStart = System.nanoTime();
  }

  double timeStop() {
    timeStop = System.nanoTime();
    time = (timeStop - timeStart) / 1_000_000.0;
    return time;
  }

  void printSummary() {
    if (!silentMode) {
      System.err.println("Comparisons: " + comparisons);
      System.err.println("Swaps: " + swaps);
      System.err.println("Time: " + time + " s");
    }
  }
}
