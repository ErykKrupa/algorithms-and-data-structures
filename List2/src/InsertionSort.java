import java.util.List;

class InsertionSort extends Sort {
  @Override
  void sort(List<Integer> list, Order order, boolean silentMode) {
    sort(list, order, silentMode, 0, list.size() - 1);
  }

  void sort(
      List<Integer> list, Order order, boolean silentMode, int left, int right) {
    prepare(list, order, silentMode);
    timeStart();
    for (int i = left + 1; i <= right; i++) {
      int movingElement = list.get(i);
      for (int j = i - 1; j >= left && (compare(movingElement, list.get(j)) > 0); j--) {
        swap(j + 1, j);
      }
    }
    timeStop();
    printSummary();
  }
}
