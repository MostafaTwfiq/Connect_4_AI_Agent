package algorithms;

import java.util.Vector;

public class TreeNode {

    long state;
    double val;
    Vector<TreeNode> children = new Vector<>();

    TreeNode(long state, double val){
        this.state= state;
        this.val = val;
    }
    public Vector<TreeNode> getChildren(){
        return this.children;
    }

    public double getVal() {
        return val;
    }

    public long getState() {
        return state;
    }

    public void setChildren(Vector<TreeNode> children) {
        this.children = children;
    }

    public void setState(long state) {
        this.state = state;
    }

    public void setVal(double val) {
        this.val = val;
    }
}
