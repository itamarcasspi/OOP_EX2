# oop-ex2
 
In this project we have implemented API's for a Directed and Weighted graph, and a GUI.
The API's that we have implemented are:

-GeoLocation
  Interface that represent a location in a 3d space.
-NodeData
  Interface that represent a node, that has a unique ID and a GeoLocation.
-EdgeData
  Interface that represents a weighted edge from one node to another.
-DirectedWeightedGraph
  Interface that represets the whole graph, that has any number of node and edges.
-DirectedWeightedGraphAlgorithms
  Interface that represents the whole graph and allow for several algorithms to be used.


The algorithms that we have implemented in the project are the following:
-NodeData Center():
  Returns the NodeData which minimizes the max distance to all the other nodes, assuming the graph isConnected.
-boolean isConnected():
  Returns true if there is a path between all nodes.
-double shortestPathDist(NodeData A, NodeData B)
  Returns the minimal total weight of a path between node A and B.
-List<NodeData> shortestPathDist(NodeData A, NodeData B)
  Returns a list of nodes that represent the shortest path between two nodes.
 -List<NodeData> tsp(List<NodeData> cities)
   Returns a list of nodes that represent the shortest path that contains all the nodes in the list "cities".
   A variation of the known The Traveling Salesman algorithm.
  
