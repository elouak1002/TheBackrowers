package linecreators;

// import classes from the ALG package;
import parser.DataLineParser;
import datastructures.Node;
import datastructures.DataLine;

import java.io.IOException;

import java.nio.file.Path;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Modify the data lines from the input file
 * using a node map with updated coordinates.
 * @version 23.02.2020
 */
public class DataLineCreator extends LineCreator {

	// A Data Line parser, parse a String line into a DataLine object.
	private DataLineParser lineParser;

	/**
	 * Constructor for the DataLineCreator class.
	 * @param nodeMap Map of the nodes. 
	 * @param originPath Path to the input file
	 * @throws IOException IOException
	 */
	public DataLineCreator(Map<String, Node> nodeMap, Path originPath) throws IOException{

		super(nodeMap, originPath);
		// A Data Line Parser.
		this.lineParser = new DataLineParser();
		
		// A list of the data lines to be modified.
		this.lines = parser.getLines();
		
		// Process the modification of the data lines.
		createLines();
	}

	/**
	 * Modify the X and Y coordinates values from a DataLine using the values
	 * given by the node map.
	 * @param dataLine a data line
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 * @return modified data line
	 */
	private static DataLine modifyCoordinatesValues(DataLine dataLine, Map<String, Node> nodeMap) {	
		// Get the node from the map using its name.
		Node node = nodeMap.get(dataLine.getDataName()); 

		if (node == null) {
			return null;
		}
		
		// Get the coordinate from the node.s
		Float xCoord = node.getX();
		Float yCoord = node.getY();
		
		// Set the new coordinates values to the data line object.
		dataLine.setxCoord(xCoord);
		dataLine.setyCoord(yCoord);
		
		// return the modified DataLine object.
		return dataLine;
	}

	/**
	 * Modify the datalines, especially the X and Y coordinates, using 
	 * the node map.
	 */
	@Override
	protected void createLines() {
		lines =  lines.stream().map(line -> lineParser.createDataLine(line))
							  .map(dataLine -> modifyCoordinatesValues(dataLine, nodeMap))
							  // remove the null values, i.e., the nodes that aren't in the node map anymore.
							  .filter(Objects::nonNull)
							  .map(DataLine::toString)
							  .collect(Collectors.toList());
	}

}