import priorityqueue.*;

import java.util.Random;

public class Main {
  public static void main(String[] args) {
    Random random = new Random();
    PriorityQueue<Integer> queue = new PriorityQueueOnBinaryHeap<>();
    System.out.println(queue.empty());
    for (int i = 0; i < 20; i++) {
      PriorityQueue.Pair<Integer> pair =
          new PriorityQueue.Pair<>(random.nextInt(3), random.nextInt(50));
      queue.insert(pair);
      System.out.print("(" + pair.getValue() + ", " + pair.getPriority() + ") ");
    }
    System.out.println();
    queue.print();
    System.out.println(queue.empty());
    System.out.println(queue.top());
    System.out.println(queue.pop());
    queue.print();
	  System.out.println("--------------");
	  queue.priority(0, 5);
	  queue.print();
    for (int i = 0; i < 9; i++) {
      System.out.println(queue.pop());
    }
    queue.print();
    System.out.println("--------------");
    queue.priority(1, 0);
    queue.print();
  }
}
