package linecreatorstest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Paths;

import linecreators.DataLineCreator;
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
			Arguments.of(new HashMap<String, Node>() {{
				put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 1.03f, 2.32f,"Node"));
				put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 10.34f, 20.54f, "Node"));
				put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 14.03f, 24.54f,"Node"));
				put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 7.01f, 27.03f,"Node"));
				put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 32.13f, 27.21f,"Node"));
			}},
			new ArrayList<String>(Arrays.asList(
				"====== NODE LISTS =======",
				"",
				"",
				"",
				"",
				""
				))),
			Arguments.of(new HashMap<String, Node>() {{
				put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 1.03f, 2.32f,"Node"));
				put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 10.34f, 20.54f,"Room"));
				put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 14.03f, 24.54f,"Toilet"));
			}},
			new ArrayList<String>(Arrays.asList(
				))),
			Arguments.of(null,
			new ArrayList<String>(Arrays.asList(
				))),
			Arguments.of(new HashMap<String, Node>() {{}},new ArrayList<String>(Arrays.asList()))
		);

	}

	@ParameterizedTest
	@MethodSource("ArgumentsProvider")
	public void getModifiedLinesTest(HashMap<String,Node> nodeMap, ArrayList<String> output) throws IOException {
		LineCreator nodeListLineCreator = new NodeListLineCreator(nodeMap,Paths.get("src/test/resources/testData.txt"));

		assertEquals(nodeListLineCreator.getLines(),output);
	}
}