package implementations;

import lombok.Data;

import java.util.Comparator;
import java.util.List;


public class StackWithMin {
    private StackElement top;
    private StackElement min;

    public StackWithMin(List<Integer> elements) {
        for (Integer element: elements) {
            this.insert(element);
        }
    }

    public Integer getMin() {
        if (this.min == null) {
            return null;
        }
        return this.min.getValue();
    }

    public void insert(Integer newTop) {
        var element = new StackElement(newTop);
        element.setNext(this.top);
        
        if (this.min == null) {
            this.min = element;
        }
        if (this.min.getValue() >= newTop) {
            var newMin = new StackElement(newTop);
            newMin.setNext(this.min);
            this.min = newMin;
        }
        this.top = element;
    }

    public Integer pop() {
        var element = this.top.getValue();
        this.top = this.top.getNext();
        if (element == this.min.getValue()) {
            this.min = this.min.getNext();
        }
        return element;
    }
    @Data
    static class StackElement {
        private int value;
        private StackElement next;
        public StackElement(int value) {
            this.value = value;
        }
    }
}
