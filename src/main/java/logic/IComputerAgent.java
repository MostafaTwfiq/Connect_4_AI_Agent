package logic;

public interface IComputerAgent {
    public int getNextMove(int playerMove);
    public int getFirstMove();
    public Heuristic evaluateHeuristics();
    public void restart();
    public int getComputerScore();
    public int getPlayerScore();
    public boolean isValidMove(int playerMove);
}
