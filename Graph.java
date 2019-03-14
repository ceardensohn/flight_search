/**
 * Name: Graph
 * 
 * @author Chris Eardensohn (ceardensohn@sandiego.edu)
 * @author Thaddeus Steele (tsteele@sandiego.edu)
 * Date: 4/29/2018
 * 
 * Description: Graph class represented using an adjacency list.
 * Provides functionality for simple Flight Search program.
 */
import java.util.*;
public class Graph {
	
	int countVertices;
	EdgeList[] adjacencyList;
	
	//constructor using number of vertices from user file
	public Graph(int countVertices) {
		this.countVertices = countVertices;
		adjacencyList = new EdgeList[countVertices];
		for(int i = 0; i < countVertices; i++) {
			adjacencyList[i] = new EdgeList();
		}
	}
	
	/**
	 * @param source the source vertex
	 * @param destination the destination vertex
	 * @param weight the cost of traversal
	 * addEdge function to add edge to an adjacency List
	 */
	public void addEdge(Vertex source, Vertex destination, int weight) {
		Edge edge = new Edge(source, destination, weight);
		adjacencyList[source.index].addFirst(edge);
	}
	
	/**
	 * @param source the user requested source vertex
	 * @param destination the user requested destination vertex
	 * @return the weight of total traversal, if no path is found return -1
	 * pathRequest gives the graph traversal and cost of requested Flight Search
	 */
	public int pathRequest(Vertex source, Vertex destination) {
		
		boolean visited[] = new boolean[countVertices];
		Vertex[] parent = new Vertex[countVertices];
		
		for (int i = 0; i < countVertices; i++) {
			parent[i] = new Vertex("", -1);
		}
		
		
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		
		visited[source.index] = true;
		queue.add(source);
		Iterator<Edge> iterate;
		while (queue.size()!= 0) {

			Vertex s = queue.poll();
			
			if(s.label == destination.label) {
				return printPath(parent, source, destination, 0);
			}
			
			//loop through edges
			iterate = adjacencyList[s.index].listIterator();
			while (iterate.hasNext()) {
				Edge temp = iterate.next();
				if(!visited[temp.destination.index])
				{
					visited[temp.destination.index] = true;
					queue.add(temp.destination);
					parent[temp.destination.index] = s;
				}
			}
		}
		return -1;
	}
	
	/**
	 * @param parent the parent array for the purpose of BFS
	 * @param source the source vertex
	 * @param destination the destination vertex
	 * @param weight the weight of traversal
	 * @return the total weight of traversal
	 * printPath builds flight path requested by user
	 */
	public int printPath(Vertex[] parent, Vertex source, Vertex destination, int weight) {
		
		if (parent[destination.index].index == -1) {
			System.out.print("Flying from: " + source.label + " ");
			return weight;
		}
		
		weight += printPath(parent, source, parent[destination.index], weight);
		weight += getEdgeWeight(parent[destination.index], destination);
		int singleWeight = getEdgeWeight(parent[destination.index], destination);
			
		if(destination.index < countVertices)
			System.out.print("to "+ destination.label + " (Cost: $" + singleWeight + ") ");
		
		return weight;
	}
	
	/**
	 * @param vertexList
	 * printGraph is used for testing purposes to confirm the adjacency list
	 * constructed from input files.
	 */
	public void printGraph(ArrayList<Vertex> vertexList) {
		for (int i=0; i < countVertices; i++) {
			System.out.println("Adj List at city: " + vertexList.get(i).label);
			for(Edge edge: adjacencyList[i]) {
				System.out.print(edge.source.label);
				System.out.print("--");
				System.out.print(edge.weight);
				System.out.print("->");
				System.out.print(edge.destination.label);
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
	
	/**
	 * @param source the source vertex
	 * @param destination the destination vertex
	 * @return weight the weight of the edge from source to destination
	 * getEdgeWeight gets a single edge weight from two vertices
	 */
	public int getEdgeWeight(Vertex source, Vertex destination) {
		int weight = 0;
		for (int i = 0; i < countVertices; i++) {
			for(Edge edge: adjacencyList[i]) {
				if (edge.source == source && edge.destination == destination) {
					weight += edge.weight; 
				}
			}
		}
		return weight;
	}
	
}
