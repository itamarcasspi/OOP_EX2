

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

import api.GeoLocation;
import api.NodeData;

public class Node_Data implements NodeData{
    static int unique_id_generator = 0;
    int id,tag;
    double weight;
    GeoLocation thisLocation;
    String data;
    public Node_Data(GeoLocation n, double w, String d)
    {
        this.thisLocation = new Geo_Location((Geo_Location)n);
        this.weight = w;
        this.data = d;
        this.id = unique_id_generator++;
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
