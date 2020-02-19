package datastructurestest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Stream;

import datastructures.DataLine;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class DataLineTest {

	private static Stream<Arguments> ArgumentsProvider() {
		return Stream.of(
			Arguments.of("32.02f", "55.02f", null, "Node name = new Node( 32.02f , 55.02f )"),
			Arguments.of("", "", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 1st Argument , 2nd Argument , 3rd Argument )"),
			Arguments.of("", "10.0f", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"),
			Arguments.of("10.0f", "10.0f", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"),
			Arguments.of("10.0f", "", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"),
			Arguments.of("10.0f", "", null, "Node name = new Node( 10.0f )"),
			Arguments.of("", "20.0f", null, "Node name = new Node( 20.0f )")
		);
	}

	@ParameterizedTest
	@MethodSource("ArgumentsProvider")
	public void stringOutputTest(String xCoord, String yCoord, ArrayList<String> otherArgs, String output) {
		DataLine line = new DataLine("name", xCoord, yCoord, otherArgs, "Node", "Node", "");
		assertEquals(line.toString(),output);
	}
}