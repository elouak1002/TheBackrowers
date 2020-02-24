package dataprocessors;

import java.util.Map;

import datastructures.Node;

/**
 * A debugger, responsible for debugging the output file before it's creation.
 * @version 23.02.2020
 */
public class Debugger {

	private Map<String, Node> nodeMap;

	/**
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 */
	public Debugger(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
		addExistingNeighbours();
		removeNeighbourlessNodes();
	}
	
	
	// TODO ADD YOUR METHODS HERE can be improved.

	private void addExistingNeighbours() {
		for (String nodeName : nodeMap.keySet()) {
			Node node = nodeMap.get(nodeName);
			for (Node neighbour : node.getNeighbours()) {
				if (neighbour.getNeighbours().contains(node)) {
					neighbour.addNeighbour(node);
				}
			}
		}
	}

	/**
	 * Remove the Nodes without neighbours from the Map.
	 */
	private void removeNeighbourlessNodes() {
		for (String nodeName : nodeMap.keySet()) {
			if (nodeMap.get(nodeName).getNeighbours().isEmpty()) {
				nodeMap.remove(nodeName);
				// Logger.logRemove(nodeName); // Log the deletion of a Node from the file to the logfile. 1st possibility.
				// notifyRemove(nodeName); // Basically the same, a form of Observer design Pattern.
			}
		}
	}



	
}