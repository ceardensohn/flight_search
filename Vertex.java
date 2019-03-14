/**
 * Name: Vertex
 * 
 * @author Chris Eardensohn (ceardensohn@sandiego.edu)
 * @author Thaddeus Steele (tsteele@sandiego.edu)
 * Date: 4/29/2018
 * 
 * Description: Vertex class for simple Flight Search program.
 * Each vertex holds a label and index for their source placement
 * in adjacency list.
 *
 */
public class Vertex {
	public String label;
	public int index;
	
	public Vertex(String label, int index) {
		this.label = label;
		this.index = index;
	}
	
}
