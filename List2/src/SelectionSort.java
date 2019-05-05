import java.util.List;

class SelectionSort extends Sort {
  @Override
  void sort(List<Integer> list, Order order, boolean silentMode) {
    prepare(list, order, silentMode);
    timeStart();
    for (int j = 0; j <= list.size() - 1; j++) {
      int extremum = j;
      for (int i = j + 1; i <= list.size() - 1; i++) {
        if (compare(list.get(i), list.get(extremum)) > 0) {
          extremum = i;
        }
      }
      if (extremum != j) {
        swap(j, extremum);
      }
    }
    timeStop();
    printSummary();
  }
}
