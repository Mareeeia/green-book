import com.sun.source.tree.AssertTree;
import implementations.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StacksQueuesTest {

    @Test
    public void threeStacksFromArrayTest() {
        ArrayStacks arrayStacks = new ArrayStacks();
        arrayStacks.push(0, 0);
        arrayStacks.push(1, 1);
        arrayStacks.push(2, 2);
        arrayStacks.push(0, 10);
        arrayStacks.push(1, 11);
        arrayStacks.push(2, 12);
        arrayStacks.push(0, 110);
        arrayStacks.push(1, 111);
        arrayStacks.push(2, 112);

        assertEquals(112, arrayStacks.pop(2));
        assertEquals(111, arrayStacks.pop(1));
        assertEquals(110, arrayStacks.pop(0));
        assertEquals(12, arrayStacks.pop(2));
        assertEquals(11, arrayStacks.pop(1));
        assertEquals(10, arrayStacks.pop(0));
    }

    @Test
    public void minStackTest() {
        var happyCase = new StackWithMin(List.of(2, 1, 0, 3));
        var duplicate = new StackWithMin(List.of(1, 2, 1, 4, 1));
        var empty = new StackWithMin(List.of());
        var equal = new StackWithMin(List.of(-1, -1, -1));

        assertEquals(0, happyCase.getMin());
        assertEquals(1, duplicate.getMin());
        assertNull(empty.getMin());
        assertEquals(-1, equal.getMin());

        duplicate.pop();
        assertEquals(1, duplicate.getMin());
    }

    @Test
    public void stackOfPlatesTest() {
        var happyCase = new StackOfPlates<Integer>(List.of(1, 2, 3, 4, 5, 6, 7));

        assertEquals(7, happyCase.pop());
        happyCase.insert(8);
        assertEquals(8, happyCase.pop());
        happyCase.insert(8);
        happyCase.insert(9);
        assertEquals(9, happyCase.pop());
    }

    @Test
    public void popAtTest() {
        var happyCase = new StackOfPlates<Integer>(List.of(1, 2, 3, 4, 5, 6, 7));

        assertEquals(6, happyCase.popAt(1));
        assertEquals(5, happyCase.popAt(1));
        assertEquals(4, happyCase.popAt(1));
        assertEquals(7, happyCase.popAt(1));
    }

    @Test
    public void queueFromStacksTest() {
        var happyCase = new QueueFromStacks<Integer>(List.of(1, 2));

        assertEquals(1, happyCase.pop());
        happyCase.push(-2);
        happyCase.push(-3);
        happyCase.push(-4);
        happyCase.pop();
        assertEquals(-2, happyCase.pop());
    }

    @Test
    public void sortStackTest() {
        var happyCase = new MyStack<Integer>(List.of(2, 4, 3, 1));

        happyCase = sortStack(happyCase);

        assertEquals(1, happyCase.pop());
        assertEquals(2, happyCase.pop());
        assertEquals(3, happyCase.pop());
        assertEquals(4, happyCase.pop());
    }

    @Test
    public void getPetTest() {

    }

    private MyStack<Integer> sortStack(MyStack<Integer> toSort) {
        MyStack<Integer> bufferStack = new MyStack<>(List.of());
        MyStack<Integer> sortedStack = new MyStack<>(List.of());

        int max = Integer.MIN_VALUE;
        while (0 < Math.max(bufferStack.getSize(), toSort.getSize())) {
            getStackMax(toSort, bufferStack, sortedStack);
        }

        return sortedStack;
    }

    private void getStackMax(MyStack<Integer> toSort, MyStack<Integer> buffer, MyStack<Integer> sorted) {
        int max = Integer.MIN_VALUE;
        while (toSort.peek() != null) {
            var value = toSort.pop();
            if (value > max) {
                max = value;
            }
            buffer.insert(value);
        }
        while (buffer.peek() != null) {
            var value = buffer.pop();
            if (value == max) {
                sorted.insert(value);
            }
            else {
                toSort.insert(value);
            }
        }
    }

}
