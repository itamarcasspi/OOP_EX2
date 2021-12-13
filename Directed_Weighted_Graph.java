

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import api.lib;

import static api.lib.parseJSONFile;


public class Directed_Weighted_Graph implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodesMap;
    HashMap<Integer, EdgeData> edgeMap;

    int edges_size;
    int mc;

    public Directed_Weighted_Graph() {
        nodesMap = new HashMap<Integer, NodeData>();
        edgeMap = new HashMap<Integer, EdgeData>();

        mc = 0;
        edges_size=0;
    }

    public Directed_Weighted_Graph(Directed_Weighted_Graph g) {
        nodesMap = new HashMap<>();
        edgeMap = new HashMap<>();
        edges_size = g.edges_size;
        mc = g.mc;
        /** Go through all the nodes in g and create a new node that copies the node in g **/
        Iterator<NodeData> NodeI = g.nodeIter();
        while(NodeI.hasNext()) {
            NodeData currNode = NodeI.next();
            nodesMap.put(currNode.getKey(), new Node_Data(currNode));
        }

        Iterator<EdgeData> EdgeI = g.edgeIter();
        while(EdgeI.hasNext()) {
            EdgeData currEdge = EdgeI.next();
            Edge_Data newEdge = new Edge_Data(currEdge);

            /**Three hashmaps that hold edges**/
            nodesMap.get(newEdge.getSrc()).getOutEdges().put(newEdge.getDest(), newEdge);
            nodesMap.get(newEdge.getDest()).getInEdges().put(newEdge.getSrc(), newEdge);
            edgeMap.put(newEdge.getId(), newEdge);

        }
    }

    public Directed_Weighted_Graph(String jsonFileName) {
        nodesMap = new HashMap<>();
        edgeMap = new HashMap<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = parseJSONFile(jsonFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonNodes = jsonObject.getJSONArray("Nodes");
        for(int i=0;i<jsonNodes.length();i++){
            int key=jsonNodes.getJSONObject(i).getInt("id");
            String pos=jsonNodes.getJSONObject(i).getString("pos");
            Node_Data v = new Node_Data(key, pos);
            nodesMap.put(v.getKey(), v);
        }

        JSONArray jsonEdges = jsonObject.getJSONArray("Edges");
        for (int i = 0; i < jsonEdges.length(); i++) {
            int src = jsonEdges.getJSONObject(i).getInt("src");
            int dest = jsonEdges.getJSONObject(i).getInt("dest");
            double w = jsonEdges.getJSONObject(i).getDouble("w");

            Edge_Data e = new Edge_Data(src, dest, w, edges_size);
            edges_size++;

            /**Three hashmaps that hold edges**/
            nodesMap.get(e.getSrc()).getOutEdges().put(e.getDest(), e);
            nodesMap.get(e.getDest()).getInEdges().put(e.getSrc(), e);
            edgeMap.put(e.id, e);
        }
    }

    @Override
    public NodeData getNode(int key) {
        return nodesMap.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest)
    {
        if(nodesMap.containsKey(dest)) {
            if (nodesMap.get(dest).getInEdges().containsKey(src)) {
                return nodesMap.get(dest).getInEdges().get(src);
            }
        }
        return null;
    }

    @Override
    public void addNode(NodeData n) {
        if (nodesMap.containsKey(n.getKey())) {
            //if node already registered, do nothing
            return;
        }
        //else
        mc++;
        nodesMap.put(n.getKey(), n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(nodesMap.containsKey(src)&&nodesMap.containsKey(dest)){
            Edge_Data edgeData=new Edge_Data(src,dest,w, edges_size);
            edges_size++;
            mc++;
            nodesMap.get(src).getOutEdges().put(dest,edgeData);
            nodesMap.get(dest).getInEdges().put(src,edgeData);
            edgeMap.put(edgeData.getId(),edgeData);
        } //MAYBE ADD SOMETHING THAT CHECKS IF THE EDGE ALREADY EXISTS WITH THE SAME WEIGHT
          //OR MAYBE ITS OKAY TO HAVE DUPLICATES AND WE NEED TO ADD THE EDGE ANYWAY
    }

//Creating the node iterator
    @Override
    public Iterator<NodeData> nodeIter() {
        return new NodeIterator(getMC());
    }
    class NodeIterator implements Iterator<NodeData>{
        private int mc_at_init;
        private Iterator<NodeData> it;

        public NodeIterator(int mc)
        {
            mc_at_init = mc;
            it = nodesMap.values().iterator();
        }
        @Override
        public boolean hasNext() {
            if(mc_at_init!=getMC())
            {
                throw new RuntimeException();
            }
            return it.hasNext();
        }

        @Override
        public NodeData next() {
            if(mc_at_init!=getMC())
            {
                throw new RuntimeException();
            }
            return it.next();
        }
    }
    // //creating the entire edge iterator
    // @Override
    // public Iterator<EdgeData> edgeIter() {
    //     return new AllEdgeIterator(getMC());
    // }
    // class AllEdgeIterator implements Iterator
    // {
    //     int mc_at_init;
    //     Iterator<HashMap<Integer,EdgeData>> it_src;
    //     Iterator<EdgeData> edge_it;

    //     public AllEdgeIterator(int mc)
    //     {
    //         mc_at_init = mc;
    //         it_src = edgeMap.values().iterator();
    //         edge_it = it_src.next().values().iterator();
    //     }

    //     @Override
    //     public boolean hasNext() {
    //         if(mc_at_init!=getMC())
    //         {
    //             throw new RuntimeException();
    //         }
    //         if(!edge_it.hasNext())
    //         {
    //             if(!it_src.hasNext())
    //             {
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }

    //     @Override
    //     public Object next() {
    //         if(mc_at_init!=getMC())
    //         {
    //             throw new RuntimeException();
    //         }
    //         if(!edge_it.hasNext())
    //         {
    //             if(it_src.hasNext()) {
    //                 edge_it = it_src.next().values().iterator();
    //                 return edge_it.next();
    //             }
    //         }
    //         return edge_it.next();
    //     }
    // }

    // @Override
    // public Iterator<EdgeData> edgeIter(int node_id) {
    //     return new EdgeIterator(getMC(),node_id);
    // }
    // class EdgeIterator implements Iterator {
    //     int mc_at_init;
    //     Iterator<EdgeData> edge_it;

    //     public EdgeIterator(int mc, int src) {

    //         mc_at_init = mc;
    //         if(edgeMap.get(src)==null || edgeMap.get(src).size()==0)
    //         {
    //             edge_it = null;
    //         }
    //         else
    //         {
    //             edge_it = edgeMap.get(src).values().iterator();
    //         }
    //     }

    //     @Override
    //     public boolean hasNext() {
    //         if (mc_at_init != getMC()) {
    //             throw new RuntimeException();
    //         }
    //         if(edge_it == null)
    //         {
    //             return false;
    //         }
    //         return edge_it.hasNext();
    //     }

    //     @Override
    //     public Object next() {
    //         if (mc_at_init != getMC()) {
    //             throw new RuntimeException();
    //         }
    //         return edge_it.next();
    //     }
    // }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new EdgeIter(mc);
    }

    /**
     * Returns the iterator for the out edge's hashmap,
     * this hashmap is stored inside the Node's object.
     * @param node_id - the ID of the node that you want to access it's out edges hashmap.
     * @return Iterator - the iterator itself.
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new outEdgesIter(node_id,this.mc); //probably not the solution but maybe it is
    }

    //remove node complexity is as the number of its neighbors- we delete the edges that the node is the src and dst
    @Override
    public NodeData removeNode(int key) {

        if (nodesMap.containsKey(key)) {
            Object[] inedges = nodesMap.get(key).getInEdges().keySet().toArray();      //keyset is O(1)
            Object[] outedges = nodesMap.get(key).getOutEdges().keySet().toArray();    //keyset is O(1)
            int[] inIds = new int[inedges.length];
            int[] outIds = new int[outedges.length];

            /**SIZE OF inIds+outIds is equal to v.degree**/
            /** O(3*v.degree)=O(v.degree)**/
            for (int i = 0; i < inIds.length; i++) {
                inIds[i] = this.getEdge((int) inedges[i], key).getId();
            }
            for (int i = 0; i < outIds.length; i++) {
                outIds[i] = this.getEdge(key, (int) outedges[i]).getId();
            }

            for (int i = 0; i < inIds.length; i++) {
                this.removeEdge((int)inedges[i],key);

            }
            for (int i = 0; i < outIds.length; i++) {
                this.removeEdge(key,(int)outedges[i]);

            }

            nodesMap.get(key).getOutEdges().clear();       //Clear is O(n) where n is the number of out edges
            nodesMap.get(key).getInEdges().clear();        //Clear is O(n) where n is the number of in edges
            edgeMap.remove(key);
            mc++;
            return nodesMap.remove(key);
        } else return null;
    }


    private class outEdgesIter implements Iterator<EdgeData> {
        private int mc_at_init;
        private Iterator<EdgeData> Iter;
        private EdgeData currEdge;
        int key;
        public outEdgesIter(int NodeKey,int _mc) {
            mc_at_init = _mc;
            Iter = nodesMap.get(NodeKey).getOutEdges().values().iterator();
            key = NodeKey;
        }

        @Override
        public boolean hasNext() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            return Iter.hasNext();
        }

        @Override
        public EdgeData next() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            currEdge = Iter.next();
            return currEdge;
        }

        @Override
        public void remove() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            if (!Iter.hasNext()) return;
            EdgeData tempEdge = Iter.next();
            EdgeData nextEdge;
            removeEdge(currEdge.getId(),0);
            Iter = nodesMap.get(key).getOutEdges().values().iterator();
            nextEdge = tempEdge;
            while (nextEdge!=tempEdge) {
                nextEdge=Iter.next();
            }
            currEdge = tempEdge;
        }

        @Override
        public void forEachRemaining(Consumer<? super EdgeData> action) {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            Iterator.super.forEachRemaining(action);
        }
    }


    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (nodesMap.containsKey(src)&&nodesMap.containsKey(dest)&&nodesMap.get(src).getOutEdges().containsKey(dest)) {

        int id = nodesMap.get(dest).getInEdges().get(src).getId();
        // could also be nodes.get(src).getOutEdges().get(dest).getId();
        edgeMap.remove(id);
        nodesMap.get(dest).getInEdges().remove(src);
        mc++;
        edges_size--;
        return nodesMap.get(src).getOutEdges().remove(dest);
        } else return null;
    }

    private class EdgeIter implements Iterator<EdgeData> {
        private int mc_at_init;
        private EdgeData currEdge;
        private Iterator<EdgeData> Iter;
        public EdgeIter(int _mc) {
            mc_at_init = _mc;
            Iter = edgeMap.values().iterator();
        }

        @Override
        public boolean hasNext() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            return Iter.hasNext();
        }

        @Override
        public EdgeData next() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            currEdge = Iter.next();
            return currEdge;
        }

        @Override
        public void remove() {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            EdgeData tempEdge = Iter.next();
            EdgeData nextEdge;
            removeEdge(currEdge.getId(),0);
            Iter = edgeMap.values().iterator();
            nextEdge = tempEdge;
            while (nextEdge!=tempEdge) {
                nextEdge=Iter.next();
            }
            currEdge = tempEdge;
        }

        @Override
        public void forEachRemaining(Consumer<? super EdgeData> action) {
            if(mc_at_init != mc)
            {
                throw new RuntimeException();
            }
            Iterator.super.forEachRemaining(action);
        }
    }

    @Override
    public int nodeSize() {
        return nodesMap.size();
    }

    @Override
    public int edgeSize() {
        return edges_size;
    }

    @Override
    public int getMC() {
        return mc;
    }

    public HashMap<Integer,NodeData> getNodesMap()
    {
        return nodesMap;
    }
    public HashMap<Integer, EdgeData> getEdgeMap()
    {
        return edgeMap;
    }
}
