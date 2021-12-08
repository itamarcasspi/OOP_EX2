package api;

public class Geo_Location implements GeoLocation{
    double x,y,z;
    public Geo_Location(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Geo_Location(Geo_Location g)
    {
        this.x = g.x;
        this.y = g.y;
        this.z = g.z;
    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt((this.x - g.x())*(this.x - g.x())+(this.y - g.y())*(this.y - g.y())+(this.z - g.z())*(this.z - g.z()));
    }
}
