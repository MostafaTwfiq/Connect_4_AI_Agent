package algorithms;

import logic.*;
public class agent implements IComputerAgent{
    MiniMax minimax;

    int k;
    int level;
    int turns = 0;
    int maxTurns =StateOperations.getColSize()*StateOperations.getRowSize();
    Node currentState;
    long maxRunningTime = Long.MIN_VALUE;

    int maxNodesExpanded = Integer.MIN_VALUE;
    private int compScore = 0;
    private int oppScore = 0;
    private boolean withPruning;

    public agent(boolean withPruning ,int k){
        this.currentState = new Node();
        this.k = k;
        this.level = Math.min(k , maxTurns);
        this.withPruning = withPruning;
        if( !withPruning )
            minimax = new MiniMax(k);
        //else
            //minimax = new MinimaxAlphaBeta(k);
    }

    @Override
    public int getNextMove(int playerMove) {
        /*write code here*/
        return 0;
    }

    @Override
    public int getFirstMove() {
        /*not trusted*/
        turns++;
        currentState.setState(StateOperations.playAtCol(currentState.getState(),StateOperations.getColSize()/2,SlotState.AGENT));
        return StateOperations.getColSize() / 2;

    }

    @Override
    public Heuristic evaluateHeuristics() {
        /* evaluate heuristic of current state*/
        //return this.currentState.getScore();
        return null;
    }

    @Override
    public void restart() {
        this.turns = 0;
        this.currentState = new Node();
        this.compScore = 0;
        this.oppScore = 0;
        this.maxRunningTime = Long.MIN_VALUE;
        this.maxNodesExpanded = Integer.MIN_VALUE;
        this.level = this.k;
    }

    @Override
    public int getComputerScore() {
        return this.compScore;
    }

    @Override
    public int getPlayerScore() {
        return this.oppScore;
    }

    @Override
    public boolean isValidMove(int playerMove) {
        if(playerMove >= 0  && playerMove <= 6 /*&& check no agent slot or opp slot*/)
            return true;
        return false;
    }
}
