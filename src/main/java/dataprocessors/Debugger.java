package dataprocessors;

import java.util.Map;

import datastructures.Node;

/**
 * A debugger, responsible for debugging the output file before it's creation.
 * @version 21.02.2020
 */
public class Debugger {

	private Map<String, Node> nodeMap;

	/**
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 */
	public Debugger(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	
	// TODO ADD YOUR METHODS HERE for Septimiu
}