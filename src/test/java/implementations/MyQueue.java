package implementations;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class MyQueue<T> {
    private QueueElement<T> first;
    private QueueElement<T> last;

    public MyQueue(List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            this.insert(elements.get(i));
            if (i == 0) {
                this.first = this.last;
            }
        }
    }

    public void insert(T newlast) {
        var element = new QueueElement<>(newlast);
        if (this.last != null) {
            this.last.setNext(element);
        }
        else {
            this.first = element;
        }
        this.last = element;
    }

    public T pop() {
        var element = this.first.getValue();
        this.first = this.first.getNext();
        return element;
    }

    public T peek() {
        return this.first.getValue();
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    @Data
    public static class QueueElement<T> {
        private T value;
        private QueueElement<T> next;
        public QueueElement(T value) {
            this.value = value;
        }
    }
}
