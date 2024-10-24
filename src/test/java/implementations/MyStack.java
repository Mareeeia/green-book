package implementations;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class MyStack<T> {
    private StackElement<T> top;
    private int size = 0;

    public MyStack(List<T> elements) {
        for (T element: elements) {
            this.insert(element);
        }
    }

    public void insert(T newTop) {
        var element = new StackElement<>(newTop);
        element.setNext(this.top);
        this.size++;
        this.top = element;
    }

    public T pop() {
        var element = this.top.getValue();
        this.top = this.top.getNext();
        this.size--;
        return element;
    }

    public T peek() {
        if (this.top == null) {
            return null;
        }
        return this.top.getValue();
    }    @Data
    public static class StackElement<T> {
        private T value;
        private StackElement<T> next;
        public StackElement(T value) {
            this.value = value;
        }
    }
}
