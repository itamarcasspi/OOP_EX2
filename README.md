
# oop-ex2
 
In this project we have implemented API's for a Directed and Weighted graph, and a GUI.
The API's that we have implemented are:

- GeoLocation

  Interface that represent a location in a 3d space using 3 double type variables.

- NodeData

  Interface that represent a node, that has a unique ID and a GeoLocation.
  In the class that implements the NodeData interface, we hold two hashmaps, one for all the edges coming into the nodes and one for the edges coming out of it.
- EdgeData

  Interface that represents a weighted edge from one node to another.
  Holds the id of the source node and the id of the destination node.

- DirectedWeightedGraph

  Interface that represets the whole graph, that has any number of node and edges.
  Holds the NodeData of the graph inside a hashmap, and a hashmap of every edge.
- DirectedWeightedGraphAlgorithms
  Interface that represents the whole graph and allow for several algorithms to be used.


The algorithms that we have implemented in the project are the following:
- NodeData Center():
  Returns the NodeData which minimizes the max distance to all the other nodes, assuming the graph isConnected.
- boolean isConnected():
  Returns true if there is a path between all nodes.
- double shortestPathDist(NodeData A, NodeData B)
  Returns the minimal total weight of a path between node A and B.
- List<NodeData> shortestPathDist(NodeData A, NodeData B)
  Returns a list of nodes that represent the shortest path between two nodes.
- List<NodeData> tsp(List<NodeData> cities)
   Returns a list of nodes that represent the shortest path that contains all the nodes in the list "cities".
   A variation of the known The Traveling Salesman algorithm.
  


Short alysis of the program, using large graphs:
1k graph:

  load() = 100ms, isConnected() = 420ms , shortest() = 430ms, center() = 16sec, 738ms

 10k graph:

  load() = 820ms , isConnected() = 1 sec, 310ms , shortest() = 1sec, 820ms

 100k:

  load() = 23sec, 770ms

#### DirectedWeightedGraphAlgorithms interface:
The main iterface of the project, holds the algorithms which operate on the graph.


Inits the graph on which this set of algorithms operates on.

- public void init(DirectedWeightedGraph g);


Returns the underlying graph of which this class works.

- public DirectedWeightedGraph getGraph();


Computes a deep copy of this weighted graph.

- public DirectedWeightedGraph copy();



Returns true if and only if (iff) there is a valid path from each node to each
other node, assuming the graph is directional.

- public boolean isConnected();


Computes the length of the shortest path between src to dest
If no such path --> returns -1


- public double shortestPathDist(int src, int dest);


Computes the the shortest path between src to dest - as an ordered List of nodes.


public List<NodeData> shortestPath(int src, int dest);


Finds the NodeData which minimizes the max distance to all the other nodes.


- public NodeData center();


Computes the shortest list (By best effort) list of consecutive nodes which go over all the nodes in cities.


Saves this weighted (directed) graph to the given file name - in JSON format. e.g "G1.json" where G1 is a json file that represents a graph.


- public boolean save(String file);


This method loads a graph to this graph algorithm.
if the file was successfully loaded - the underlying graph
of this class will be changed (to the loaded one), in case the
graph was not loaded the original graph should remain "as is".


- public boolean load(String file);


##UML
 
 ![Screenshot from 2021-12-13 16-34-13](https://user-images.githubusercontent.com/74679553/145852822-154cc921-093b-4aaf-9ba3-8eb672a5d278.png)




