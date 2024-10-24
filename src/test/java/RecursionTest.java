import implementations.MyStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RecursionTest {

    private Set<List<Integer>> state = new HashSet<>();

    private Map<Integer, Integer> childStepsMem = new HashMap<>();

    @Test
    public void runningChildTest() {
        assertEquals(1, childRun(1));
        assertEquals(2, childRun(2));
        assertEquals(4, childRun(3));
    }

    @Test
    public void robotGridTest() {
        var pathList = new ArrayList<Point>();
        var grid = new int[][] {
            {0, 0, -1},
            {-1, 0, -1},
            {-1, 0, 0}
        };
        var path = getPath(2, 2, pathList, grid);
        assertTrue(path);
    }

    @Test
    public void magicIndexTest() {
        var happyCase = List.of(-1, -2, 0, 2, 4, 5);
        var none = List.of(1, 2, 3, 4, 5);
        List<Integer> empty = List.of();
        var dup = List.of(-10, -5, 2, 2, 2, 3, 4, 6, 9, 12, 13);

        assertEquals(4, findMagic(happyCase, 2));
        assertEquals(-1, findMagic(none, 2));
        assertEquals(-1, findMagic(empty, 0));
        assertEquals(2, findMagic(dup, 5));
    }

    public int findMagic(List<Integer> list, Integer middle) {
        if (list.isEmpty()) {
            return -1;
        }
        if (middle >= list.size()) {
            return -1;
        }
        if (middle.equals(list.get(middle))) {
            return middle;
        }
        if (middle == 0) {
            return -1;
        }
        if (middle < list.get(middle)) {
            return findMagic(list, Math.min(middle/2, list.get(middle/2)));
        }
        if (middle > list.get(middle)) {
            return findMagic(list, Math.max(list.get(middle/2), middle/2 + list.size()/2));
        }
        return -1;
    }

    @Test
    public void allSubsetsTest() {
        var happy  = List.of(1, 2, 3);
        List<Integer> empty = List.of();
        var one = List.of(1);

        assertEquals(8, getSubsets(happy, 0).size());
        assertEquals(1, getSubsets(empty, 0).size());
        assertEquals(2, getSubsets(one, 0).size());
    }

    @Test
    public void multiplicationTest() {
        assertEquals(4*5, multiply(4, 5));
        assertEquals(42*51, multiply(42, 51));
        assertEquals(0, multiply(0, 5));
        assertEquals(13*23, multiply(13, 23));
    }

//    @Test
//    public void hanoi() {
//        var happyStack = new MyStack<>(List.of(5, 4, 3, 2, 1));
//        var buffer = new MyStack<Integer>(List.of());
//        var answer = new MyStack<Integer>(List.of());
//
//        hanoi(happyStack, buffer, answer);
//
//        assertTrue(answer.equals(new MyStack<>(List.of(5, 4, 3, 2, 1))));
//    }

    @Test
    public void permutationsTest() {
        var happy = "abc";

        assertTrue(Set.of("abc", "cba", "bac", "cab", "bca", "acb").containsAll(permutations("abc")));
        assertTrue(permutations("abc").containsAll(Set.of("abc", "cba", "bac", "cab", "bca", "acb")));
    }

    @Test
    public void bracketsTest() {
        List<String> combinations = new ArrayList<>();

        int half = 1;
        char[] str = new char[2 * half];
        MyStack<Character> stack = new MyStack<>(List.of());
        makeBrackets(combinations, half, half, str, 0, stack);
        assertFalse(combinations.isEmpty());
    }

    @Test
    public void paintToolTest() {

    }

    public void paintTool(int[][] board, int row, int column, int oldColor, int newColor) {
        if (row < 0 || column < 0) {
            return;
        }

        if (board[row][column] == oldColor) {
            board[row][column] = newColor;
            paintTool(board, row - 1, column, oldColor, newColor);
            paintTool(board, row, column - 1, oldColor, newColor);
            paintTool(board, row + 1, column, oldColor, newColor);
            paintTool(board, row, column + 1, oldColor, newColor);
        }
        return;
    }

    @Test
    public void makeChangeTest() {

        int ways = makeChange(15);
        assertTrue(ways == 2);
    }

    @Test
    public void stackOfBoxesTest() {

    }

    public int getBoxFoundations(int w, int d) {
        return w * d;
    }

    int makeChange(int amount, int[] denoms, int index) {
        if (index >= denoms.length - 1) return 1; // if we reach 1 then we good
        int denomAmount = denoms[index];
        int ways = 0;
        for (int i = 0; i * denomAmount <= amount; i++) {
            int amountRemaining = amount - i * denomAmount;
            ways += makeChange(amountRemaining, denoms, index + 1); // trick is to sort the amounts
        }
        return ways;
    }

    int makeChange(int n) {
        int[] denoms = {25, 15, 5, 1};
        return makeChange(n, denoms, 0);
    }

    public void makeBrackets(List<String> combinations, int left, int right, char[] str, int index, MyStack<Character> stack) {
        if (left < 0 || right < 0) {
            return;
        }

        if (left == 0 && right == 0) {
            combinations.add(String.valueOf(str));
        }
        else {
            str[index] = '(';
            makeBrackets(combinations, left - 1, right, str, index + 1, stack);
            str[index] = ')';
            makeBrackets(combinations, left, right - 1, str, index + 1, stack);
        }
    }



    public Set<String> permutations(String str) {
        if (str == null) {
            return null;
        }

        Set<String> permutations = new HashSet<>();

        if (str.length() == 0) {
            permutations.add("");
            return permutations;
        }

        char first = str.charAt(0);
        String remainder = str.substring(1);
        Set<String> words = permutations(remainder);
        for (String word: words) {
            for (int j = 0; j <= word.length(); j++) {
                    String s = insertCharAt(word, first, j);
                    permutations.add(s);
                }
            }
        return permutations;
    }

    String insertCharAt(String word, char c, int i) {
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }



    public int multiply(int smaller, int bigger) {
        if (smaller == 0) {
            return 0;
        }

        if (smaller % 2 == 0) {
            return 2 * multiply(smaller >> 1, bigger);
        }
        else {
            return bigger + 2 * multiply(smaller >> 1, bigger);
        }
    }

    // assume unique elements
    public ArrayList<ArrayList<Integer>> getSubsets(List<Integer> set, int index) {
        ArrayList<ArrayList<Integer>> allSubsets;
        if (index == set.size()) {
            allSubsets = new ArrayList<ArrayList<Integer>>();
            allSubsets.add(new ArrayList<Integer>());
        }
        else {
            System.out.println(index);
            allSubsets = getSubsets(set, index + 1);
            int item = set.get(index);
            ArrayList<ArrayList<Integer>> moresubsets = new ArrayList<ArrayList<Integer>>();
            for (var incompleteSet: allSubsets) {
                var newSet = new ArrayList<>(incompleteSet);
                newSet.add(set.get(index));
                moresubsets.add(newSet);
            }
            allSubsets.addAll(moresubsets);
        }
        return allSubsets;
    }



    public boolean getPath(int r, int c, List<Point> path, int[][] grid) {
        if (r < 0 || c < 0) {
            return false;
        }

        if (r == 0 && c == 0) {
            return true;
        }

        if (grid[r][c] == -1) {
            return false;
        }

        if (getPath(r, c - 1, path, grid) || getPath(r - 1, c, path, grid)) {
            path.add(new Point(r, c));
            return true;
        }
        return false;
    }

    public int childRun(int stepsRemaining) {
        if (this.childStepsMem.containsKey(stepsRemaining)) {
            return childStepsMem.get(stepsRemaining);
        }
        if (stepsRemaining == 0) return 1;
        if (stepsRemaining < 0) return 0;
        var value = childRun(stepsRemaining - 1) + childRun(stepsRemaining - 2) + childRun(stepsRemaining - 3);
        this.childStepsMem.put(stepsRemaining, value);
        return value;
    }

    @Data
    @AllArgsConstructor
    public static class Point {
        int x;
        int y;
    }


}
