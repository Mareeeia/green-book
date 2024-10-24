import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class ArraysTest {
    @Test
    public void checkDuplicates() {
        String noDup = "abcdefghijkl";
        String someDup = "abcdefghijklmnopnqn";
        String edgeCaseEmpty = "";
        String nullString = null;
        String onlyDup = "aaaaaaa";

        assertFalse(isDuplicatedCharacters(noDup));
        assertTrue(isDuplicatedCharacters(someDup));
        assertFalse(isDuplicatedCharacters(edgeCaseEmpty));
        assertFalse(isDuplicatedCharacters(nullString));
        assertTrue(isDuplicatedCharacters(onlyDup));
    }

    @Test
    public void urlify() {
        Pair<char[], Integer> happyCase = Pair.of(toCharArray("Call of Cthulhu    "), "Call of Cthulhu    ".length());
        Pair<char[], Integer> struckSpaces = Pair.of(toCharArray("Call of   Cthulhu        "), "Call of   Cthulhu        ".length());
        Pair<char[], Integer> onlySpaces = Pair.of(toCharArray("         "), "         ".length());

        assertTrue(compareCharArray(makeLikeUrl(happyCase.getKey(), happyCase.getValue()), toCharArray("Call%20of%20Cthulhu")));
        assertTrue(compareCharArray(makeLikeUrl(struckSpaces.getKey(), struckSpaces.getValue()), toCharArray("Call%20of%20%20%20Cthulhu")));
        assertTrue(compareCharArray(makeLikeUrl(onlySpaces.getKey(), onlySpaces.getValue()), toCharArray("%20%20%20")));
    }

    @Test
    public void isPermutation() {
        // recycles old algorithms
    }

    @Test
    public void oneAway() {
        List<String> happyCaseLength = List.of("pale", "ple");
        List<String> happyCaseLetter = List.of("pale", "kale");
        List<String> wrongLength = List.of("stone", "one");
        List<String> lengthAndLetter = List.of("pale", "bla");
        List<String> empty = List.of("", "");
        List<String> emptyDiff = List.of("", "a");
        List<String> emptyDiffWrong = List.of("", "an");

        assertTrue(isOneAway(happyCaseLength));
        assertTrue(isOneAway(happyCaseLetter));
        assertFalse(isOneAway(wrongLength));
        assertFalse(isOneAway(lengthAndLetter));
        assertTrue(isOneAway(empty));
        assertTrue(isOneAway(emptyDiff));
        assertFalse(isOneAway(emptyDiffWrong));
    }

    @Test
    public void stringCompressionTest() {
        List<String> happyCase = List.of("aaabbbccccaaa", "a3b3c4a3");
        List<String> noDuplicates = List.of("abcd", "abcd");
        List<String> twosOnly = List.of("aabcceedee", "aabcceedee");
        List<String> oneLetterOnly = List.of("aaaaa", "a5");
        List<String> empty = List.of("", "");

        assertEquals(happyCase.get(1), compressString(happyCase.get(0)));
        assertEquals(noDuplicates.get(1), compressString(noDuplicates.get(0)));
        assertEquals(twosOnly.get(1), compressString(twosOnly.get(0)));
        assertEquals(oneLetterOnly.get(1), compressString(oneLetterOnly.get(0)));
        assertEquals(empty.get(1), compressString(empty.get(0)));
    }

    @Test
    public void rotateMatrixTest() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
        };
        int[][] matrixExpected = {
                {1, 1, 1, 1},
                {2, 2, 2, 2},
                {3, 3, 3, 3},
                {4, 4, 4, 4},
        };

        assertArrayEquals(matrixExpected, rotateMatrix(matrix));
    }

    @Test
    public void stringRotationTest() {
        List<String> happyCase = List.of("waterbottle", "erbottlewat");
        List<String> equalStrings = List.of("eeeee", "eeeee");
        List<String> emptyStrings = List.of("", "");
        List<String> almostRotation = List.of("waterbittle", "erbottlewat");
        List<String> differnentLength = List.of("book", "okkbo");

        assertTrue(isRotation(happyCase.get(0), happyCase.get(1)));
        assertTrue(isRotation(equalStrings.get(0), equalStrings.get(1)));
        assertTrue(isRotation(emptyStrings.get(0), emptyStrings.get(1)));
        assertFalse(isRotation(almostRotation.get(0), almostRotation.get(1)));
        assertFalse(isRotation(differnentLength.get(0), differnentLength.get(1)));
    }

    private boolean isSubstring(String first, String second) {
        return first.contains(second);
    }

    private boolean isRotation(String first, String second) {
        if (first.equals(second)) {
            return true;
        }
        if (first.length() != second.length()) {
            return false;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(second);
        builder.append(second);
        return isSubstring(builder.toString(), first);
    }

    private int[][] rotateMatrix(int[][] matrix) {
        int n = matrix.length;
        for (int layer = 0; layer < n/2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;

                int top = matrix[first][i];

                matrix[first][i] = matrix[last - offset][first];

                matrix[last - offset][first] = matrix[last][last - offset];

                matrix[last][last - offset] = matrix[i][last];

                matrix[i][last] = top;
            }
        }
        return matrix;
    }

    private String compressString(String input) {
        if ("".equals(input)) {
            return "";
        }
        char currentLetter = input.charAt(0);
        int counter = 1;
        char[] charArray = toCharArray(input);
        int indexOfResult = 0;
        int expectedLength = determineExpectedLength(charArray);
        var result = new char[expectedLength];
        if (input.length() == expectedLength) {
            return input;
        }
        for (int i = 1; i < input.length(); i++) {
            if (currentLetter == charArray[i]) {
                counter++;
            }
            if (currentLetter != charArray[i] || i == input.length() - 1) {
                if (counter >= 2) {
                    result[indexOfResult] = currentLetter;
                    result[indexOfResult + 1] = (char)(counter + '0');
                    indexOfResult += 2;
                }
                if (counter == 1) {
                    result[indexOfResult] = currentLetter;
                    indexOfResult += 1;
                }
                counter = 1;
                currentLetter = charArray[i];
            }
        }
        return String.valueOf(result);
    }

    private int determineExpectedLength(char[] input) {
        int counter = 1;
        char currentLetter = input[0];
        int totalLength = 0;
        for (int i = 1; i < input.length; i++) {
            if (currentLetter == input[i]) {
                counter++;
            }
            else {
                totalLength += getTotalLengthIncrementation(counter);
                counter = 1;
                currentLetter = input[i];
            }
        }
        totalLength += getTotalLengthIncrementation(counter);
        return totalLength;
    }

    private int getTotalLengthIncrementation(int counter) {
        if (counter >= 2) {
            return 2;
        }
        if (counter == 1) {
            return 1;
        }
        return 0;
    }

    private boolean isOneAway(List<String> strings) {
        var first = strings.get(0);
        var second = strings.get(1);

        int differences = 0;

        int abs = Math.abs(first.length() - second.length());
        int min = Math.min(first.length(), second.length());
        boolean firstIsLonger = first.length() > second.length();
        if (abs > 1) {
            return false;
        }

        for (int i = 0; i < min; i++) {
            if (i <= min - 2 && first.charAt(i) != second.charAt(i)
                    && first.charAt(i + 1) != second.charAt(i + 1) && abs == 1) {
                differences++;
                if (firstIsLonger) {
                    second = second.substring(0, i) + first.charAt(i + 1) + second.substring(i, min);
                }
            }
            else {
                if (first.charAt(i) != second.charAt(i)) {
                    differences++;
                }
            }
            if (differences > 1) {
                return false;
            }
        }
        return true;
    }

    private char[] toCharArray(String sample) {
        char[] ch = new char[sample.length()];

        // Copying character by character into array
        // using for each loop
        for (int i = 0; i < sample.length(); i++) {
            ch[i] = sample.charAt(i);
        }
        return ch;
    }

    private boolean compareCharArray(char[] first, char[] second) {
        if (first.length != second.length) {
            return false;
        }

        for (int i = 0; i < first.length; i++) {
            if (first[i] != second[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isDuplicatedCharacters(String testString) {
        if (testString == null) {
            return false;
        }
        int alphabet = 0b0000_0000_0000_0000_0000_0000_0000_0000;
        for (int i = 0; i < testString.length(); i++) {
            int index = testString.charAt(i) - 'a';
            if ((alphabet & (1L << index)) != 0) {
                return true;
            }
            alphabet = alphabet | (1 << index);
        }
        return false;
    }

    private char[] makeLikeUrl(char[] input, Integer length) {
        char[] result = new char[length];
        int resultIndex = 0;
        int stop = 2 * totalSpaces(input, length) / 3;

        for (int i = 0; i < length - stop; i++) {
            if (input[i] == ' ') {
                result[resultIndex] = '%';
                result[resultIndex+1] = '2';
                result[resultIndex+2] = '0';
                resultIndex += 3;
            }
            else {
                result[resultIndex] = input[i];
                resultIndex +=1;
            }
        }
        return result;
    }

    private int totalSpaces(char[] input, Integer length) {
        int totalSpacesCount = 0;
        for (int i= 0; i < length; i++) {
            if (input[i] == ' ') {
                totalSpacesCount++;
            }
        }
        return totalSpacesCount;
    }
}
