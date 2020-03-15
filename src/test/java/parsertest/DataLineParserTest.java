package parsertest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

import parser.DataLineParser;

/**
* Test that each part of the parser is working as expected.
*/
public class DataLineParserTest {

	public static Stream<Arguments> ArgumentsProvider() {
		return Stream.of(
			Arguments.of("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 ); // Test", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList("GuysHeights.HenRaph_04")), "; // Test"),
			Arguments.of("Node HenRaph_04_493_264 = new Node( 49.312683f );", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "", new ArrayList<String>(Arrays.asList()), ";"),
			Arguments.of("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f );", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList()), ";"),
			Arguments.of("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 , GuysHeights.HenRaph_04 , GuysHeights.HenRaph_04 ); // Test", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList("GuysHeights.HenRaph_04","GuysHeights.HenRaph_04","GuysHeights.HenRaph_04")), "; // Test")
		);
	}

	private DataLineParser parser;

	@BeforeEach
	public void createParser() {
		parser = new DataLineParser();
	}

	@ParameterizedTest
	@MethodSource("ArgumentsProvider")
	public void dataLineCreationTest(String inputLine, String staticType, String dataName, String dynamicType, String xCoord, String yCoord, List<String> otherArgList, String endPart) {
		parser.parseNewLine(inputLine);
		assertEquals(parser.getStaticType(), staticType);
		assertEquals(parser.getDataName(), dataName);
		assertEquals(parser.getDynamicType(), dynamicType);
		assertEquals(parser.getxCoord(), xCoord);
		assertEquals(parser.getyCoord(), yCoord);
		assertEquals(parser.getOtherArguments(), otherArgList);
		assertEquals(parser.getEndPart(), endPart);
	}
}