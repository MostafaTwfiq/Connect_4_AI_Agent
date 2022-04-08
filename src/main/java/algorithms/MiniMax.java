package algorithms;


import logic.Heuristic;
import logic.Node;
import logic.SlotState;
import logic.StateOperations;

import java.util.List;

public class MiniMax {

    private Node node;

    private int maxDepth;

    public MiniMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Node max(long state, int depth) {

        if(depth == maxDepth) {
            System.out.println("SCORE max: " + Heuristic.score_evaluate(state));
            return new Node(state, Heuristic.score_evaluate(state));
        }

        Node maxNode = new Node(state, Byte.MIN_VALUE);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.AGENT);

        Node node;

        for(long neighbour: neighbours) {
            node = min(neighbour, depth+1);
            System.out.println(node.getScore());
            if(node.getScore() > maxNode.getScore()) {
                System.out.println("hey");
                maxNode.setState(neighbour);
                maxNode.setScore(node.getScore());
            }

        }
//        System.out.println("depth: " + depth);

        if(depth ==  0) {
            System.out.println("max: " + maxNode.getState());
            System.out.println("max: " + maxNode.getScore());
        }
        return maxNode;
    }

    public Node min(long state, int depth) {
        if(depth == maxDepth) {
            System.out.println("SCORE min: " + Heuristic.score_evaluate(state));
            return new Node(state, Heuristic.score_evaluate(state));
        }

        Node minNode = new Node(state, Byte.MAX_VALUE);
        List<Long> neighbours = StateOperations.getStateChildren(state, SlotState.USER);

        Node node;

        for(long neighbour: neighbours) {
            node = max(neighbour, depth+1);

            if(node.getScore() < minNode.getScore()) {

                minNode.setState(neighbour);
                minNode.setScore(node.getScore());
            }

        }

//        System.out.println("depth: " + depth);
//        System.out.println("min: " + minNode.getState());
//        System.out.println("min: " + minNode.getScore());
        return minNode;
    }


}
