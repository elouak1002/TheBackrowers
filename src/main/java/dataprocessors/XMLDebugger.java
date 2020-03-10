package dataprocessors;

import java.util.Iterator;
import java.util.Map;

import datastructures.Node;
import datastructures.Status;

/**
 * A debugger, responsible for debugging the output file before it's creation.
 * @version 23.02.2020
 */
public class XMLDebugger extends Debugger {

	/**
	 * @param nodeMap a Map of node (Node Name --> Node Object)
	 */
	public XMLDebugger(Map<String, Node> nodeMap) {
		super(nodeMap);
	}

	/**
	 * Process the debugging of the node map.
	 */
	@Override
	public void processDebugger() {
		removeUninitialisedNeighbours();
		super.processDebugger();
	}

	/**
	 * Remove neighbours of nodes that aren't
	 * initialised in the file.
	 */
	private void removeUninitialisedNeighbours() {
		for (String nodeName : nodeMap.keySet()) {
			Node node = nodeMap.get(nodeName);
			Iterator<Node> iter = node.getNeighbours().iterator();
			while (iter.hasNext()) {
				Node neighbour = iter.next();
				if (neighbour.getStatus() == Status.ONLY_NEIGHBOUR) {
					iter.remove();
				}
			}
		}
	}
}