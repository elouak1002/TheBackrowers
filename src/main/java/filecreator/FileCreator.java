package filecreator;

// import classes from the ALG package;
import ALG.Parser;
import ALG.Node;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import java.util.Map;

import java.util.List;
import java.util.LinkedList;

import java.util.stream.Collectors;

/**
 * Create an output file based on a model one.
 * Only change the data coordinates for each node,
 * using the values from the Node Map.
 * @version 07.02.2020
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

	// Name of the input file.
	private String filename;

	// A Parser object
	private Parser parser;

	// Path to new file
	private Path destinationPath;

	/**
	 * Constructor for the FileCreator class.
	 * @param nodeMap Map of the file nodes. 
	 * @param originPath Path to the input file
	 * @param destinationPath Path to save the output file to
	 * @throws IOException
	 */
	public FileCreator(Map<String, Node> nodeMap, Path originPath, Path destinationPath) throws IOException{

		parser = new Parser(originPath);
		this.nodeMap = nodeMap;
		this.dataLines = parser.getLines();
		this.fileLines = parser.getAllLines();
		this.lineParser = new DataLineParser();
		this.beginPosition = parser.beginOfDataLines(fileLines, dataLines);
		this.endPosition = parser.endOfDataLines(fileLines, dataLines);
		this.filename = originPath.getFileName().toString();
		this.destinationPath = destinationPath;

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
		
		for (String node : dataLines) {
			outputFile.add(node);
		}
		
		for (int i = endPosition + 1; i < fileLines.size(); i++) {
			outputFile.add(fileLines.get(i));
		}
		
		return outputFile;
	}

	/**
	 * @return The derived file name of the output file.
	 */
	private String getDerivedFileName() {
		return "global_" + filename;
	}

	/**
	 * Create a new file using a List of String, with a given name.
	 * If the file already exists, it 
	 * @param fileLines the List of Strings representing the file.
	 * @param fileName the name of the new file.s
	 * @throws IOException
	 */
	private void createFile(List<String> fileLines, String fileName) throws IOException {
		Files.write(Paths.get(destinationPath.toString(), fileName),
				fileLines, 
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE);
	}

	/**
	 * Modify the datalines, especially the X and Y coordinates, using 
	 * the node map.
	 */
	private void modifyDataLines() {
		dataLines =  dataLines.stream().map(line -> lineParser.createDataLine(line))
							  .map(dataLine -> modifyCoordinatesValues(dataLine, nodeMap))
							  .map(dataLine -> dataLine.toString())
							  .collect(Collectors.toList());
	}

	/**
	 * Process the creation of the file.
	 */
	public void processOutputFile() throws IOException{
		modifyDataLines();
		createFile(mergeLines(), getDerivedFileName());
	}
}