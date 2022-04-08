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
}
