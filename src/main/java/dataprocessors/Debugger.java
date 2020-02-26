package dataprocessors;

import java.util.Iterator;
import java.util.Map;

import GUI.LoggerController;
import datastructures.Node;
import GUI.LoggerController;

/**
 * A debugger, responsible for debugging the output file before it's creation.
 * @version 23.02.2020
 */
public class Debugger {

	private Map<String, Node> nodeMap;
	private LoggerController logger = new LoggerController();

	/**
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 */
	public Debugger(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
		addExistingNeighbours();
		removeNeighbourlessNodes();
	}
	
	
	/**
	 * If nodeA doesn't already have nodeB as a neighbour,
	 * then add it.
	 */
	private void addAsNeighbour(Node nodeA, Node nodeB) {
		if(!nodeA.hasNeighbour(nodeB)) {
			nodeA.addNeighbour(nodeB);
			logger.logAdd(nodeA.getName(),nodeB.getName()); // Log the addition of nodeB as a neighbor of nodeA.
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
		Iterator<Map.Entry<String,Node>> iter = nodeMap.entrySet().iterator();
		while (iter.hasNext()) {
    		Map.Entry<String,Node> node = iter.next();
    		if(node.getValue().getNeighbours().isEmpty()){
				logger.logRemove(node.getValue().getName()); // Log the deletion of a Node from the file to the logfile. 1st possibility.
				// notifyRemove(nodeName); // Basically the same, a form of Observer design Pattern.
        		iter.remove();
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