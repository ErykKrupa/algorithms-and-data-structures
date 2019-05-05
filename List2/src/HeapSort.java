import java.util.List;

class HeapSort extends Sort {
  @Override
  void sort(List<Integer> list, Order order, boolean silentMode) {
    prepare(list, order, silentMode);
    timeStart();
    for (int i = list.size() / 2 - 1; i >= 0; i--) {
      heapify(list, list.size(), i);
    }
    for (int i = list.size() - 1; i >= 0; i--) {
      swap(0, i);
      heapify(list, i, 0);
    }
    timeStop();
    printSummary();
  }

  private void heapify(List<Integer> list, int size, int root) {
    int extremum = root;
    int left = 2 * root + 1;
    int right = 2 * root + 2;
    extremum = getExtremum(list, size, extremum, left);
    extremum = getExtremum(list, size, extremum, right);
    if (compare(extremum, root) != 0) {
      swap(root, extremum);
      heapify(list, size, extremum);
    }
  }

  private int getExtremum(List<Integer> list, int size, int extremum, int children) {
    if (children < size && compare(list.get(extremum), list.get(children)) > 0) {
      return children;
    } else {
      return extremum;
    }
  }
}
