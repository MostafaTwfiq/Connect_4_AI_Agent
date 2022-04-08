package logic;

public class Node {
    long state;
    float score;

    public Node(long state, float score) {
        this.state = state;
        this.score = score;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}