package implementations;

import java.util.ArrayList;
import java.util.List;

public class Sorts {

    public static List<Integer> bubbleSort(List<Integer> list) {
        boolean swap = true;
        while (swap) {
            swap = false;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) > list.get(i - 1)) {
                    swap = true;
                    int bucket = list.get(i);
                    list.set(i, list.get(i - 1));
                    list.set(i - 1, bucket);
                }
            }
        }
        return list;
    }

    public static List<Integer> selectionSort(List<Integer> list) {
        return list;
    }

    public static List<Integer> mergeSort(List<Integer> list) {
        if (list.size() == 1) {
            return list;
        }
        var left = mergeSort(new ArrayList<>(list.subList(0, list.size()/2)));
        var right = mergeSort(new ArrayList<>(list.subList(list.size()/2, list.size())));
        var merged = new ArrayList<Integer>();
        int indexLeft = 0;
        int indexRight = 0;
        while (indexLeft < left.size() || indexRight < right.size()) {
            if (indexLeft == left.size()) {
                merged.addAll(right.subList(indexRight, right.size()));
                return merged;
            }
            if (indexRight == right.size()) {
                merged.addAll(left.subList(indexLeft, left.size()));
                return merged;
            }
            if (left.get(indexLeft) < right.get(indexRight)) {
                merged.add(left.get(indexLeft));
                indexLeft++;
            }
            else {
                merged.add(right.get(indexRight));
                indexRight++;
            }
        }
        return merged;
    }

    public static List<Integer> quickSort(List<Integer> list) {
        return list;
    }

    public static List<Integer> radixSort(List<Integer> list) {
        return list;
    }

    public static int binarySearchRecursive(int[] a, int x, int low, int high) {
        if (low > high) return -1;

        int mid = (low + high) / 2;
        if (x > a[mid]) {
            return binarySearchRecursive(a, x, mid, high);
        }
        if (x < a[mid]) {
            return binarySearchRecursive(a, x, low, mid);
        }
        return mid;
    }
}
