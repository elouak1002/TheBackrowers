package dataprocessorstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataprocessors.Debugger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import datastructures.Node;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class DebuggerTest {

	private HashMap<String, Node> nodeMap;
	private List<Node> nodeList;

	@BeforeEach
	private void setUp() {
		nodeList = new ArrayList<Node>();
		nodeList.add(new Node("Node1", 10f, 10f));
		nodeList.add(new Node("Node2", 10f, 10f));
		nodeList.add(new Node("Node3", 10f, 10f));
		nodeList.add(new Node("Node4", 10f, 10f));
		nodeList.add(new Node("Node5", 10f, 10f));

		nodeMap = new HashMap<String, Node>();
		for (Node node : nodeList) {
			nodeMap.put(node.getName(), node);
		}
	}
	
	@Test
	public void testRemovingAllNeighbours() {
		Debugger debugger = new Debugger(nodeMap);

		HashMap<String, Node> actualOutcome = new HashMap<>(debugger.getMap());

		assertThat(actualOutcome.keySet().isEmpty(), is(true));
	}

	@Test
	public void testAddOneNeighbour() {
		HashMap<String, Node> expectedOutcome = new HashMap<>();
        Node node1 = new Node("Node1",10f,10f);
		Node node2 = new Node("Node2",10f,10f);

        node1.setNeighbours(Arrays.asList(node2));
        node2.setNeighbours(Arrays.asList(node1));

        expectedOutcome.put("Node1",node1);
        expectedOutcome.put("Node2",node2);

		nodeMap.get("Node1").addNeighbour(nodeList.get(1));
		
		Debugger debugger = new Debugger(nodeMap);

		HashMap<String, Node> actualOutcome = new HashMap<>(debugger.getMap());
		
		for (String nodeName : actualOutcome.keySet()) {
            assertThat(actualOutcome.get(nodeName).getNeighbours().toString(), equalTo(expectedOutcome.get(nodeName).getNeighbours().toString()));
        }
	}

	@Test
	public void testAddandRemoveMultiple() {
		HashMap<String, Node> expectedOutcome = new HashMap<>();
        Node node1 = new Node("Node1",10f,10f);
		Node node2 = new Node("Node2",10f,10f);
		Node node3 = new Node("Node3",10f,10f);
		Node node4 = new Node("Node4",10f,10f);
		Node node5 = new Node("Node5",10f,10f);

        node1.setNeighbours(Arrays.asList(node2,node4,node3));
        node2.setNeighbours(Arrays.asList(node3,node1,node4));
        node3.setNeighbours(Arrays.asList(node1,node2,node5));
        node4.setNeighbours(Arrays.asList(node1,node2,node5));
        node5.setNeighbours(Arrays.asList(node4,node3));

        expectedOutcome.put("Node1",node1);
        expectedOutcome.put("Node2",node2);
        expectedOutcome.put("Node3",node3);
        expectedOutcome.put("Node4",node4);
        expectedOutcome.put("Node5",node5);

		// Add neighbour for node 1
		nodeMap.get("Node1").addNeighbour(nodeList.get(1));
		nodeMap.get("Node1").addNeighbour(nodeList.get(3));

		// Add neighbour for node 2
		nodeMap.get("Node2").addNeighbour(nodeList.get(2));

		// Add neighbour for node 3
		nodeMap.get("Node3").addNeighbour(nodeList.get(0));
		nodeMap.get("Node3").addNeighbour(nodeList.get(1));

		// Add neighbour for node 4
		nodeMap.get("Node4").addNeighbour(nodeList.get(0));
		nodeMap.get("Node4").addNeighbour(nodeList.get(1));

		// Add neighbour for node 5
		nodeMap.get("Node5").addNeighbour(nodeList.get(3));
		nodeMap.get("Node5").addNeighbour(nodeList.get(2));



		
		Debugger debugger = new Debugger(nodeMap);

		HashMap<String, Node> actualOutcome = new HashMap<>(debugger.getMap());

		Comparator<Node> byName = (Node nodeA, Node nodeB) -> node1.getName().compareTo(node2.getName());
		
		for (String nodeName : actualOutcome.keySet()) {
			System.out.println(nodeName);
			actualOutcome.get(nodeName).getNeighbours().sort(byName);
			expectedOutcome.get(nodeName).getNeighbours().sort(byName);
            assertThat(actualOutcome.get(nodeName).getNeighbours().toString(), equalTo(expectedOutcome.get(nodeName).getNeighbours().toString()));
        }
	}
}