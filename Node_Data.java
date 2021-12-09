

import java.util.HashMap;
import java.util.Map;

import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

public class Node_Data implements NodeData{
    static int unique_id_generator = 0;
    int id,tag;
    double weight;
    GeoLocation thisLocation;
    String data;

    HashMap<Integer, EdgeData> outEdges; // Integer is the edge's destination
    HashMap<Integer, EdgeData> inEdges;  // Integer is the edge's source

    public Node_Data(GeoLocation n, double w, String d)
    {
        this.thisLocation = new Geo_Location((Geo_Location)n);
        this.weight = w;
        this.data = d;
        this.id = unique_id_generator++;
    }

    public Node_Data(int id, String data) {
        this.id = id;
        String xyz[] = data.split(",");
        double x = Double.parseDouble(xyz[0]);
        double y = Double.parseDouble(xyz[1]);
        double z = Double.parseDouble(xyz[2]);

        thisLocation = new Geo_Location(x, y, z);
        weight = 0;
        data = "";
        tag = 0;

        outEdges = new HashMap<>();
        inEdges = new HashMap<>();
    }

    public Node_Data(NodeData N) {
        this.id = N.getKey();
        this.thisLocation = new Geo_Location(N.getLocation().x(), N.getLocation().y(), N.getLocation().z());
        weight = N.getWeight();
        data = N.getInfo();
        tag = 0;

        outEdges = new HashMap<>();
        inEdges = new HashMap<>();
    }

    @Override
    public HashMap<Integer, EdgeData>getOutEdges() {
        return outEdges;
    }
    @Override
    public HashMap<Integer, EdgeData> getInEdges() {
        return inEdges;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return thisLocation;
    }

    @Override
    public void setLocation(GeoLocation p) {
        thisLocation = new Geo_Location((Geo_Location)p);
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return data;
    }

    @Override
    public void setInfo(String s) {
        this.data = s;
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
