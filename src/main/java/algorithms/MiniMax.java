package algorithms;


import logic.Heuristic;
import logic.SlotState;
import logic.StateOperations;

import java.util.List;

public class MiniMax {

    public class Node {
        long state;
        int score;

        public Node(long state, int score) {
            this.state = state;
            this.score = score;
        }

        public long getState() {
            return state;
        }

        public void setState(long state) {
            this.state = state;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    private int maxDepth;

    public MiniMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Node max(long state, int depth) {

        if(depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node maxNode = new Node(state, Integer.MIN_VALUE);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.AGENT);

        Node node;

        for(long neighbour: neighbours) {
            node = min(neighbour, depth+1);

            if(node.score > maxNode.score) {
                maxNode.state = neighbour;
                maxNode.score = node.score;
            }

        }

        return maxNode;
    }

    public Node min(long state, int depth) {

        if(depth == maxDepth) {
            return new Node(state, Heuristic.getStateScore(state));
        }

        Node minNode = new Node(state, Integer.MIN_VALUE);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.AGENT);

        Node node;

        for(long neighbour: neighbours) {
            node = max(neighbour, depth+1);

            if(node.score < minNode.score) {
                minNode.state = neighbour;
                minNode.score = node.score;
            }

        }

        return minNode;
    }


}
