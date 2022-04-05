import java.util.ArrayList;
import java.util.Vector;

public class MiniMax {

    private State sol;

    public State max(State state) {

        if(state.isTerminal()) {
            state.setScore(Heuristic.score_evaluate(state.getState()));
        }

        int maxScore = Integer.MIN_VALUE;
        State child;

        Vector<State> neighbours = state.getChildrenStates();

        for(State neighbour: neighbours) {
            child = min(neighbour);

            if(child.getScore() > maxScore) {
                sol = state;
            }

        }

        return sol;
    }

    public State min(State state) {

        if(state.isTerminal()) {
            state.setScore(Heuristic.score_evaluate(state.getState()));
        }

        int minScore = Integer.MAX_VALUE;
        State child;

        Vector<State> neighbours = state.getChildrenStates();

        for(State neighbour: neighbours) {
            child = max(neighbour);

            if(child.getScore() < minScore) {
                sol = state;
            }

        }

        return sol;
    }


}
