package linecreators;

import parser.Parser;
import datastructures.Node;

import java.io.IOException;

import java.nio.file.Path;

import java.util.Map;
import java.util.List;

/**
 * Abstract superclass of a Line Creator.
 * Use a node map and a parser in order to modify
 * a group of lines from the input file.
 * @version 12.02.2020
 */
public abstract class LineCreator {

	// Map the Name of the Node to its Object representation.
	protected Map<String, Node> nodeMap; 
	
	// List of the lines to be modfied.
	protected List<String> lines;

	// A parser to get the needed lines to be modified.
	protected Parser parser;

	/**
	 * Constructor for the Line Creator abstract class.
	 * @param nodeMap Map of the file nodes. 
	 * @param originPath Path to the input file
	 * @throws IOException IOException
	 */
	public LineCreator(Map<String, Node> nodeMap, Path originPath) throws IOException{

		// A File Parser.
		if (originPath != null)
			this.parser = new Parser(originPath);
		else
			this.parser = null;

		this.nodeMap = nodeMap;
	}

	/**
	 * Modify the initial lines.
	 */
	protected abstract void createLines();

	/**
	 * @return The modified lines.
	 */
	public List<String> getLines() {
		return lines;
	}
}