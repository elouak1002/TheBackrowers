package linecreatorstest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.io.IOException;

import linecreators.LineCreator;
import linecreators.NodeListLineCreator;
import datastructures.Node;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class NodeListLineCreatorTest {

	@ParameterizedTest
	private static Stream<Arguments> ArgumentsProvider() {
		return Stream.of(
			Arguments.of(new LinkedHashMap<String, Node>() {{
				put("Node1", new Node("Node1", 1.03f, 2.32f,"Node"));
				put("Node2", new Node("Node2", 10.34f, 20.54f, "Node"));
				put("Node3", new Node("Node3", 14.03f, 24.54f,"Node"));
				put("Node4", new Node("Node4", 7.01f, 27.03f,"Node"));
				put("Node5", new Node("Node5", 32.13f, 27.21f,"Node"));
			}},
			new ArrayList<String>(Arrays.asList(
				"====== NODE LISTS =======",
				"",
				"// Nodes:",
				" , Node1 , Node2 , Node3 , Node4 , Node5"
				))),
			Arguments.of(new LinkedHashMap<String, Node>() {{
				put("Node1", new Node("Node1", 1.03f, 2.32f,"Node"));
				put("Node2", new Node("Node2", 10.34f, 20.54f,"Room"));
				put("Node3", new Node("Node3", 14.03f, 24.54f,"Toilet"));
				put("Node4", new Node("Node4", 14.03f, 24.54f,"FloorChanger"));
			}},
			new ArrayList<String>(Arrays.asList(
				"====== NODE LISTS =======",
				"",
				"// Nodes:",
				" , Node1",
				"",
				"// Rooms:",
				" , Node2",
				"",
				"// Toilets:",
				" , Node3",
				"",
				"// FloorChangers:",
				" , Node4"
				))),
			Arguments.of(null,
			new ArrayList<String>(Arrays.asList(
				))),
			Arguments.of(new LinkedHashMap<String, Node>() {{}},new ArrayList<String>(Arrays.asList()))
		);

	}

	@ParameterizedTest
	@MethodSource("ArgumentsProvider")
	public void getModifiedLinesTest(LinkedHashMap<String,Node> nodeMap, ArrayList<String> output) throws IOException {
		LineCreator nodeListLineCreator = new NodeListLineCreator(nodeMap,null);

		assertEquals(nodeListLineCreator.getLines(),output);
	}
}