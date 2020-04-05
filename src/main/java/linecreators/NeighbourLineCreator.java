package linecreators;

import datastructures.Node;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Modify the neighbour lines from the input file
 * using a node map with updated neighbours for each node.
 * @version 23.02.2020
 */
public class NeighbourLineCreator extends LineCreator {

	/**
	 * Constructor for the Neighbour line creator class.
	 * @param nodeMap Map of the file nodes. 
	 * @param originPath Path to the input file
	 * @throws IOException IOException
	 */
	public NeighbourLineCreator(Map<String, Node> nodeMap, Path originPath) throws IOException {
		// Call to parent constructor
		super(nodeMap, originPath);
		
		// A list of the neighbour lines to be modified.
		this.lines = Collections.emptyList();

		// Process the modification of the neighbour lines.
		if (nodeMap != null ) 
		createLines();
		else
			lines = parser.getNeighboursLines();
	}

	/**
	 * Modify/Recreate the neighbour line using the
	 * information given by the node map.
	 * @param nodeName the node's name
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 * @return modified neighbour line
	 */
	private static String createNeighbourLine(String nodeName, Map<String, Node> nodeMap) {	
		// Get the node from the map using its name.
		Node parentNode = nodeMap.get(nodeName); 
		
		// Check if the parent exist in the node map and if there are neighbours.
		if (parentNode == null || parentNode.getNeighbours().isEmpty()) {
			return "";
		}

		StringBuilder neighbourLine = new StringBuilder(nodeName);
		neighbourLine.append(".addAllNeighbours( new List<Node>{ ");
		
		// Add each neighbour using it's name to the neighbour line.
		for (Node node : parentNode.getNeighbours()) {
			neighbourLine.append(node.getName());
			neighbourLine.append(" , ");
		}
		
		neighbourLine = new StringBuilder(neighbourLine.substring(0, neighbourLine.length() - 3));
		
		neighbourLine.append(" } );");
		
		// return the modified DataLine object.
		return neighbourLine.toString();
	}

	/**
	 * Modify the neighbours lines, adding the corresponding
	 * neighbours using the node map.
	 */
	@Override
	protected void createLines() {
		lines = nodeMap.keySet()
		.stream()
		.map(nodeName -> createNeighbourLine(nodeName, nodeMap))
		// Eliminate all the empty line from List, i.e. , all the nodes without neighbours.
		.filter(line -> !line.isEmpty())
		.collect(Collectors.toList());
	}

}