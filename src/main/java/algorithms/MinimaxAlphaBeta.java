package algorithms;

import javafx.util.Pair;
import logic.Heuristic;
import logic.SlotState;
import logic.StateOperations;

public class MinimaxAlphaBeta {

    static int maxDepth = 5;
    public static long decision(long state){
        var value = maximize(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        return value.getKey();
    }

    private static Pair<Long, Double> maximize(long state, double alpha, double beta, int depth) {
        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth )
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));
        long maxChild = 0;
        double maxUtility = Double.NEGATIVE_INFINITY;

        for (var c : StateOperations.getStateChildren(state, SlotState.AGENT)) {
            var value = minimize(state, alpha, beta, depth+1);
            var utility = value.getValue();
            if (utility > maxUtility){
                maxChild = value.getKey();
                maxUtility = value.getValue();
            }
            if (maxUtility >= beta)
                break;
            if (maxUtility > alpha)
                alpha = maxUtility;
        }

        return new Pair<Long, Double>(maxChild, maxUtility);


    }

    private static Pair<Long, Double> minimize(long state, double alpha, double beta, int depth) {
        if (StateOperations.getEmptySlotsCount(state) == 0 || depth >= maxDepth )
            return new Pair<Long, Double>(null , (double) Heuristic.getStateScore(state));
        long minChild = 0;
        double minUtility = Double.POSITIVE_INFINITY;

        for (var c : StateOperations.getStateChildren(state, SlotState.USER)) {
            var value = maximize(state, alpha, beta, depth+1);
            var utility = value.getValue();
            if (utility < minUtility){
                minChild = value.getKey();
                minUtility = value.getValue();
            }
            if (minUtility <= alpha)
                break;
            if (minUtility < beta)
                beta = minUtility;
        }
        return new Pair<Long, Double>(minChild, minUtility);
        
    }
}
