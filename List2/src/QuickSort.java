import java.util.List;

class QuickSort extends Sort {
  @Override
  void sort(List<Integer> list, Order order, boolean silentMode) {
    prepare(list, order, silentMode);
    timeStart();
    sort(0, list.size() - 1);
    timeStop();
    printSummary();
  }

  void sort(int left, int right) {
    if (ascendCompare(left, right) > 0) {
      int pivot = partition(left, right);
      sort(left, pivot);
      sort(pivot + 1, right);
    }
  }

  int partition(int left, int right) {
    int pivot = getPivot(left, right);
    int p = left - 1;
    int q = right + 1;
    while (true) {
      do {
        p++;
      } while (compare(list.get(p), pivot) > 0);
      do {
        q--;
      } while (compare(pivot, list.get(q)) > 0);
      if (p >= q) {
        return q;
      }
      swap(p, q);
    }
  }

  int getPivot(int left, int right) {
    return list.get((left + right) / 2);
  }
}
