package logic;

import java.util.ArrayList;
import java.util.List;

public class Node<P> {
    long state;
    float score;
    private List<Node> children;


    public Node(long state, float score) {
        this.state = state;
        this.score = score;
        children = new ArrayList<>();
    }

    public void addChild(Node child) {
        children.add(child);
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