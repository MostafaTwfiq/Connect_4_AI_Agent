package algorithms;

import java.util.Vector;

public class TreeNode {

    long state;
    double val;
    boolean isMaxNode;
    Vector<TreeNode> children = new Vector<>();

    TreeNode(long state, double val, boolean isMaxNode){
        this.state= state;
        this.val = val;
        this.isMaxNode = isMaxNode;
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

    public boolean isMaxNode() {
        return isMaxNode;
    }
}
