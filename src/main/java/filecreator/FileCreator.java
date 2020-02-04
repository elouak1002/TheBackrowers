package filecreator; 

import java.io.IOException;
import java.util.Map;

/**
 * Create an output file based on a model one.
 * Only change the data coordinates for each node,
 * using the values of the Node Map.
 * @version 04.02.2020
 */
public class FileCreator {

	private Map<String, TestNode> nodeMap; // TestNode will be replace with Node
	// private Parser parser;

	/**
	 * @param nodeMap Map of the file nodes. 
	 * @param parser Use to parse the input file.
	 */
	public FileCreator(Map<String,TestNode> nodeMap /*, Parser parser */ ) { // TestNode will be replaced with Node
		this.nodeMap = nodeMap;
		// this.parser = parser;
	}

	public void processOutputFile() throws IOException {
		/* TODO HERE */
		// Get the data lines from the file.
		// Extract the argument of the data lines as a List.
		// Modify the X and Y coordinates using the node map.
		// Recreate the line with the new X and Y coordinates values.
		// Integrate the modified lines into the whole file.
	}
}