

import org.w3c.dom.Node;

import api.EdgeData;

public class Edge_Data implements EdgeData{
    int src,dest,tag;
    double weight;
    String info;

    public Edge_Data(int s,int d,double w)
    {
        this.src = s;
        this.dest = d;
        this.weight = w;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
