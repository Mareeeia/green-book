import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProgrammingTest {

    @Test
    public void longestSequenceTest() {
        var happy = List.of(12, 1, 3, 5, 6, 13, 4, 7, 8);
        var justGo = List.of(1, 2, 3, 4, 5, 6);
        var empty = List.of(1);

        var happyArray = new int[]{12, 1, 3, 5, 6, 13, 4, 7, 8};
//        assertEquals(6, longestSeq(happy));
//        assertEquals(6, longestSeq(justGo));
//        assertEquals(1, longestSeq(empty));

        assertEquals(6, constructPrintLIS(happyArray, happy.size()));

    }

    static int constructPrintLIS(int arr[],
                                  int n)
    {
        // L[i] - The longest increasing
        // sub-sequence ends with arr[i]
        Vector<Integer> L[] = new Vector[n];
        for (int i = 0; i < L.length; i++)
            L[i] = new Vector<Integer>();

        // L[0] is equal to arr[0]
        L[0].add(arr[0]);

        // Start from index 1
        for (int i = 1; i < n; i++)
        {
            // Do for every j less than i
            for (int j = 0; j < i; j++)
            {
                //L[i] = {Max(L[j])} + arr[i]
                // where j < i and arr[j] < arr[i]
                if ((arr[i] > arr[j]) &&
                        (L[i].size() < L[j].size() + 1))
                    L[i] = (Vector<Integer>) L[j].clone();  //deep copy
            }

            // L[i] ends with arr[i]
            L[i].add(arr[i]);
        }

        // L[i] now stores increasing sub-sequence of
        // arr[0..i] that ends with arr[i]
        Vector<Integer> max = L[0];

        // LIS will be max of all increasing sub-
        // sequences of arr
        for (Vector<Integer> x : L)
            if (x.size() > max.size())
                max = x;

        // max will contain LIS
        return max.size();
    }

    public int longestSeq(List<Integer> list) {
        var longestUntil = new int[list.size()];
        var sequence = new ArrayList<Integer>();

        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (sequence.isEmpty() || list.get(j) >= sequence.get(sequence.size() - 1)) {
                    sequence.add(list.get(j));
                }
            }
            if (sequence.size() > longestUntil[i]) {
                longestUntil[i] = sequence.size();
            }
            sequence = new ArrayList<Integer>();
        }

        int max = Integer.MIN_VALUE;

        for (int j : longestUntil) {
            if (j > max) max = j;
        }
        return max;
    }
}
