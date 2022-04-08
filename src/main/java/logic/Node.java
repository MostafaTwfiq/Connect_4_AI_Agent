package logic;

public class Node {
    long state;
    byte score;

    public Node(long state, byte score) {
        this.state = state;
        this.score = score;
    }

    public Node() {
        this.state = state;
        this.score=score;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }
}