package algorithms;
/*
import javafx.util.Pair;
import logic.StateOperations;

public class MinimaxAlphaBeta {

    public long decision(long state){
        var value = maximize(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return value.getKey();
    }

    private Pair<Long, Double> maximize(long state, double alpha, double beta) {
        if (StateOperations.isTerminal())
            return StateOperations.evalu();
        long maxChild = 0;
        double maxUtility = Double.NEGATIVE_INFINITY;

        for (c:StateOperations.getAgentSlots(state)) {
            var value = minimize(state, alpha, beta);
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

    private Pair<Long, Double> minimize(long state, double alpha, double beta) {
    }
}
*/