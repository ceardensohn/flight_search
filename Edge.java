/**
 * Name: Edge
 * 
 * @author Chris Eardensohn (ceardensohn@sandiego.edu)
 * @author Thaddeus Steele (tsteele@sandiego.edu)
 * Date: 4/29/2018
 * 
 * Description: Edge class for the purpose of building an Adjacency List.
 * Each edge holds a source and destination vertex as well as a weight
 * for traversal.  
 *
 */
public class Edge {
	public Vertex source;
	public Vertex destination;
	public int weight;
	
	public Edge(Vertex source, Vertex destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
}