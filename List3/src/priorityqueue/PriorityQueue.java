package priorityqueue;

public interface PriorityQueue<T> {
  void insert(Pair<T> pair);

  void insert(T value, Double priority);

  boolean empty();

  T top();

  T pop();

  void priority(T value, Double priority);

  void print();

  class Pair<T> {
    private T value;
    private Double priority;

    T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }

    Double getPriority() {
      return priority;
    }

    void setPriority(Double priority) {
      this.priority = priority;
    }

    Pair(T value, Double priority) {
      this.value = value;
      this.priority = priority;
    }
  }
}
