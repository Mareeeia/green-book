import implementations.MyBinarySearchTree;
import implementations.MyGraph;
import implementations.MyQueue;
import implementations.MyStack;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTreeTest {

    private int previous = 0;

    @Test
    public void graphRouteBetweenNodesTest() {
        var unconnected = new MyGraph(List.of(1, 2, 3, -1, -2, -3),
                List.of(Pair.of(1, 2),
                        Pair.of(2, 3),
                        Pair.of(3, 1),
                        Pair.of(-1, -2),
                        Pair.of(-1, -3),
                        Pair.of(-2, -3)
                        ));
        var connected = new MyGraph(List.of(1, 2, 3, -1, -2, -3),
                List.of(Pair.of(1, 2),
                        Pair.of(2, 3),
                        Pair.of(3, 1)
                ));

        assertFalse(isThereConnection(1, -3, unconnected));
        assertTrue(isThereConnection(-1, 3, unconnected));
        assertTrue(isThereConnection(3, 1, connected));
    }

    @Test
    public void minimalTreeTest() {
        List<Integer> simple = List.of(1, 2, 3);
        List<Integer> niceListOdd = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> niceListEven = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> emptyList = List.of();
        List<Integer> badList = List.of(3, 2, 1);
        List<Integer> nullList = null;

        var tree = makeTreeFromSortedList(simple);
        var testTree = new MyBinarySearchTree();
        testTree.insert(2);
        testTree.insert(1);
        testTree.insert(3);
        assertTrue(testTree.equals(tree));
        var longerTree = makeTreeFromSortedList(niceListEven);
        var longerTreeOdd = makeTreeFromSortedList(niceListOdd);
        var emptyTree = makeTreeFromSortedList(emptyList);
        var badTree = makeTreeFromSortedList(badList);
        var nullTree = makeTreeFromSortedList(nullList);
    }

    @Test
    public void checkBalancedTest() {
        List<Integer> niceListOdd = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> niceListEven = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> slightlyMoreComplicated = List.of(4, 3, 5, 7, 6, 15, 17);

        var balancedTreeOdd = makeTreeFromSortedList(niceListOdd);
        var balancedTreeEven = makeTreeFromSortedList(niceListEven);
        var notBalanced = new MyBinarySearchTree();
        for (int entry: niceListEven) {
            notBalanced.insert(entry);
        }
        var alsoNotBalanced = new MyBinarySearchTree();
        for (int entry: slightlyMoreComplicated) {
            alsoNotBalanced.insert(entry);
        }
        var empty = new MyBinarySearchTree();

        assertTrue(isBalanced(balancedTreeEven));
        assertTrue(isBalanced(balancedTreeOdd));
        assertFalse(isBalanced(notBalanced));
        assertFalse(isBalanced(alsoNotBalanced));
        assertTrue(isBalanced(empty));
    }

    @Test
    public void checkBSTTest() {
        List<Integer> niceListOdd = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> niceListEven = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        var balancedTreeOdd = makeTreeFromSortedList(niceListOdd);
        var balancedTreeEven = makeTreeFromSortedList(niceListEven);

        assertTrue(isBST(balancedTreeEven));
        assertTrue(isBST(balancedTreeOdd));

        balancedTreeOdd.getRoot().getLeft().getLeft().setValue(10);

        assertFalse(isBST(balancedTreeOdd));
    }

    @Test
    public void getNextNodeTest() {
        List<Integer> niceListOdd = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> niceListEven = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        var notBalanced = new MyBinarySearchTree();
        for (int entry: niceListEven) {
            notBalanced.insert(entry);
        }

        var balancedTreeOdd = makeTreeFromSortedList(niceListOdd);

        assertEquals(4, getNextNode(balancedTreeOdd.getRoot(), 3));
        assertEquals(4, getNextNode(notBalanced.getRoot(), 3));
        assertEquals(Integer.MIN_VALUE, getNextNode(notBalanced.getRoot(), -13));
    }

    @Test
    public void buildOrderTest() {
        List<Integer> nodeList = List.of(2, 3, 4, 5, 6, 7);
        List<Pair<Integer, Integer>> depList = List.of(
                Pair.of(7, 3),
                Pair.of(7, 6),
                Pair.of(7, 5),
                Pair.of(2, 7),
                Pair.of(4, 2)
        );
        // first, find root or set of roots

        // then, build order with BFS
    }

    @Test
    public void getCommonAncestorTest() {
        List<Integer> niceListOdd = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        var balancedTreeOdd = makeTreeFromSortedList(niceListOdd);

        assertEquals(5, firstCommon(balancedTreeOdd.getRoot(), 3, 7));
        assertEquals(9, firstCommon(balancedTreeOdd.getRoot(), 1, 9));
    }



    private Integer firstCommon(MyBinarySearchTree.Node node, Integer a, Integer b) {
        if (node == null) {
            return null;
        }
        Integer left = firstCommon(node.getLeft(), a, b);
        Integer right = firstCommon(node.getRight(), a, b);
        if (node.getValue() == a) {
            return a;
        }
        if (node.getValue() == b) {
            return b;
        }
        if ((left == a && right == b) ||
                (left == b && right == a) ||
                (node.getValue() == a && right == b) ||
                (node.getValue() == b && right == a) ||
                (node.getValue() == b && left == a) ||
                (node.getValue() == b && left == b)
        ) {
            return node.getValue();
        }
        if (left != null) {
            return left;
        }
        return right;
    }

    public int getNextNode(MyBinarySearchTree.Node node, int value) {
        if (node == null) {
            return Integer.MIN_VALUE;
        }
        int leftSide = getNextNode(node.getLeft(), value);
        if (this.previous == value) {
            this.previous = node.getValue();
            return node.getValue();
        }
        this.previous = node.getValue();
        int rightSide = getNextNode(node.getRight(), value);
        return Math.max(leftSide, rightSide);
    }

    private boolean isBST(MyBinarySearchTree tree) {
        if (tree.getRoot() == null) {
            return true;
        }
        return isBST(tree.getRoot(), null, null);
    }

    private boolean isBST(MyBinarySearchTree.Node node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }

        if ((min != null && node.getValue() <= min) || (max != null && node.getValue() >= max)) {
            return false;
        }

        return isBST(node.getLeft(), min, node.getValue()) && isBST(node.getRight(), node.getValue(), max);
    }
    private boolean isBalanced(MyBinarySearchTree tree) {
        if (tree.getRoot() == null) {
            return true;
        }
        return getHeight(tree.getRoot()) > 0;
    }

    private int getHeight(MyBinarySearchTree.Node node) {

        if (node == null) {
            return -1;
        }

        int left = getHeight(node.getLeft());
        int right = getHeight(node.getRight());
        if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (Math.abs(left - right) > 1) {
            return Integer.MIN_VALUE;
        }
        return Math.max(left, right) + 1;
    }


    private MyBinarySearchTree makeTreeFromSortedList(List<Integer> newList) {
        if (newList == null || newList.isEmpty()) {
            return null;
        }
        MyBinarySearchTree tree = new MyBinarySearchTree();
        addMiddleToTree(0, newList.size(), tree, newList);
        return tree;
    }

    private boolean addMiddleToTree(int left, int right, MyBinarySearchTree tree, List<Integer> list) {
        int middle = (left + right)/2;
        if (tree.insert(list.get(middle))) {
            return addMiddleToTree(left, middle, tree, list) || addMiddleToTree(middle, right, tree, list);
        }
        return false;
    }

    private boolean isThereConnection(int first, int second, MyGraph graph) {
        MyGraph.Node firstNode = graph.getNodes().get(first);
        MyGraph.Node secondNode = graph.getNodes().get(second);

        MyQueue<MyGraph.Node> queue = new MyQueue<>(List.of(firstNode, secondNode));

        while (!queue.isEmpty()) {
            var node = queue.pop();
            if (node.isMarked()) {
                return true;
            }
            if (!node.isVisited()) {
                node.setMarked(true);
                node.setVisited(true);
                for (MyGraph.Node child: node.getChildren()) {
                    queue.insert(child);
                }
            }
        }
        return false;
    }
}
