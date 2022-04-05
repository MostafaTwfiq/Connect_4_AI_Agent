import java.util.Vector;

import java.util.Vector;

public class State {

    public static final int ROW_SIZE=6;
    public  static final int COL_SIZE=7;
    public static final byte agent_slot=10;
    public static final byte user_slot=01;
    public static final byte empty_slot=00;

    private byte[] state;

    private int score;

    public Vector<State> getChildrenStates(){//TODO: implementation

        Vector<State> children = new Vector<>();

        return  children;
    }

    public byte[] getState() {
        return state;
    }

    public void setState(byte[] state) {
        this.state = state;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean isTerminal() {
        return true;//TODO: implementation
    }


}
