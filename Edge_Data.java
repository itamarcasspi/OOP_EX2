
import org.w3c.dom.Node;

import api.EdgeData;

public class Edge_Data implements EdgeData {
    int src, dest, tag, id;
    double weight;
    String info;

    public Edge_Data(int s, int d, double w) {
        this.src = s;
        this.dest = d;
        this.weight = w;
    }

    public Edge_Data(int s, int d, double w, int id) {
        this.src = s;
        this.dest = d;
        this.weight = w;
        this.id = id;
        this.info = " ";
        this.tag = 0;
    }

    public Edge_Data(EdgeData E) {
        this.src = E.getSrc();
        this.dest = E.getDest();
        this.weight = E.getWeight();
        this.info = E.getInfo();
        this.tag = 0;
        this.id = E.getId();
    }


    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getId() {
        return id;
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

    public boolean isEqual(EdgeData e)
    {
        if(this.dest != e.getDest())
        {
            return false;
        }
        if(this.src != e.getSrc())
        {
            return false;
        }
        if(this.weight != e.getWeight())
        {
            return false;
        }
        return true;
    }

    public void print()
    {
        System.out.println(src+"-["+weight+"]->"+dest);
    }

    @Override
    public String toString() {
        return "Edge_Data{" +
                "src=" + src +
                ", dest=" + dest +
                ", id=" + id +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                '}';
    }
}
