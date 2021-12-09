import api.EdgeData;
import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;


class Edge_DataTest {

    EdgeData a0_a1 = new Edge_Data(0,1,5);
    EdgeData a1_a2 = new Edge_Data(1,2,7);
    EdgeData a2_a3 = new Edge_Data(2,3,8);
    EdgeData a3_a4 = new Edge_Data(3,4,11);

    @org.junit.jupiter.api.Test
    void getSrc() {
        Assert.assertEquals(a0_a1.getSrc(),0);
        Assert.assertEquals(a1_a2.getSrc(),1);
        Assert.assertEquals(a2_a3.getSrc(),2);
        Assert.assertEquals(a3_a4.getSrc(),3);
    }

    @org.junit.jupiter.api.Test
    void getDest() {
        Assert.assertEquals(a0_a1.getDest(),1);
        Assert.assertEquals(a1_a2.getDest(),2);
        Assert.assertEquals(a2_a3.getDest(),3);
        Assert.assertEquals(a3_a4.getDest(),4);
    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        Assert.assertEquals(a0_a1.getWeight(),5,0.01);
        Assert.assertEquals(a1_a2.getWeight(),7,0.01);
        Assert.assertEquals(a2_a3.getWeight(),8,0.01);
        Assert.assertEquals(a3_a4.getWeight(),11,0.01);
    }

    @org.junit.jupiter.api.Test
    void setInfo() {
        a0_a1.setInfo("a0_a1");
        a1_a2.setInfo("a1_a2");
        a2_a3.setInfo("a2_a3");
        a3_a4.setInfo("a3_a4");
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        a0_a1.setInfo("a0_a1");
        a1_a2.setInfo("a1_a2");
        a2_a3.setInfo("a2_a3");
        a3_a4.setInfo("a3_a4");
        Assert.assertEquals(a0_a1.getInfo(), "a0_a1");
        Assert.assertEquals(a1_a2.getInfo(),"a1_a2");
        Assert.assertEquals(a2_a3.getInfo(),"a2_a3");
        Assert.assertEquals(a3_a4.getInfo(),"a3_a4");
    }

    @org.junit.jupiter.api.Test
    void setTag() {
        a0_a1.setTag(0);
        a1_a2.setTag(1);
        a2_a3.setTag(2);
        a3_a4.setTag(3);
    }

    @org.junit.jupiter.api.Test
    void getTag() {
        a0_a1.setTag(0);
        a1_a2.setTag(1);
        a2_a3.setTag(2);
        a3_a4.setTag(3);
        assertEquals(a0_a1.getTag(), 0);
        assertEquals(a1_a2.getTag(),1);
        assertEquals(a2_a3.getTag(),2);
        assertEquals(a3_a4.getTag(),3);
    }


}