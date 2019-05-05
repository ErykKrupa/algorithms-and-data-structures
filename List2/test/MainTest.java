import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
  private static int TEST_SIZE = 10_000;
  private static boolean SILENT_MODE = true;
  private Random random = new Random();
  private List<Integer> list;
  private long controlSum;

  @BeforeEach
  void prepareArrayList() {
    list = new ArrayList<>();
    controlSum = 0;
    for (int j = 0; j < TEST_SIZE; j++) {
      int element = random.nextInt(200_001) - 100_000;
      list.add(element);
      controlSum += element;
    }
  }

  @AfterEach
  void tearDown() {
    long sum = 0;
    for (Integer element : list) {
      sum += element;
    }
    list = null;
    if (sum != controlSum) {
      fail();
    }
  }

  void isSorted(Order order) {
    if (order == Order.ASCEND) {
      for (int j = 0; j < TEST_SIZE - 1; j++) {
        assertTrue(list.get(j) <= list.get(j + 1));
      }
    } else if (order == Order.DESCEND) {
      for (int j = 0; j < TEST_SIZE - 1; j++) {
        assertTrue(list.get(j) >= list.get(j + 1));
      }
    } else {
      fail();
    }
  }

  @Test
  void selectionSortAscendTest() {
    new SelectionSort().sort(list, Order.ASCEND, SILENT_MODE);
    isSorted(Order.ASCEND);
  }

  @Test
  void selectionSortDescendTest() {
    new SelectionSort().sort(list, Order.DESCEND, SILENT_MODE);
    isSorted(Order.DESCEND);
  }

  @Test
  void insertionSortAscendTest() {
    new InsertionSort().sort(list, Order.ASCEND, SILENT_MODE);
    isSorted(Order.ASCEND);
  }

  @Test
  void insertionSortDescendTest() {
    new InsertionSort().sort(list, Order.DESCEND, SILENT_MODE);
    isSorted(Order.DESCEND);
  }

  @Test
  void heapSortAscendTest() {
    new HeapSort().sort(list, Order.ASCEND, SILENT_MODE);
    isSorted(Order.ASCEND);
  }

  @Test
  void heapSortDescendTest() {
    new HeapSort().sort(list, Order.DESCEND, SILENT_MODE);
    isSorted(Order.DESCEND);
  }

  @Test
  void quickSortAscendTest() {
    new QuickSort().sort(list, Order.ASCEND, SILENT_MODE);
    isSorted(Order.ASCEND);
  }

  @Test
  void quickSortDescendTest() {
    new QuickSort().sort(list, Order.DESCEND, SILENT_MODE);
    isSorted(Order.DESCEND);
  }

  @Test
  void modifiedQuickSortAscendTest() {
    new ModifiedQuickSort().sort(list, Order.ASCEND, SILENT_MODE);
    isSorted(Order.ASCEND);
  }

  @Test
  void modifiedQuickSortDescendTest() {
    new ModifiedQuickSort().sort(list, Order.DESCEND, SILENT_MODE);
    isSorted(Order.DESCEND);
  }
}
