package priorityqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriorityQueueOnBinaryHeapTest {
  static final int TESTS = 1000;
  PriorityQueue<Integer> queue;
  List<PriorityQueue.Pair<Integer>> list;
  Random random;

  @BeforeEach
  void setUp() {
    queue = new PriorityQueueOnBinaryHeap<>();
    list = new LinkedList<>();
    random = new Random();
    for (int i = 0; i < TESTS; i++) {
      Integer number = random.nextInt(100);
      PriorityQueue.Pair<Integer> pair = new PriorityQueue.Pair<>(number, number);
      queue.insert(pair);
      list.add(pair);
    }
  }

  @Test
  void shouldPopsCorrectly() {
    list.sort(Comparator.comparingInt(PriorityQueue.Pair::getPriority));
    int counter = TESTS;
    while (--counter >= 0) {
      assertEquals(list.remove(0).getValue(), queue.pop());
    }
  }

  @Test
  void shouldPopsCorrectlyAfterPriority() {
    queue.priority(10, 0);
    list.sort(Comparator.comparingInt(PriorityQueue.Pair::getPriority));
    int counter = TESTS;
    while (--counter >= 0) {
      Integer pop = queue.pop();
      Integer remove = list.remove(0).getValue();
      if (!((remove == 10 || remove == 0) && (pop == 10 || pop == 0))) {
        assertEquals(remove, pop);
      }
    }
  }
}
