package algorithms;


import logic.Heuristic;
import logic.Node;
import logic.SlotState;
import logic.StateOperations;

import java.util.List;

public class MiniMax {

    private Node node;

    private final int maxDepth;

    public MiniMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Node max(long state, int depth) {

        if (depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node maxNode = new Node(state, Float.NEGATIVE_INFINITY);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.AGENT);

        Node node;

        for (long neighbour : neighbours) {
            node = min(neighbour, depth + 1);
            //System.out.println("max: " + node.getScore());
            //StateOperations.printState(node.getState());
            if (node.getScore() > maxNode.getScore()) {
                maxNode.setState(neighbour);
                maxNode.setScore(node.getScore());
            }

        }

        return maxNode;
    }

    public Node min(long state, int depth) {
        if (depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node minNode = new Node(state, Float.POSITIVE_INFINITY);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.USER);

        Node node;

        for (long neighbour : neighbours) {
            node = max(neighbour, depth + 1);
            //System.out.println("min: " + node.getScore());
            //StateOperations.printState(node.getState());
            if (node.getScore() < minNode.getScore()) {
                minNode.setState(neighbour);
                minNode.setScore(node.getScore());
            }

        }
        return minNode;
    }


}
