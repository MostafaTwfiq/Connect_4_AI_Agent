package algorithms;


import logic.Heuristic;
import logic.Node;
import logic.SlotState;
import logic.StateOperations;

import java.util.List;

public class MiniMax {

    private Node root;
    private Node node;

    private final int maxDepth;

    public Node getTree() {
        return root;
    }

    public MiniMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Node max(long state, int depth) {

        root.setState(state);

        if (depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node maxNode = new Node(state, Float.NEGATIVE_INFINITY);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.AGENT);

        Node node;

        for (long neighbour : neighbours) {
            node = min(neighbour, depth + 1);

            root.addChild(node);

            if (node.getScore() > maxNode.getScore()) {
                maxNode.setState(neighbour);
                maxNode.setScore(node.getScore());
            }

        }

        root.setState(maxNode.getState());

        return maxNode;
    }

    public Node min(long state, int depth) {
        root.setState(state);
        if (depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node minNode = new Node(state, Float.POSITIVE_INFINITY);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.USER);

        Node node;

        for (long neighbour : neighbours) {
            node = max(neighbour, depth + 1);
            root.addChild(node);
            //System.out.println("min: " + node.getScore());
            //StateOperations.printState(node.getState());
            if (node.getScore() < minNode.getScore()) {
                minNode.setState(neighbour);
                minNode.setScore(node.getScore());
            }

        }

        root.setScore(minNode.getScore());
        return minNode;
    }


}