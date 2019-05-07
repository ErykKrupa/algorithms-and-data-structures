package priorityqueue;

public interface PriorityQueue<T> {
  void insert(Pair<T> pair);

  void insert(T value, Integer priority);

  boolean empty();

  T top();

  T pop();

  void priority(T value, Integer priority);

  void print();

  class Pair<T> {
    private T value;
    private Integer priority;

    public T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }

    public Integer getPriority() {
      return priority;
    }

    public void setPriority(Integer priority) {
      this.priority = priority;
    }

    public Pair(T value, Integer priority) {
      this.value = value;
      this.priority = priority;
    }
  }
}
