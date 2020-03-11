package linecreators;

import java.util.Map;
import java.util.LinkedList;
import java.util.List;

import datastructures.Node;
import parser.Parser;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Process the creation of the lines of the Ouput modified file.
 * @version 21.02.2020
 */
public class FileLinesCreator {

	// parser in order to parse the initial input file.
	private Parser parser;

	// Begin and end of data lines in the input
	private int beginDataPosition;
	private int endDataPosition;
	
	// Begin and end of neighbours lines in the input
	private int beginNeighbourPosition;
	private int endNeighbourPosition;
	
	// The output file, as a list of strings.
	private List<String> file;
	// The data lines of the output file.
	private List<String> dataLines;
	// The neighbour lines of the output file.
	private List<String> neighbourLines;
	
	/**
	 * @param nodeMap the map of Nodes (Name -> Node)
	 * @param originPath The path to the origin input file, needed for the parser.
	 * @throws IOException
	 */
	public FileLinesCreator(Map<String, Node> nodeMap, Path originPath) throws IOException {
		this.parser = new Parser(originPath);
		
		// Create the data lines for the output file, using the modified nodeMap.
		LineCreator dataCreator = new DataLineCreator(nodeMap, originPath);
		this.dataLines = dataCreator.getLines();
		
		// Create the neighbour lines for the output file, using the modified nodeMap.
		LineCreator neighbourCreator = new NeighbourLineCreator(nodeMap, originPath);
		this.neighbourLines =  neighbourCreator.getLines();
		
		// Initially the ouput file is the input file.
		this.file = parser.getAllLines();
		
		// compute begin and end of data lines in the input
		this.beginDataPosition = parser.beginOfDataLines();
		this.endDataPosition = parser.endOfDataLines();
		
		// compute begin and end of neighbours lines in the input
		this.beginNeighbourPosition = parser.beginOfNeighbourLines();
		this.endNeighbourPosition =  parser.endOfNeighbourLines();
		
		// Process the modifications to the input file.
		processOutputFile();
	}

	/**
	 * Merge the data (modified) lines into the full file lines.
	 * @return A list of String that merge the modified data lines into 
	 * the full file lines.
	 */
	private List<String> mergeDataLines(List<String> outputFile) {
		
		for (int i=0; i < beginDataPosition; i++) {
			outputFile.add(file.get(i));
		}

		outputFile.addAll(dataLines);
		
		return outputFile;
	}

	/**
 	* Merge the neighbours (modified) lines into the full file lines.
	 * @return A list of String that merge the modified neighbours lines into 
	 * the full file lines.
	 */
	private List<String> mergeNeighbourLines(List<String> outputFile) {

		outputFile.addAll(neighbourLines);
		
		for (int i = endNeighbourPosition + 1; i < file.size(); i++) {
			outputFile.add(file.get(i));
		}
		
		return outputFile;
	}

	private List<String> mergeBetweenDataAndNeighbour(List<String> outputFile) {

		for (int i = endDataPosition + 1; i < beginNeighbourPosition; i++) {
			outputFile.add(file.get(i));
		}

		return outputFile;
	}

	/**
	 * Process the modifications from input file to output file.
	 */
	private void processOutputFile() {
		List<String> outputFile = new LinkedList<>();

		mergeDataLines(outputFile);
		mergeBetweenDataAndNeighbour(outputFile);
		mergeNeighbourLines(outputFile);

		this.file = outputFile;
	}

	/**
	 * @return The ouput file.
	 */
	public List<String> getOutputFile() {
		return file;
	}
}