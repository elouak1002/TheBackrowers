package dataprocessorstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataprocessors.XMLDebugger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import datastructures.Node;
import datastructures.Status;

/** 
 * Test the Added method to the XML Debugger (removing uninitialised neighbours) 
 * @version 12.02.2020
*/
public class XMLDebuggerTest {

	private HashMap<String, Node> nodeMap;
	private List<Node> nodeList;

	@BeforeEach
	private void setUp() {
		nodeList = new ArrayList<Node>();
		nodeList.add(new Node("Node1", 10f, 10f));
		nodeList.add(new Node("Node2", 10f, 10f));

		nodeMap = new HashMap<String, Node>();
		for (Node node : nodeList) {
			nodeMap.put(node.getName(), node);
		}
	}

	@Test
	public void testUnitialisedNeighboursAreKept() {
		HashMap<String, Node> expectedOutcome = new HashMap<>();
        Node node1 = new Node("Node1",10f,10f);
		Node node2 = new Node("Node2",10f,10f);

		node1.setNeighbours(Arrays.asList(node2));
        node2.setNeighbours(Arrays.asList(node1));

        expectedOutcome.put("Node1",node1);
		expectedOutcome.put("Node2",node2);

		// Add neighbour for node 1
		nodeMap.get("Node1").setNeighbours(new ArrayList<Node>(Arrays.asList(new Node("NodeA",Status.ONLY_NEIGHBOUR),new Node("NodeB",Status.ONLY_NEIGHBOUR))));
		nodeMap.get("Node2").setNeighbours(new ArrayList<Node>(Arrays.asList(new Node("NodeA",Status.ONLY_NEIGHBOUR),new Node("NodeB",Status.ONLY_NEIGHBOUR))));
		
		// Add neighbour for node 1
		nodeMap.get("Node1").addNeighbour(nodeList.get(1));
		
		// Add neighbour for node 2
		nodeMap.get("Node2").addNeighbour(nodeList.get(0));
		
		XMLDebugger XMLdebugger = new XMLDebugger(nodeMap);

		HashMap<String, Node> actualOutcome = new HashMap<>(XMLdebugger.getMap());

		Comparator<Node> byName = (Node nodeA, Node nodeB) -> node1.getName().compareTo(node2.getName());

		for (String nodeName : actualOutcome.keySet()) {
			actualOutcome.get(nodeName).getNeighbours().sort(byName);
			expectedOutcome.get(nodeName).getNeighbours().sort(byName);
            assertEquals(actualOutcome.get(nodeName).getNeighbours().toString(), expectedOutcome.get(nodeName).getNeighbours().toString());
		}
	}
}