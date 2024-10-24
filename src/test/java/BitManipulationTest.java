import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BitManipulationTest {

    @Test
    public void insertBitsTest() {
        int happy = 0b0000_0000_0000_0000;
        int insertHappy = 0b0000_0000_1001_0000;

        int fullish = 0b0111_1110_0001_1111;
        int insertFullish = 0b0000_0000_0000_1111;

        int one = 0b0000_0000_0000_0001;
        int empty = 0b0000_0000_0000_0000;

        assertEquals(insertHappy, insert(happy, insertHappy, 0, 7));
        assertEquals(0b0111_1111_1111_1111, insert(fullish, insertFullish, 5, 8));
        assertEquals(0b0111_1110_0101_1111, insert(fullish, one, 6, 7));
        assertEquals(fullish, insert(fullish, empty, 6, 8));
    }

    private int insert(int big, int small, int starting, int ending) {
        if (!checkSpace(big, starting, ending)) {
            return 0;
        }
        int shifted = small << starting;
        return big | shifted;
    }

    private boolean checkSpace(int big, int starting, int ending) {
        int mask = ((0b0000_0000_0000_0001 << (ending - starting)) - 1) << starting;
        System.out.println(Integer.toBinaryString(mask));
        return (big & mask) == 0;
    }

    @Test
    public void flipBitTest() {
        var happy = 0b1111_0111_1111_0011_0000_0000_0000_0000;
        var overTwo = 0b1111_0011_0101_1111_0000_0000_0000_0000;
        var none = 0b0000_0000_0000_0000_0000_0000_0000_0000;
        var one = 0b0000_0000_0000_0001_0000_0000_0000_0000;

        assertEquals(12, longestSeq(happy));
        assertEquals(7, longestSeq(overTwo));
        assertEquals(1, longestSeq(none));
        assertEquals(2, longestSeq(one));
    }

    private int longestSeq(int bits) {
        int mask = 1;
        int max = 0;
        int currentSeq = 0;
        int prevSeq = 0;
        for (int i = 0; i < 32; i++) {
            if ((bits & mask) != 0) {
                currentSeq++;
            }
            else {
                if (prevSeq + currentSeq > max) {
                    max = prevSeq + currentSeq;
                }
                prevSeq = currentSeq;
                currentSeq = 0;
            }
            mask = mask << 1;
        }
        if (prevSeq + currentSeq > max) {
            max = prevSeq + currentSeq;
        }
        return max + 1;
    }

//    @Test
//    public void nextSmallestLargestTest() {
//        var happy = 0b1111_0111_1111_0000_0011_0000_0000_0000;
//        var none = 0b0000_0000_0000_0000_0000_0000_0000_0000;
//        var one = 0b0000_0000_0000_0000_0000_0000_0000_0001;
//
//        assertEquals(0, nextSmallest(none));
//        assertEquals(0, nextLargest(none));
//    }

    @Test
    public void howManyFlipTest() {
        var happyOne = 0b1111_0111_1111_0011_0000_0000_0000_0000;
        var happyTwo = 0b1111_0111_1111_0000_1100_0000_0000_0000;
        var none = 0b0000_0000_0000_0000_0000_0000_0000_0000;
        var noneSame = 0b0000_0000_0000_0000_0000_0000_0000_0000;
        var one = 0b0000_0000_0000_0001_0000_0000_0000_0000;
        var oneTwo = 0b0000_0000_0000_0000_0000_0000_0000_0000;

        assertEquals(4, howManyFlip(happyOne, happyTwo));
        assertEquals(0, howManyFlip(none, noneSame));
        assertEquals(1, howManyFlip(one, oneTwo));
    }

    private int howManyFlip(int first, int second) {
        int mask = 1;
        int count = 0;
        int res = first ^ second;
        for (int i = 0; i < 32; i++) {
            if ((res & mask) != 0) {
                count++;
            }
            mask = mask << 1;
        }
        return count;
    }

    private int countZeroes(int bits) {
        int zeroes = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((bits & mask) == 0) {
                zeroes++;
            }
            mask = mask << 1;
        }
        return zeroes;
    }
}
