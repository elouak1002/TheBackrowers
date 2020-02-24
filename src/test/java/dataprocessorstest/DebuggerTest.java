package dataprocessorstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataprocessors.Debugger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat; 

import java.util.ArrayList;
import java.util.Arrays;
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
		nodeList = new ArrayList<Node>(Arrays.asList(
			new Node("HenRaph_04_493_264", 10f, 10f),
			new Node("HenRaph_04_476_264", 10f, 10f),
			new Node("HenRaph_04_374_347", 10f, 10f),
			new Node("HenRaph_04_418_357", 10f, 10f),
			new Node("HenRaph_04_419_365", 10f, 10f)

		));
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
}