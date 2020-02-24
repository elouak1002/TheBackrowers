//package linecreatorstest;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.Arguments;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.stream.Stream;
//import java.util.HashMap;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//import linecreators.DataLineCreator;
//import linecreators.LineCreator;
//import datastructures.Node;
//
///**
//* Test the String creation of the Data Line, especially the arguments creation.
//* @version 12.02.2020
//*/
//public class DataLineCreatorTest {
//
//	@ParameterizedTest
//	private static Stream<Arguments> ArgumentsProvider() {
//		return Stream.of(
//			Arguments.of(new HashMap<String, Node>() {{
//				put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 1.03f, 2.32f));
//				put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 10.34f, 20.54f));
//				put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 14.03f, 24.54f));
//				put("HenRaph_04_418_357", new Node("HenRaph_04_418_357", 7.01f, 27.03f));
//				put("HenRaph_04_419_365", new Node("HenRaph_04_419_365", 32.13f, 27.21f));
//			}},
//			new ArrayList<String>(Arrays.asList(
//				"Node HenRaph_04_493_264 = new Node( 1.03f , 2.32f , GuysHeights.HenRaph_04 );",
//				"Room HenRaph_04_476_264 = new Room( 10.34f , 20.54f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
//				"Toilet HenRaph_04_374_347 = new Toilet( 14.03f , 24.54f , GuysHeights.HenRaph_04 , ToiletType.Female );",
//				"FloorChanger HenRaph_04_418_357 = new FloorChanger( 7.01f , 27.03f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
//				"FloorChanger HenRaph_04_419_365 = new FloorChanger( 32.13f , 27.21f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2"
//				))),
//			Arguments.of(new HashMap<String, Node>() {{
//				put("HenRaph_04_493_264", new Node("HenRaph_04_493_264", 1.03f, 2.32f));
//				put("HenRaph_04_476_264", new Node("HenRaph_04_476_264", 10.34f, 20.54f));
//				put("HenRaph_04_374_347", new Node("HenRaph_04_374_347", 14.03f, 24.54f));
//			}},
//			new ArrayList<String>(Arrays.asList(
//				"Node HenRaph_04_493_264 = new Node( 1.03f , 2.32f , GuysHeights.HenRaph_04 );",
//				"Room HenRaph_04_476_264 = new Room( 10.34f , 20.54f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
//				"Toilet HenRaph_04_374_347 = new Toilet( 14.03f , 24.54f , GuysHeights.HenRaph_04 , ToiletType.Female );"
//				))),
//			Arguments.of(null,
//			new ArrayList<String>(Arrays.asList(
//				"Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
//				"Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
//				"Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
//				"FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
//				"FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2"
//				))),
//			Arguments.of(new HashMap<String, Node>() {{}},new ArrayList<String>(Arrays.asList()))
//		);
//
//	}
//
//	@ParameterizedTest
//	@MethodSource("ArgumentsProvider")
//	public void getModifiedLinesTest(HashMap<String,Node> nodeMap, ArrayList<String> output) throws IOException {
//		LineCreator dataLineCreator = new DataLineCreator(nodeMap,Paths.get("src/test/resources/testData.txt"));
//
//		assertEquals(dataLineCreator.getLines(),output);
//	}
//}