package algorithms;

import javafx.util.Pair;
import logic.Heuristic;
import logic.Node;
import logic.SlotState;
import logic.StateOperations;

import java.util.HashMap;
import java.util.Vector;

public class MinimaxAlphaBeta {

    static int maxDepth = 10;
    static TreeNode root = null;
    public static Pair<Long, TreeNode> decision(long state){
        root = new TreeNode(state, 0, true);
        var value = maximize(state, root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        double boardFullRatio = Heuristic.getBoardFullRatio(state);
        boardFullRatio = Math.max(boardFullRatio, 0.33);
        maxDepth = (int) Math.floor(Heuristic.map(boardFullRatio, 0.33, 1, 10, 17));
        root.val = value.getValue();
        Pair<Long, TreeNode> val = new Pair<>(value.getKey(), root);
        return val;
    }

    private static Pair<Long, Double> maximize(long state, TreeNode node, double alpha, double beta, int depth) {
        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth )
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));

        long maxChild = 0;
        double maxUtility = Double.NEGATIVE_INFINITY;

        for (var c : StateOperations.getStateChildren(state, SlotState.AGENT)) {
            var nodec = new TreeNode(c,0, false);
            node.children.add(nodec);
            var value = minimize(c, nodec, alpha, beta, depth+1);
            var utility = value.getValue();
            nodec.val = utility;
            if (utility > maxUtility){
                maxChild = c;
                maxUtility = utility;
            }
            if (maxUtility >= beta)
                break;
            if (maxUtility > alpha)
                alpha = maxUtility;
        }

        return new Pair<Long, Double>(maxChild, maxUtility);


    }

    private static Pair<Long, Double> minimize(long state, TreeNode node, double alpha, double beta, int depth) {
        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth)
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));
        long minChild = 0;
        double minUtility = Double.POSITIVE_INFINITY;

        for (var c : StateOperations.getStateChildren(state, SlotState.USER)) {
            var nodec = new TreeNode(c,0, true);
            node.children.add(nodec);
            var value = maximize(c, nodec, alpha, beta, depth+1);
            var utility = value.getValue();
            nodec.val = utility;
            if (utility < minUtility){
                minChild = c;
                minUtility = utility;
            }
            if (minUtility <= alpha)
                break;
            if (minUtility < beta)
                beta = minUtility;
        }
        return new Pair<Long, Double>(minChild, minUtility);
        
    }
}