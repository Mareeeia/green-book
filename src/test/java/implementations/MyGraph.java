package implementations;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MyGraph {
    private final Map<Integer, Node> nodes;

    public MyGraph(List<Integer> nodeValues, List<Pair<Integer, Integer>> edges) {
        this.nodes = nodeValues.stream()
                .collect(Collectors.toMap(value -> value, Node::new));

        for (var nodeEdges: edges) {
            nodes.get(nodeEdges.getLeft()).getChildren().add(nodes.get(nodeEdges.getRight()));
        }
    }

    @Data
    public static class Node {
        private int value;
        private boolean visited;
        private boolean marked;
        private List<Node> children;

        public Node(int value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }
}
