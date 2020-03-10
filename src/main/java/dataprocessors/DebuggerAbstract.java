package dataprocessors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import datastructures.Node;
import datastructures.Status;

/**
 * A debugger, responsible for debugging the output file before it's creation.
 * @version 23.02.2020
 */
public class DebuggerAbstract {

	private Map<String, Node> nodeMap;
	private ArrayList<String> log;

	/**
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 */
	public DebuggerAbstract(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
		log = new ArrayList<>();
		removeUninitialisedNeighbours();
		addExistingNeighbours();
		removeNeighbourlessNodes();
	}

	public ArrayList<String> getLog() {
		return log;
	}

	/**
	 * If nodeA doesn't already have nodeB as a neighbour,
	 * then add it.
	 */
	private void addAsNeighbour(Node nodeA, Node nodeB) {
		if(!nodeA.hasNeighbour(nodeB)) {
			nodeA.addNeighbour(nodeB);
			// Log the addition of nodeB as a neighbor of nodeA.
			log.add(nodeB.getName() + " added to become a neighbour for " + nodeA.getName() + ".\n");
		}
	}
  
	/**
	 * Run over the key of the map,
	 * and add neighbours to each node if needed.
	 * Add neighbours only to initialised nodes.
	 */
	private void addExistingNeighbours() {
		for (String nodeName : nodeMap.keySet()) {
			Node node = nodeMap.get(nodeName);
			for (Node neighbour : node.getNeighbours()) {
				if (neighbour.getStatus() == Status.INITIALISED) {
					addAsNeighbour(neighbour, node);
				}
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
				// Log the deletion of a Node from the file to the log. 1st possibility.
				log.add(node.getValue().getName() + " has no neighbour, so it was removed.\n");
				// notifyRemove(nodeName); // Basically the same, a form of Observer design Pattern.
        		iter.remove();
    		}
		}
	}

	/**
	 * Remove the Nodes from the Nodes that haven't been initialised.
	 */
	private void removeUninitialisedNeighbours() {
		Iterator<Map.Entry<String,Node>> iter = nodeMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,Node> node = iter.next();
    		if(node.getValue().getStatus() == Status.UNINITIALISED) {
    			log.add("Node " + node.getValue().getName() + " has not been initialised, so it was removed. \n"); // Log the deletion of a Node from the file to the logfile. 1st possibility.
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