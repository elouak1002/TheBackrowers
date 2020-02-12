package filecreator;

// import classes from the ALG package;
import ALG.Parser;
import ALG.Node;

import java.io.IOException;

import java.nio.file.Path;

import java.util.Map;

import java.util.List;
import java.util.LinkedList;

import java.util.stream.Collectors;

/**
 * Create a list of string based on two model lists.
 * Only change the data coordinates for each node,
 * using the values from the Node Map.
 * @version 12.02.2020
 */
public class FileCreator {

	// Map the Name of the Node to its Object representation.
	private Map<String, Node> nodeMap; 
	
	// List of the data lines of the input file.
	private List<String> dataLines;

	// List of the full lines of the input file.
	private List<String> fileLines;

	// A Data Line parser, parse a String line into a DataLine object.
	private DataLineParser lineParser;

	// Position of the first data line in the input file.
	private int beginPosition;

	// Position of the last data line in the input file.
	private int endPosition;

	/**
	 * Constructor for the FileCreator class.
	 * @param nodeMap Map of the file nodes. 
	 * @param originPath Path to the input file
	 * @throws IOException IOException
	 */
	public FileCreator(Map<String, Node> nodeMap, Path originPath) throws IOException{

		// A File Parser.
		Parser parser = new Parser(originPath);
		// A Line Parser.
		this.lineParser = new DataLineParser();
		
		this.nodeMap = nodeMap;
		
		// A list of the data lines to be modified.
		this.dataLines = parser.getLines();
		// A list of all the string of the file.
		this.fileLines = parser.getAllLines();
		
		// Position of the first data line in the input file.
		this.beginPosition = parser.beginOfDataLines(fileLines, dataLines);
		// Position of the last data line in the input line.
		this.endPosition = parser.endOfDataLines(fileLines, dataLines);
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
	 * Merge the data (modified) lines into the full file lines.
	 * @return A list of String that merge the modified data lines into 
	 * the full file lines.
	 */
	private List<String> mergeLines() {
		List<String> outputFile = new LinkedList<>();
		
		for (int i=0; i < beginPosition; i++) {
			outputFile.add(fileLines.get(i));
		}

		outputFile.addAll(dataLines);
		
		for (int i = endPosition + 1; i < fileLines.size(); i++) {
			outputFile.add(fileLines.get(i));
		}
		
		return outputFile;
	}

	/**
	 * Modify the datalines, especially the X and Y coordinates, using 
	 * the node map.
	 */
	private void modifyDataLines() {
		dataLines =  dataLines.stream().map(line -> lineParser.createDataLine(line))
							  .map(dataLine -> modifyCoordinatesValues(dataLine, nodeMap))
							  .map(DataLine::toString)
							  .collect(Collectors.toList());
	}

	/**
	 * Process the creation of the list of string with the modified data.
	 */
	public List<String> processOutputFile() {
		modifyDataLines();
		return mergeLines();
	}
}