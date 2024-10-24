package implementations;

import lombok.Data;
import lombok.Getter;

public class MyBinarySearchTree {
    @Getter
    private Node root;
    @Data
    public static class Node {
        public Node(int value) {
            this.value = value;
        }
        private int value;
        private Node left;
        private Node right;
    }

    public boolean insert(int value) {
        boolean inserted = false;
        if (this.root == null) {
            this.root = new Node(value);
            inserted = true;
        }
        var iterator = this.root;
        while (!inserted && iterator != null) {
            if (iterator.getValue() == value) {
                return false;
            }
            if (value > iterator.getValue()) {
                if (iterator.getRight() != null) {
                    iterator = iterator.getRight();
                }
                else {
                    iterator.setRight(new Node(value));
                    inserted = true;
                }
            }
            else {
                if (iterator.getLeft() != null) {
                    iterator = iterator.getLeft();
                }
                else {
                    iterator.setLeft(new Node(value));
                    inserted = true;
                }
            }
        }
        return inserted;
    }

    private Node findValue(int value) {
        var iterator = root;
        while (iterator != null) {
            if (value == iterator.getValue()) return iterator;
            if (value > iterator.getValue()) iterator = iterator.getRight();
            if (value < iterator.getValue()) iterator = iterator.getLeft();
        }
        return null;
    }

    private Node findSuccessor(Node node) {
        var iterator = node;
        while (iterator.getLeft() != null) {
            iterator = iterator.getLeft();
        }
        return iterator;
    }

    public Node delete(Node root, int value) {
        if (root == null) return root;
        if (root.getValue() > value) root.setLeft(delete(root.getLeft(), value));
        if (root.getValue() < value) root.setRight(delete(root.getRight(), value));

        if (root.getValue() == value) {
            // go to right subtree
            var succ = findSuccessor(root);
            root.setValue(succ.getValue());

            root.setRight(delete(root.getRight(), root.getValue()));
        }

        return root;
    }

    public boolean equals(MyBinarySearchTree other) {
        var ourIterator = this.root;
        var otherIterator = other.getRoot();
        return compareNodes(ourIterator, otherIterator);
    }

    private boolean compareNodes(Node local, Node other) {
        if (local == null && other == null) {
            return true;
        }
        if ((local == null || other == null)) {
            return false;
        }
        if (local.getValue() == other.getValue()) {
            return compareNodes(local.getLeft(), other.getLeft()) && compareNodes(local.getRight(), other.getRight());
        }
        return false;
    }
}
