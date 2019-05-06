import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityQueueOnBinaryHeap<T> implements PriorityQueue<T> {
  private List<Pair<T>> list = new ArrayList<>();
  private Comparator<Integer> comparator;

  public PriorityQueueOnBinaryHeap() {
    comparator = Comparator.comparingInt(a -> a);
  }

  public PriorityQueueOnBinaryHeap(Comparator<Integer> comparator) {
    this.comparator = comparator;
  }

  private void swap(int firstIndex, int secondIndex) {
    Pair<T> pair = list.get(firstIndex);
    list.set(firstIndex, list.get(secondIndex));
    list.set(secondIndex, pair);
  }

  private int compare(int firstIndex, int secondIndex) {
    return comparator.compare(
        list.get(firstIndex).getPriority(), list.get(secondIndex).getPriority());
  }

  private void heapifyDown(int parentIndex) {
    int smallestIndex = parentIndex;
    int childIndex = (parentIndex * 2) + 1;
    if (childIndex < list.size() && compare(childIndex, smallestIndex) < 0) {
      smallestIndex = childIndex;
    }
    childIndex++;
    if (childIndex < list.size() && compare(childIndex, smallestIndex) < 0) {
      smallestIndex = childIndex;
    }
    if (smallestIndex != parentIndex) {
      swap(smallestIndex, parentIndex);
      heapifyDown(smallestIndex);
    }
  }

  private void heapifyUp(int childIndex) {
    int parentIndex = (childIndex - 1) / 2;
    if (childIndex > 0 && compare(childIndex, parentIndex) < 0) {
      swap(childIndex, parentIndex);
      heapifyUp(parentIndex);
    }
  }

  @Override
  public void insert(Pair<T> pair) {
    list.add(pair);
    int childIndex = list.size() - 1;
    int parentIndex = (childIndex - 1) / 2;
    while (childIndex > 0 && compare(childIndex, parentIndex) < 0) {
      swap(childIndex, parentIndex);
      childIndex = parentIndex;
      parentIndex = (childIndex - 1) / 2;
    }
  }

  @Override
  public void insert(T value, Integer priority) {
    insert(new Pair<>(value, priority));
  }

  @Override
  public boolean empty() {
    return list.size() == 0;
  }

  @Override
  public T top() {
    return empty() ? null : list.get(0).getValue();
  }

  @Override
  public T pop() {
    T value = top();
    if (value != null) {
      list.set(0, list.remove(list.size() - 1));
      heapifyDown(0);
    }
    return value;
  }

  @Override
  public void priority(T value, Integer priority) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getValue() == value
          && comparator.compare(priority, list.get(i).getPriority()) < 0) {
        list.get(i).setPriority(priority);
        heapifyUp(i);
      }
    }
  }

  @Override
  public void print() {
    for (Pair<T> pair : list) {
      System.out.print("(" + pair.getValue() + ", " + pair.getPriority() + ") ");
    }
    System.out.println();
  }
}
