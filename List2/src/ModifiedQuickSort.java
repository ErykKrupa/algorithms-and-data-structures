import java.util.Arrays;

class ModifiedQuickSort extends QuickSort {
  private InsertionSort insertionSort = new InsertionSort();
  @Override
  void sort(int left, int right) {
    if (ascendCompare(left, right) > 0) {
      if (right - left + 1 <= 16) {
        insertionSort.sort(list, order, silentMode, left, right);
        comparisons += insertionSort.comparisons;
        swaps += insertionSort.swaps;
      } else {
        int pivot = partition(left, right);
        sort(left, pivot);
        sort(pivot + 1, right);
      }
    }
  }

  @Override
  int getPivot(int left, int right) {
    int[] arr = new int[] {list.get(left), list.get(right), list.get((left + right) / 2)};
    Arrays.sort(arr);
    return arr[1];
  }
}
