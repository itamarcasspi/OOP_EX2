package api;

import java.util.List;
import java.util.Queue;

public class ComparableNode implements Comparable<ComparableNode>{
    NodeData node;
    double val;
    public ComparableNode(NodeData n,double v)
    {
        node = n;
        val = v;
    }

    public void setVal(int v)
    {
        val = v;
    }

    @Override
    public int compareTo(ComparableNode n) {
        if(this.val>n.val)
        {
            return 1;
        }
        else if(this.val < n.val)
        {
            return -1;
        }
        return 0;
    }
}
