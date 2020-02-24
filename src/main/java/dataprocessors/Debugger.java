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
	
	
	/**
	 * If nodeB doesn't already have nodeA as a neighbour,
	 * then add it.
	 */
	private void addAsNeighbour(Node nodeA, Node nodeB) {
		if(!nodeA.hasNeighbour(nodeB)) {
			nodeA.addNeighbour(nodeB);
			// Logger.logAdd(nodeA.getName(),nodeB.getName())
		}
	}


	/**
	 * Run over the key of the map,
	 * and add neighbours to each node if needed.
	 */
	private void addExistingNeighbours() {
		for (String nodeName : nodeMap.keySet()) {
			Node node = nodeMap.get(nodeName);
			for (Node neighbour : node.getNeighbours()) {
				addAsNeighbour(neighbour, node);
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

	/**
	 * @return the debugged node map.
	 */
	public Map<String,Node> getMap() {
		return nodeMap;
	}
	
}