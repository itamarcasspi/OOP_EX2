

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

public class Directed_Weighted_Graph implements DirectedWeightedGraph {
    HashMap<Integer, NodeData> nodesMap;
    HashMap<Integer, HashMap<Integer, EdgeData>> edgeMap;
    HashMap<Integer, HashMap<Integer, EdgeData>> destToSource;
    int edges_size;
    int mc;

    public Directed_Weighted_Graph() {
        nodesMap = new HashMap<Integer, NodeData>();
        edgeMap = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        destToSource = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        mc = 0;
        edges_size=0;
    }

    public Directed_Weighted_Graph(Directed_Weighted_Graph g) {
        this.nodesMap = new HashMap<Integer, NodeData>();
        for (Map.Entry<Integer, NodeData> entry : g.nodesMap.entrySet()) {
            this.nodesMap.put(entry.getKey(), entry.getValue());
        }

        this.destToSource = new HashMap<>();
        for (Map.Entry<Integer, HashMap<Integer, EdgeData>> a : g.destToSource.entrySet()) {
            HashMap<Integer,EdgeData> temp = new HashMap<>();
            this.destToSource.put(a.getKey(),temp);
            for (Map.Entry<Integer, EdgeData> b : a.getValue().entrySet()) {
                this.destToSource.get(a.getKey()).put(b.getKey(), b.getValue());
            }
        }

        this.edgeMap = new HashMap<>();
        for (Map.Entry<Integer, HashMap<Integer, EdgeData>> a : g.edgeMap.entrySet()) {
            HashMap<Integer,EdgeData> temp = new HashMap<>();
            this.edgeMap.put(a.getKey(),temp);
            for (Map.Entry<Integer, EdgeData> b : a.getValue().entrySet()) {
                this.edgeMap.get(a.getKey()).put(b.getKey(), b.getValue());
            }
        }
        this.mc = g.mc;
        edges_size = g.edges_size;

    }

    @Override
    public NodeData getNode(int key) {
        return nodesMap.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {

        return edgeMap.get(src).get(dest);
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
        if (edgeMap.containsKey(src)) {
            if (edgeMap.get(src).containsKey(dest))
            {
                return;
            }
            if (!edgeMap.get(src).containsKey(dest)) {
                EdgeData edge = new Edge_Data(src, dest, w);
                edgeMap.get(src).put(dest, edge);
                mc++;
                edges_size++;
            }
        } else {
            mc++;
            edges_size++;
            EdgeData edge = new Edge_Data(src, dest, w);
            HashMap<Integer, EdgeData> dstMap = new HashMap<Integer, EdgeData>();
            edgeMap.put(src, dstMap);
            edgeMap.get(src).put(dest, edge);
        }
        //now we add to the hashmap that contains edges from dest to src
        if (destToSource.containsKey(dest)) {
            if (!destToSource.get(dest).containsKey(src)) {
                EdgeData edge = new Edge_Data(src, dest, w);
                destToSource.get(dest).put(src, edge);
            }
        } else {
            EdgeData edge = new Edge_Data(src, dest, w);
            HashMap<Integer, EdgeData> dstMap = new HashMap<Integer, EdgeData>();
            destToSource.put(dest, dstMap);
            destToSource.get(dest).put(src, edge);
        }
    }
//Creating the node iterator
    @Override
    public Iterator<NodeData> nodeIter() {
        return new NodeIterator(getMC());
    }
    class NodeIterator implements Iterator<NodeData>{
        int mc_at_init;
        Iterator<NodeData> it;

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
    //creating the entire edge iterator
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new AllEdgeIterator(getMC());
    }
    class AllEdgeIterator implements Iterator
    {
        int mc_at_init;
        Iterator<HashMap<Integer,EdgeData>> it_src;
        Iterator<EdgeData> edge_it;

        public AllEdgeIterator(int mc)
        {
            mc_at_init = mc;
            it_src = edgeMap.values().iterator();
            edge_it = it_src.next().values().iterator();
        }

        @Override
        public boolean hasNext() {
            if(mc_at_init!=getMC())
            {
                throw new RuntimeException();
            }
            if(!edge_it.hasNext())
            {
                if(!it_src.hasNext())
                {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object next() {
            if(mc_at_init!=getMC())
            {
                throw new RuntimeException();
            }
            if(!edge_it.hasNext())
            {
                if(it_src.hasNext()) {
                    edge_it = it_src.next().values().iterator();
                    return edge_it.next();
                }
            }
            return edge_it.next();
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new EdgeIterator(getMC(),node_id);
    }
    class EdgeIterator implements Iterator {
        int mc_at_init;
        Iterator<EdgeData> edge_it;

        public EdgeIterator(int mc, int src) {

            mc_at_init = mc;
            if(edgeMap.get(src)==null || edgeMap.get(src).size()==0)
            {
                edge_it = null;
            }
            else
            {
                edge_it = edgeMap.get(src).values().iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (mc_at_init != getMC()) {
                throw new RuntimeException();
            }
            if(edge_it == null)
            {
                return false;
            }
            return edge_it.hasNext();
        }

        @Override
        public Object next() {
            if (mc_at_init != getMC()) {
                throw new RuntimeException();
            }
            return edge_it.next();
        }
    }
    //remove node complexity is as the number of its neighbors- we delete the edges that the node is the src and dst
    @Override
    public NodeData removeNode(int key) {
        if (!nodesMap.containsKey(key)) {
            return null;
        }
        mc++;

        NodeData removed = nodesMap.remove(key);
        //remove the edges that our node is the destination of.
        for (Map.Entry<Integer, EdgeData> src : destToSource.get(key).entrySet()) {
            edgeMap.get(src.getKey()).remove(key);
            edges_size--;
            if(edgeMap.get(src.getKey()).size()==0)
            {
                edgeMap.remove(src.getKey());
            }
        }

        //remove the edges that our node is the source of.
        edges_size -= edgeMap.get(key).size();
        edgeMap.remove(key);

        return removed;

    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (edgeMap.containsKey(src)) {
            if (edgeMap.get(src).containsKey(dest)) {
                mc++;
                edges_size--;
                EdgeData removed = edgeMap.get(src).remove(dest);
                if(edgeMap.get(src).size()==0)
                {
                    edgeMap.remove(src);
                }
                if(destToSource.containsKey(dest))
                {
                    destToSource.get(dest).remove(src);
                }
            }

        }

        return null;
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
    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdgeMap()
    {
        return edgeMap;
    }
}
