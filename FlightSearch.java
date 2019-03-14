/**
 * Name: FlightSearch
 * 
 * @author Chris Eardensohn (ceardensohn@sandiego.edu)
 * @author Thaddeus Steele (tsteele@sandiego.edu)
 * Date: 4/29/2018
 * 
 * Description: Driver class for simple flight search program.  Uses BFS type search
 * to give a user a total cost from desired departure to arrival city. 
 *
 * Execution instructions: java FlightSearch cityFile.txt flightFile.txt
 * Capitalization is required, type "exit" to quit program.
 */
import java.util.*;
import java.io.*;
public class FlightSearch {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(System.in);
		boolean exit = false;
		
		File vertexFile;
		File edgeFile;
		
		if (args.length != 2) {
			System.out.println("Please enter the file containing cities:");
			vertexFile = new File(in.nextLine());
			System.out.println("Please enter the file containing flights:");
			edgeFile = new File(in.nextLine());
		} else {
			vertexFile = new File(args[0]);
			edgeFile = new File(args[1]);	
		}
		
		
		//in case user misspells the text files
		while(!vertexFile.exists() || !edgeFile.exists()) {
			System.out.println("Invalid file name(s).");
			System.out.println("Please enter the file containing cities:");
			vertexFile = new File(in.nextLine());
			System.out.println("Please enter the file containing flights:");
			edgeFile = new File(in.nextLine());
		}
		
		//parse text files
		ArrayList<Vertex> vertexArrayList = parseVertices(vertexFile);
		
		//check if there are digits in the vertex file
		//if true exit program and print execution error
		if (vertexArrayList.get(0).label.matches(".*\\d.*")) {
			System.out.println("Please enter the file of cities before" + 
		" the file of flights when executing FlightSearch.");
			System.exit(0);
		}
		Graph graph = new Graph(vertexArrayList.size());
		addEdgesToGraph(edgeFile, vertexArrayList, graph);
		
		//graph.printGraph(vertexArrayList);
		
		//prompt user for path requests
		while(!exit) {
			String inputOne = "";
			String inputTwo = "";
			System.out.println("Welcome to USAir. To exit Flight Search, type: exit");
			System.out.println("Please enter the departure city:");
			inputOne = in.nextLine();
			if(inputOne.equals("exit")){
				System.out.println("Thank you for using USAir.");
				in.close();
				System.exit(0);
			}
			System.out.println("Please enter the arrival city:");
			inputTwo = in.nextLine();
			if(inputTwo.equals("exit")){
				System.out.println("Thank you for using USAir.");
				in.close();
				System.exit(0);
			}
			int indexOne = indexOfVertex(inputOne, vertexArrayList);
			int indexTwo = indexOfVertex(inputTwo, vertexArrayList);
			if (indexOne == -1) {
				System.out.println("Request is to fly from " + inputOne + " to " + inputTwo + ".");
				System.out.println("Sorry. USAir does not serve " + inputOne);
				System.out.println("Reminder: check for correct capitals and punctuation.");
				System.out.println();
			} else if (indexTwo == -1) {
				System.out.println("Request is to fly from " + inputOne + " to " + inputTwo + ".");
				System.out.println("Sorry. USAir does not serve " + inputTwo);
				System.out.println("Reminder: check for correct capitals and punctuation.");
				System.out.println();
			} else {
				Vertex source = vertexArrayList.get(indexOne);
				Vertex destination = vertexArrayList.get(indexTwo);
				int weight = graph.pathRequest(source, destination);
				if(weight != -1) {
					System.out.println("\n" + "Total Cost: $" + weight);
				} else{
					System.out.println("Request is to fly from " + inputOne + " to " + inputTwo + ".");
					System.out.println("Sorry. USAir does not fly from " + inputOne + " to " + inputTwo + ".");
					System.out.println();
				}
			}
		}
		in.close();
	}
	
	/**
	 * @param vertexFile the file to read from
	 * @return vertexArrayList the list of compiled vertices
	 * @throws FileNotFoundException
	 * parseVertices function allows for the traversal of cities
	 */
	public static ArrayList<Vertex> parseVertices(File vertexFile) throws FileNotFoundException {
		int countVertices = 0;
		Scanner readVertex = new Scanner(vertexFile);
		ArrayList<Vertex> vertexArrayList = new ArrayList<Vertex>();
		String vertexLabel = "";
		while (readVertex.hasNextLine()){
			vertexLabel = readVertex.nextLine();
			Vertex vertex = new Vertex(vertexLabel, countVertices);
			vertexArrayList.add(vertex);
			countVertices ++;
		}
		readVertex.close();
		return vertexArrayList;
	}
	
	/**
	 * @param edgeFile the edge file input by user
	 * @param vertexArrayList the list to build edges from
	 * @param graph the adjacency list
	 * @throws FileNotFoundException
	 * addEdgesToGraph function builds graph from user input files
	 */
	public static void addEdgesToGraph(File edgeFile, ArrayList<Vertex> vertexArrayList, Graph graph)
			throws FileNotFoundException {
		Scanner readEdge = new Scanner(edgeFile);
		String edgeLine = "";
		while (readEdge.hasNextLine()) {
			edgeLine = readEdge.nextLine();
			String[] edgeArray = edgeLine.split("\\s*,\\s*");
			Vertex source = vertexArrayList.get(indexOfVertex(edgeArray[0], vertexArrayList));
			Vertex destination = vertexArrayList.get(indexOfVertex(edgeArray[1], vertexArrayList));
			int weight = Integer.parseInt(edgeArray[2]);
			graph.addEdge(source, destination, weight);
		}
		readEdge.close();
	}
	
	/**
	 * @param string the string from user input to request flight
	 * @param vertexArrayList the array list of all vertices
	 * @return index the index of the vertex with input string
	 */
	public static int indexOfVertex(String string, ArrayList<Vertex> vertexArrayList) {
		int index = -1;
		for (int i = 0; i < vertexArrayList.size(); i++) {
			if (vertexArrayList.get(i).label.equals(string))
				index = i;
		}
		return index;
	}
}
