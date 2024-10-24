package implementations;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LinkedListImpl<T> {

    protected Node<T> beginning;

    public LinkedListImpl(Node<T> beginning) {
        this.beginning = beginning;
    }

    public LinkedListImpl(List<T> values) {
        Node<T> previous = null;
        for (int i = 0; i < values.size(); i++) {
            Node<T> node = new Node<T>(values.get(i));
            if (i == 0) {
                this.beginning = node;
            }
            else {
                previous.setChild(node);
            }
            previous = node;
        }
    }

    public boolean isEmpty() {
        return this.beginning == null;
    }

    public Node<T> getEnd() {
        var iterator = this.beginning;
        while (iterator.getChild() != null) {
            iterator = iterator.getChild();
        }
        return iterator;
    }

    public Node<T> getAtIndex(int index) throws Exception {
        if (index < 0) {
            throw new Exception();
        }
        var iterator = this.beginning;
        for (int i = 0; i < index; i++) {
            iterator = iterator.getChild();
        }
        return iterator;
    }

    public void createLoop(int index) {
        var end = this.getEnd();
        var iterator = this.beginning;
        for (int i = 0; i < index; i++) {
            iterator = iterator.getChild();
        }
        end.setChild(iterator);
    }

    public boolean equals(LinkedListImpl<T> other) {
        var iteratorOther = other.getBeginning();
        var iterator = this.beginning;
        while (iterator != null && iteratorOther != null) {
            if (iterator.getValue() != iteratorOther.getValue()) {
                return false;
            }
            iterator = iterator.getChild();
            iteratorOther = iteratorOther.getChild();
        }
        return (iterator == null) == (iteratorOther == null);
    }

    public Node<T> appendNode(Node<T> node) {
        if (this.beginning == null) {
            this.beginning = node;
            return node;
        }
        var iterator = this.beginning;
        while (iterator.getChild() != null) {
            iterator = iterator.getChild();
        }
        iterator.setChild(node);
        return node;
    }

    public void printList() {
        Node<T> iterator = this.beginning;
        while (iterator != null) {
            System.out.println(iterator.getValue());
            iterator = iterator.getChild();
        }
    }

    public void deleteNode(T value) {
        var iterator = this.beginning;
        while (iterator != null) {
            if (iterator.getChild().getValue() == value) {
                var newChild = iterator.getChild().getChild();
                iterator.setChild(newChild);
                return;
            }
        }
    }

//    public void printListReverse() {
//        Node<T> iterator = this.beginning;
//        while (iterator.getChild() != null) {
//            iterator = iterator.getChild();
//        }
//        while (iterator != null) {
//            System.out.println(iterator.getValue());
//            iterator = iterator.getParent();
//        }
//    }
    @Getter
    @Setter
    public static class Node<T> {

        public Node(T value) {
            this.value = value;
        }

//        public Node(T value, Node<T> parent) {
//            this.value = value;
//            this.parent = parent;
//        }
        private T value;
//        private Node<T> parent;
        private Node<T> child;
    }
}
