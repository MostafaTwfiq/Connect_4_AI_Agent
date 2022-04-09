package algorithms;


import javafx.util.Pair;
import logic.Heuristic;
import logic.SlotState;
import logic.StateOperations;

public class MiniMax {


    public static TreeNode root;
    private static int maxDepth = 7;

    public static Pair<Long, TreeNode> decision(long state){
        root = new TreeNode(state, 0, true);
        var value = max(state, root, 0);
        root.val = value.getValue();
        Pair<Long, TreeNode> val = new Pair<>(value.getKey(), root);
        return val;
    }

    private static Pair<Long, Double> max(long state, TreeNode node, int depth) {

        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth)
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));

        long maxChild = 0;
        double maxUtility = Double.NEGATIVE_INFINITY;

        for (var neighbour : StateOperations.getStateChildren(state, SlotState.AGENT)) {
            var nodec = new TreeNode(neighbour,0, false);
            node.children.add(nodec);
            var value = min(neighbour,nodec ,depth + 1);
            var utility = value.getValue();
            nodec.val = utility;
           if (utility > maxUtility){
               maxChild = neighbour;
               maxUtility = utility;
           }

        }

        return new Pair<Long, Double>(maxChild, maxUtility);
    }

    private static Pair<Long, Double> min(long state, TreeNode node, int depth) {
        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth)
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));
        long minChild = 0;
        double minUtility = Double.POSITIVE_INFINITY;

        for (long neighbour : StateOperations.getStateChildren(state, SlotState.USER)) {
            var nodec = new TreeNode(neighbour,0, true);
            var value = max(neighbour, nodec,  depth+1);
            var utility = value.getValue();
            nodec.val = utility;
            node.children.add(nodec);
            if (utility < minUtility){
                minChild = neighbour;
                minUtility = utility;
            }

        }
        return new Pair<Long, Double>(minChild, minUtility);
    }


}