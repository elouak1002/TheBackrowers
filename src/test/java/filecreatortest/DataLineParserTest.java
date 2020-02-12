package filecreatortest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import filecreator.DataLineParser;

/**
 * Test that each part of the parser is working as expected.
 */
@RunWith(Parameterized.class)
public class DataLineParserTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 ); // Test", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList("GuysHeights.HenRaph_04")), "; // Test"},
			{"Node HenRaph_04_493_264 = new Node( 49.312683f );", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "", new ArrayList<String>(Arrays.asList()), ";"},
			{"Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f );", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList()), ";"},
			{"Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 , GuysHeights.HenRaph_04 , GuysHeights.HenRaph_04 ); // Test", "Node", "HenRaph_04_493_264", "Node", "49.312683f", "26.463207f", new ArrayList<String>(Arrays.asList("GuysHeights.HenRaph_04","GuysHeights.HenRaph_04","GuysHeights.HenRaph_04")), "; // Test"},
		});
	}

	private DataLineParser parser;

	private String inputLine;
	private String staticType;
	private String dataName;
	private String dynamicType;
	private String xCoord;
	private String yCoord;
	private List<String> otherArgList;
	private String endPart;
	public DataLineParserTest(String inputLine, String staticType, String dataName, String dynamicType, String xCoord, String yCoord, List<String> otherArgList, String endPart) {
		this.inputLine = inputLine;
		this.staticType = staticType;
		this.dataName = dataName;
		this.dynamicType = dynamicType;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.otherArgList = otherArgList;
		this.endPart = endPart;
	}

	@Before
	public void createParser() {
		parser = new DataLineParser();
	}

	@Test
	public void testDataLineCreation() {
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