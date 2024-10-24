package implementations;

import java.util.List;

public class QueueFromStacks<T> {
    MyStack<T> firstStack = new MyStack<>(List.of());
    MyStack<T> secondStack = new MyStack<>(List.of());;

    public QueueFromStacks(List<T> elements) {
        for (var element: elements) {
            this.firstStack.insert(element);
        }
    }

    public T pop() {
        if (secondStack.peek() == null) {
            while (firstStack.peek() != null) {
                var elem = firstStack.pop();
                this.secondStack.insert(elem);
            }
        }
        return this.secondStack.pop();
    }

    public void push(T value) {
//        if (firstStack.peek() == null && secondStack.peek() != null) {
//            while (secondStack.peek() != null) {
//                var elem = secondStack.pop();
//                this.firstStack.insert(elem);
//            }
//        }
        this.firstStack.insert(value);
    }
}
