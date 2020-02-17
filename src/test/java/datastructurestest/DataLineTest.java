//package datastructurestest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameters;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.ArrayList;
//
//import datastructures.DataLine;
//
///**
// * Test the String creation of the Data Line, especially the arguments creation.
// * @version 12.02.2020
// */
//@RunWith(Parameterized.class)
//public class DataLineTest {
//
//	@Parameters
//	public static Collection<Object[]> data() {
//		return Arrays.asList(new Object[][] {
//			{"32.02f", "55.02f", null, "Node name = new Node( 32.02f , 55.02f )"},
//			{"", "", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 1st Argument , 2nd Argument , 3rd Argument )"},
//			{"", "10.0f", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"},
//			{"10.0f", "10.0f", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"},
//			{"10.0f", "", new ArrayList<String>(Arrays.asList("1st Argument", "2nd Argument", "3rd Argument")), "Node name = new Node( 10.0f , 1st Argument , 2nd Argument , 3rd Argument )"},
//			{"10.0f", "", null, "Node name = new Node( 10.0f )"},
//			{"", "20.0f", null, "Node name = new Node( 20.0f )"}
//		});
//	}
//
//
//	private String xCoord;
//	private String yCoord;
//	private ArrayList<String> otherArgs;
//	private String output;
//	public DataLineTest(String xCoord, String yCoord, ArrayList<String> otherArgs, String output) {
//		this.xCoord = xCoord;
//		this.yCoord = yCoord;
//		this.otherArgs = otherArgs;
//		this.output = output;
//	}
//
//	@Test
//	public void stringOutputTest() {
//		DataLine line = new DataLine("name", xCoord, yCoord, otherArgs, "Node", "Node", "");
//		assertEquals(line.toString(),output);
//	}
//}