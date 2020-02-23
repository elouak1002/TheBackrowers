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

import linecreators.FileLinesCreator;
import datastructures.Node;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class FileLinesCreatorTest {

	@ParameterizedTest
	private static Stream<Arguments> ArgumentsProvider() {
		return Stream.of(
			Arguments.of(new HashMap<String, Node>() {{
				put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 1.0f, 2.0f));
				put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 10.0f, 20.0f));
				put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 14.0f, 24.0f));
				put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 7.0f, 27.0f));
				put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 32.1f, 27.19f));
			}},
			new ArrayList<String>(Arrays.asList(
				"// Data of Mappin Technologies LTD 2019",
				"// Thu Aug  1 11:48:39 2019",
				"",
				"",
				"Node HenRaph_04_493_264 = new Node( 1.0f , 2.0f , GuysHeights.HenRaph_04 );",
				"Room HenRaph_04_476_264 = new Room( 10.0f , 20.0f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
				"Toilet HenRaph_04_374_347 = new Toilet( 14.0f , 24.0f , GuysHeights.HenRaph_04 , ToiletType.Female );",
				"FloorChanger HenRaph_04_418_357 = new FloorChanger( 7.0f , 27.0f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
				"FloorChanger HenRaph_04_419_365 = new FloorChanger( 32.1f , 27.19f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
				"",
				// "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
				// "HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
				// "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );",
				"",
				"====== NODE LISTS =======",
				"",
				"// Nodes:",
				" , HenRaph_04_493_264 , HenRaph_04_493_276 , HenRaph_04_493_346 , HenRaph_04_438_346 , HenRaph_04_439_357 , HenRaph_04_439_365"
				)))
		);
		
	}

	@ParameterizedTest
	@MethodSource("ArgumentsProvider")
	public void processOutputFileTest(HashMap<String,Node> nodeMap, ArrayList<String> output) throws IOException {
		FileLinesCreator fileCrea = new FileLinesCreator(nodeMap,Paths.get("src/test/resources/testData.txt"));

		assertEquals(fileCrea.getOutputFile(),output); // Need to be modified when wrangler will be added.
	}
}