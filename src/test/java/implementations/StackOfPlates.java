package implementations;

import java.util.ArrayList;
import java.util.List;

public class StackOfPlates<T> {

    private Integer currentStackSize = 0;
    private Integer currentStackNumber = 0;
    private static final Integer MAX_STACK_SIZE = 3;
    private List<MyStack.StackElement<T>> stackTops = new ArrayList<>();

    public StackOfPlates(List<T> elements) {
        for (T element: elements) {
            this.insert(element);
        }
    }

    public T popAt(int index) {
        var element = this.stackTops.get(index).getValue();
        if (this.stackTops.get(index).getNext() == null) {
            this.stackTops.remove(index);
        }
        else {
            this.stackTops.set(index, this.stackTops.get(index).getNext());
        }
        return element;

    }

    public void insert(T newTop) {
        var element = new MyStack.StackElement<>(newTop);
        if (MAX_STACK_SIZE.equals(this.currentStackSize) || stackTops.isEmpty()) {
            stackTops.add(element);
            this.currentStackSize = 1;
            this.currentStackNumber++;
        }
        else {
            this.currentStackSize++;
            element.setNext(this.stackTops.get(this.currentStackNumber - 1));
        }
        this.stackTops.set(this.currentStackNumber - 1, element);
    }

    public T pop() {
        var element = this.stackTops.get(this.currentStackNumber - 1).getValue();
        this.stackTops.set(this.currentStackNumber - 1, this.stackTops.get(this.currentStackNumber - 1).getNext());
        if (this.currentStackSize == 1) {
            this.stackTops.remove(this.currentStackNumber - 1);
            this.currentStackNumber--;
            this.currentStackSize = MAX_STACK_SIZE;
        }
        return element;
    }
}
