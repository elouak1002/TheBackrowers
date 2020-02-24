package linecreatorstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;

import linecreators.LineCreator;
import linecreators.NeighbourLineCreator;
import datastructures.Node;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class NeighbourLineCreatorTest {

	private HashMap<String, Node> nodeMap;
	private List<Node> nodeList;

	@BeforeEach
	private void setUp() {
		nodeList = new ArrayList<Node>(Arrays.asList(
			new Node("HenRaph_04_493_264", 1.03f, 2.32f, "14"),
			new Node("HenRaph_04_476_264", 10.34f, 20.54f, "14"),
			new Node("HenRaph_04_374_347", 14.03f, 24.54f, "14"),
			new Node("HenRaph_04_418_357", 7.01f, 27.03f, "14"),
			new Node("HenRaph_04_419_365", 32.13f, 27.21f, "14")

		));
		nodeMap = new HashMap<String, Node>();
		for (Node node : nodeList) {
			nodeMap.put(node.getName(), node);
		}
	}
	
	@Test
	public void getLinesAllNodeWithNeighboursTest() throws IOException {
		List<String> output = new ArrayList<String>(Arrays.asList(
			"HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_374_347 } );",
			"HenRaph_04_476_264.addAllNeighbours( new List<Node>{ HenRaph_04_493_264 , HenRaph_04_374_347 } );",
			"HenRaph_04_374_347.addAllNeighbours( new List<Node>{ HenRaph_04_419_365 , HenRaph_04_418_357 } );",
			"HenRaph_04_418_357.addAllNeighbours( new List<Node>{ HenRaph_04_374_347 , HenRaph_04_419_365 } );",
			"HenRaph_04_419_365.addAllNeighbours( new List<Node>{ HenRaph_04_418_357 , HenRaph_04_476_264 } );"
		));

		nodeList.get(0).setNeighbours(Arrays.asList(nodeList.get(1), nodeList.get(2)));
		nodeList.get(1).setNeighbours(Arrays.asList(nodeList.get(0), nodeList.get(2)));
		nodeList.get(2).setNeighbours(Arrays.asList(nodeList.get(4), nodeList.get(3)));
		nodeList.get(3).setNeighbours(Arrays.asList(nodeList.get(2), nodeList.get(4)));
		nodeList.get(4).setNeighbours(Arrays.asList(nodeList.get(3), nodeList.get(1)));
		

		LineCreator neighbourLineCreator = new NeighbourLineCreator(nodeMap,Paths.get("src/test/resources/testData.txt"));

		List<String> input = neighbourLineCreator.getLines();
		// As I am using a HashMap, that does not maintain the order
		// I need to sort the list to compare it.
		input.sort(Comparator.naturalOrder());
		output.sort(Comparator.naturalOrder());

		assertEquals(input,output);
	}

	@Test
	public void getLinesFromNeighbourlessNodess() throws IOException {
		List<String> output = new ArrayList<String>(Arrays.asList(
		));

		LineCreator neighbourLineCreator = new NeighbourLineCreator(nodeMap,Paths.get("src/test/resources/testData.txt"));

		List<String> input = neighbourLineCreator.getLines();
		assertEquals(input,output);
	}

	@Test
	public void getLinesFromNullMap() throws IOException {
		List<String> output = new ArrayList<String>(Arrays.asList(
			"HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
			"HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
			"HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );"
		));

		LineCreator neighbourLineCreator = new NeighbourLineCreator(null,Paths.get("src/test/resources/testData.txt"));

		List<String> input = neighbourLineCreator.getLines();
		assertEquals(input,output);
	}
}