import implementations.Sorts;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ArraySorter.sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {

    @Test
    public void testMergeSort() {
        var list = List.of(11, 10, 6, 7, 3, 12, 5);
        var evenList = List.of(2, 8, 7, 6);

        var sorted = Sorts.mergeSort(list);
        assertEquals(sorted, List.of(3, 5, 6, 7, 10, 11, 12));
        assertEquals(List.of(2, 6, 7, 8), Sorts.mergeSort(evenList));
    }

    @Test
    public void testBinarySearch() {
        int[] sorted = new int[] {3, 5, 6, 7, 10, 11, 12};
        assertEquals(6, Sorts.binarySearchRecursive(sorted, 12, 0, 7));
        assertEquals(2, Sorts.binarySearchRecursive(sorted, 6, 0, 7));
        assertEquals(3, Sorts.binarySearchRecursive(sorted, 7, 0, 7));
    }

    @Test
    public void insertArrayTest() {
        int[] smallArray = new int[]{2, 4, 6, 8};
        int[] bigArray = new int[7];
        bigArray[0] = 1;
        bigArray[1] = 3;
        bigArray[2] = 5;
        bigArray[3] = 7;

        assertEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, insertArray(bigArray, smallArray));
    }

    @Test
    public void groupAnagramsTest() {
        List<String> normalList = List.of("act", "bro", "cat", "cta", "orb", "atk");
        List<String> singles = List.of("cat", "orb", "atk");
        List<String> empty = List.of();

        assertTrue(groupAnagrams(empty).equals(List.of()));
        assertTrue(groupAnagrams(singles).equals(singles));
        assertTrue(groupAnagrams(normalList).equals(List.of("act", "cat", "cta", "bro", "orb", "atk")));
    }

    @Test
    public void checkDuplicatesTest() {
        var n = 32000;
        var init = new int[n];

        for (int i = 1; i < n; i++) {
            init[i - 1] = i;
        }

        init[233] = 500;
        init[31000] = 700;
        init[3333] = 500;

        assertEquals(List.of(500, 500, 700), checkDuplicates(init));
    }

//    @Test
//    public void binarySearchGridTest() {
//        int[][] happy = new int[][] {
//                {11, 12, 13, 14},
//                {21, 22, 23, 24},
//                {31, 32, 33, 34},
//                {41, 42, 43, 44},
//                {51, 52, 53, 54},
//        };
//
//        assertEquals(new Coordinate(3, 1), findIndex(42));
//    }

//    private Coordinate partitionAndSearch(int[][] matrix, Coordinate origin, Coordinate destination) {
//        Coordinate topLeft =
//    }

//    private Coordinate findElement(int[][] matrix, int value, Coordinate min, Coordinate max) {
//        Coordinate mid = new Coordinate(0,0);
//        mid.setToAverage(min, max);
//        if (matrix[mid.row][mid.column] == value) {
//            return mid;
//        }
//
//
//
//    }

    @Test
    public void rankNodeTest() {
        var stream  = List.of(2, 5, 8, 11, 3, 17, 9);
        var streamHappy  = List.of(5, 1, 4, 4, 5, 9, 7, 13, 3);

        var node = new RankNode(2);

        for (int i = 1; i < stream.size(); i++) {
            node.insert(stream.get(i));
        }

        assertEquals(3, node.getRank(8));
    }

    @Data
    public class RankNode {
        private int lessThan;
        private int value;
        private RankNode left;
        private RankNode right;

        public RankNode(int value) {
            this.value = value;
        }

        public void insert(int value) {
            if (value < this.value) {
                this.lessThan++;
                if (this.left == null) {
                    this.left = new RankNode(value);
                }
                else {
                    this.left.insert(value);
                }
            }
            else {
                if (this.right == null) {
                    this.right = new RankNode(value);
                }
                else {
                    this.right.insert(value);
                }
            }
        }

        public int getRank(int value) {
            if (this.value == value) {
                return this.getLessThan();
            }
            else if (value < this.value) {
                if (this.left == null) return -1;
                return this.left.getRank(value);
            }
            else {
                int right_rank = this.right == null ? -1 : this.right.getRank(value);
                if (right_rank == -1) return -1;
                else return lessThan + 1 + right_rank;
            }
        }
    }



    @Data
    private class Coordinate {
        private int row;
        private int column;

        public Coordinate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public boolean isBefore(Coordinate coord) {
            return (this.row < coord.getRow() && this.column < coord.getColumn());
        }

        public void setToAverage(Coordinate min, Coordinate max) {
            this.row = (min.getRow() + max.getRow()) / 2;
            this.column = (min.getColumn() + max.getColumn()) / 2;
        }

        public Coordinate clone() {
            return new Coordinate(this.row, this.column);
        }
    }




    private List<Integer> checkDuplicates(int[] arr) {
        BitSet bitSet = new BitSet(arr.length);
        List<Integer> result = new ArrayList<>();
        for (int j : arr) {
            if (bitSet.get(j)) {
                result.add(j);
            } else {
                bitSet.set(j);
            }
        }
        return result;
    }

    private class BitSet {
        private int[] array;
        public BitSet(int size) {
            array = new int[size >> 5];
        }

        boolean get(int value) {
            int index = value >> 5;
            int location = value & ((1 << 5) - 1);
            return (array[index] & (1 << location)) != 0;
        }

        void set(int value) {
            int index = value >> 5;
            int location = value & ((1 << 5) - 1);
            array[index] |= 1 << location;
        }
    }

    private List<String> groupAnagrams(List<String> ungrouped) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str: ungrouped) {
            var key = String.valueOf(sort(str.toCharArray()));
            if (map.containsKey(key)) {
                map.get(key).add(str);
            }
            else {
                map.put(key, new ArrayList<>(List.of(str)));
            }
        }
        List<String> result = new ArrayList<>();
        for (String key: map.keySet()) {
            result.addAll(map.get(key));
        }
        return result;
    }


    public int[] insertArray(int[] bigArray, int[] smallArray) {
        int sizeBig = bigArray.length;
        int sizeSmall = smallArray.length;
        int bufferStarts = sizeBig - sizeSmall;
        int bigIter = 0;
        for (int i = 0; i < sizeSmall; i++) {
            while (bigArray[bufferStarts - bigIter - 1] > smallArray[sizeSmall - i - 1]) {
                swap(bigArray, bufferStarts - bigIter - 1, sizeBig - bigIter - i - 1);
                bigIter++;
            }
            bigArray[sizeBig - bigIter - i - 1] = smallArray[sizeSmall - i - 1];
        }
        return bigArray;
    }

    private void swap(int[] arr, int i, int j) {
        int bucket = arr[i];
        arr[i] = arr[j];
        arr[j] = bucket;
    }
}
