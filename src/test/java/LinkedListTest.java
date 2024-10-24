import implementations.LinkedListImpl;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    public void basicTestImplementation() {
        List<Integer> values = List.of(1, 2, 3, 4, 5);
        LinkedListImpl<Integer> test = new LinkedListImpl<>(values);
//        test.printListReverse();
        test.printList();
    }

    @Test
    public void deleteNode() {
        List<Integer> values = List.of(1, 2, 3, 4, 5);
        LinkedListImpl<Integer> test = new LinkedListImpl<>(values);
        test.deleteNode(3);
        test.printList();
    }

    @Test
    public void kthToLastTest() {
        List<Integer> values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        LinkedListImpl<Integer> test = new LinkedListImpl<>(values);

        assertEquals(findKthToLast(test, 4), 9);
        assertEquals(findKthToLast(test, 12), 1);
        assertEquals(findKthToLast(test, 1), 12);
        assertEquals(findKthToLast(test, 13), -1);
    }

    @Test
    public void partitionTest() {
        List<Integer> valuesHappy = List.of(2, 3, 4, 7, 5, 3, 1, -3, -9);
        List<Integer> onlyEqual = List.of(2, 2, 2);
        List<Integer> onlyMore = List.of(3, 3, 3);
        List<Integer> empty = List.of();
        LinkedListImpl<Integer> testHappy = new LinkedListImpl<>(valuesHappy);
        LinkedListImpl<Integer> testEqual = new LinkedListImpl<>(onlyEqual);
        LinkedListImpl<Integer> testMore = new LinkedListImpl<>(onlyMore);
        LinkedListImpl<Integer> testEmpty = new LinkedListImpl<>(empty);

        assertTrue(new LinkedListImpl<Integer>(List.of(2, 1, -3, -9, 3, 4, 7, 5, 3)).equals(partitionList(testHappy, 3)));
        assertTrue(new LinkedListImpl<Integer>(List.of(2, 2, 2)).equals(partitionList(testEqual, 2)));
        assertTrue(new LinkedListImpl<Integer>(List.of(3, 3, 3)).equals(partitionList(testMore, 2)));
        assertTrue(new LinkedListImpl<Integer>(List.of()).equals(partitionList(testEmpty, 5)));

    }

    @Test
    public void isPalindromeTest() {
        List<Integer> palindromeOdd = List.of(1, 2, 3, 3, 2, 1);
        List<Integer> palindromeEven = List.of(1, 2, 3, 2, 1);
        List<Integer> palindromeSame = List.of(2, 2, 2);
        List<Integer> notPalindrome = List.of(1, 3, 3);
        List<Integer> almostPalindrome = List.of(1, 2, 3, 4, 5, 4, 3, 2, 2);
        List<Integer> empty = List.of();
        LinkedListImpl<Integer> testOdd = new LinkedListImpl<>(palindromeOdd);
        LinkedListImpl<Integer> testEven = new LinkedListImpl<>(palindromeEven);
        LinkedListImpl<Integer> testSame = new LinkedListImpl<>(palindromeSame);
        LinkedListImpl<Integer> testNot = new LinkedListImpl<>(notPalindrome);
        LinkedListImpl<Integer> testAlmost = new LinkedListImpl<>(almostPalindrome);
        LinkedListImpl<Integer> testEmpty = new LinkedListImpl<>(empty);

        assertTrue(isPalindrome(testOdd));
        assertTrue(isPalindrome(testEven));
        assertTrue(isPalindrome(testSame));
        assertFalse(isPalindrome(testNot));
        assertFalse(isPalindrome(testAlmost));
        assertTrue(isPalindrome(testEmpty));
    }

    @Test
    public void detectLoopTest() {
        List<Integer> shortLoop = List.of(0, 1, 2, 3, 4, 5);
        LinkedListImpl<Integer> shortLoopList = new LinkedListImpl<>(shortLoop);
        shortLoopList.createLoop(3);

        List<Integer> longerLoop = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        LinkedListImpl<Integer> longerLoopList = new LinkedListImpl<>(longerLoop);
        longerLoopList.createLoop(7);

        assertEquals(3, findLoopStart(shortLoopList));
        assertEquals(7, findLoopStart(longerLoopList));
    }

    @Test
    public void detectMergeTest() throws Exception {
        LinkedListImpl<Integer> first = new LinkedListImpl<>(List.of(0, 1, 2, 3, 4, 5));
        LinkedListImpl<Integer> second = new LinkedListImpl<>(List.of(0, 1, 1));
        var endOfSecond = second.getEnd();
        var sectionOfFirst = first.getAtIndex(3);
        endOfSecond.setChild(sectionOfFirst);

        assertEquals(3, findOverlap(first, second));
    }

    public int findOverlap(LinkedListImpl first, LinkedListImpl second) {
        HashSet<LinkedListImpl.Node> nodeSet = new HashSet<>();
        var firstIterator = first.getBeginning();
        var secondIterator = second.getBeginning();

        while (firstIterator != null) {
            nodeSet.add(firstIterator);
            firstIterator = firstIterator.getChild();
        }

        int index = 0;

        while (secondIterator != null) {
            if (nodeSet.contains(secondIterator)) {
                return index;
            }
            index++;
            secondIterator = secondIterator.getChild();
        }

        return index;
    }

    public int findLoopStart(LinkedListImpl<Integer> linkedList) {
        var fastIterator = linkedList.getBeginning().getChild().getChild();
        var slowIterator = linkedList.getBeginning().getChild();

        //navigate to intersection
        while(fastIterator != slowIterator) {
            fastIterator = fastIterator.getChild().getChild();
            slowIterator = slowIterator.getChild();
        }

        //now move slow iterator to start
        slowIterator = linkedList.getBeginning();

        //now collide them
        int index = 0;
        while(fastIterator != slowIterator) {
            fastIterator = fastIterator.getChild();
            slowIterator = slowIterator.getChild();
            index++;
        }
        return index;
    }


    private boolean isPalindrome(LinkedListImpl<Integer> list) {
        if (list.getBeginning() == null) {
            return true;
        }
        var reversedList = reverseList(list);
        return reversedList.equals(list);
    }

    @Test
    public void reverseListTest() {
        List<Integer> valuesHappy = List.of(1, 2, 3, 4, 5);

        LinkedListImpl<Integer> testHappy = new LinkedListImpl<>(valuesHappy);
        assertTrue(new LinkedListImpl<>(List.of(5, 4, 3, 2, 1)).equals(reverseList(testHappy)));
    }

    public LinkedListImpl<Integer> reverseList(LinkedListImpl<Integer> listToReverse) {
        if (listToReverse.getBeginning() == null) {
            return listToReverse;
        }
        if (listToReverse.getBeginning().getChild() == null) {
            return listToReverse;
        }
        var iterator = listToReverse.getBeginning();
        var newList = new LinkedListImpl<Integer>(List.of());
        var iteratorNew = new LinkedListImpl.Node<Integer>(iterator.getValue());
        while (iterator.getChild() != null) {
            var node = iteratorNew;
            iterator = iterator.getChild();
            iteratorNew = new LinkedListImpl.Node<Integer>(iterator.getValue());
            iteratorNew.setChild(node);
        }
        newList.setBeginning(iteratorNew);
        return newList;
    }

    public LinkedListImpl<Integer> partitionList(LinkedListImpl<Integer> input, Integer value) {
        LinkedListImpl<Integer> smaller = new LinkedListImpl<>(List.of());
        LinkedListImpl<Integer> biggerOrEqual = new LinkedListImpl<>(List.of());;
        LinkedListImpl.Node<Integer> iterator = input.getBeginning();
        LinkedListImpl.Node<Integer> smallerLastNode = null;
        while (iterator != null) {
            var node = new LinkedListImpl.Node<Integer>(iterator.getValue());
            if (iterator.getValue() < value) {
                smallerLastNode = smaller.appendNode(node);
            }
            else {
                biggerOrEqual.appendNode(node);
            }
            iterator = iterator.getChild();
        }
        if (smaller.isEmpty()) {
            return biggerOrEqual;
        }
        if (biggerOrEqual.isEmpty()) {
            return smaller;
        }

        smallerLastNode.setChild(biggerOrEqual.getBeginning());
        return smaller;
    }

    private Integer findKthToLast(LinkedListImpl<Integer> linkedList, Integer k) {
        var runner = linkedList.getBeginning();
        var iterator = linkedList.getBeginning();

        for (int i = 0; i < k; i++) {
            if (runner == null) {
                return -1; // we ran over
            }
            runner = runner.getChild();
        }

        while (runner != null) {
            iterator = iterator.getChild();
            runner = runner.getChild();
        }
        return iterator.getValue();
    }
}
