package linecreatorstest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;

import linecreators.FileLinesCreator;
import datastructures.Node;

/**
* Test the String creation of the Data Line, especially the arguments creation.
* @version 12.02.2020
*/
public class FileLinesCreatorTest {

	private LinkedHashMap<String, Node> nodeMap;
	private List<Node> nodeList;

	@BeforeEach
	private void setUp() {
		nodeList = new ArrayList<Node>();
		nodeList.add(new Node("Node1", 1.0f, 2.0f));
		nodeList.add(new Node("Node2", 10.0f, 20.0f));
		nodeList.add(new Node("Node3", 14.0f, 24.0f));
		nodeList.add(new Node("Node4", 7.0f, 27.0f));
		nodeList.add(new Node("Node5", 32.1f, 27.19f));

		nodeMap = new LinkedHashMap<String, Node>();
		for (Node node : nodeList) {
			nodeMap.put(node.getName(), node);
		}
	}

	@Test
	public void processOutputFileTest() throws IOException {
		
		// Set type of each Node
		nodeMap.get("Node1").setType("Node");
		nodeMap.get("Node2").setType("Room");
		nodeMap.get("Node3").setType("Toilet");
		nodeMap.get("Node4").setType("FloorChanger");
		nodeMap.get("Node5").setType("FloorChanger");

		// Add neighbour for node 1
		nodeMap.get("Node1").addNeighbour(nodeList.get(1));
		nodeMap.get("Node1").addNeighbour(nodeList.get(3));

		// Add neighbour for node 2
		nodeMap.get("Node2").addNeighbour(nodeList.get(2));

		// Add neighbour for node 3
		nodeMap.get("Node3").addNeighbour(nodeList.get(0));
		nodeMap.get("Node3").addNeighbour(nodeList.get(1));

		// Add neighbour for node 4
		nodeMap.get("Node4").addNeighbour(nodeList.get(0));
		nodeMap.get("Node4").addNeighbour(nodeList.get(1));

		List<String> output = new ArrayList<String>(Arrays.asList(
			"// Data of Mappin Technologies LTD 2019",
			"// Thu Aug  1 11:48:39 2019",
			"",
			"",
			"Node Node1 = new Node( 1.0f , 2.0f , GuysHeights.HenRaph_04 );",
			"Room Node2 = new Room( 10.0f , 20.0f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
			"Toilet Node3 = new Toilet( 14.0f , 24.0f , GuysHeights.HenRaph_04 , ToiletType.Female );",
			"FloorChanger Node4 = new FloorChanger( 7.0f , 27.0f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
			"FloorChanger Node5 = new FloorChanger( 32.1f , 27.19f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
			"",
			"Node1.addAllNeighbours( new List<Node>{ Node2 , Node4 } );",
			"Node2.addAllNeighbours( new List<Node>{ Node3 } );",
			"Node3.addAllNeighbours( new List<Node>{ Node1 , Node2 } );",
			"Node4.addAllNeighbours( new List<Node>{ Node1 , Node2 } );",
			"",
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
			" , Node4 , Node5"
			));

			FileLinesCreator fileCrea = new FileLinesCreator(nodeMap,Paths.get("src/test/resources/testFileLinesCreatorData.txt"));

			assertEquals(fileCrea.getOutputFile(),output); // Need to be modified when wrangler will be added.
	}
}

// expected: <[// Data of Mappin Technologies LTD 2019, // Thu Aug  1 11:48:39 2019, , , Node Node1 = new Node( 1.0f , 2.0f , GuysHeights.HenRaph_04 );, Room Node2 = new Room( 10.0f , 20.0f , GuysHeights.HenRaph_04 , "HR 4.2" );, Toilet Node3 = new Toilet( 14.0f , 24.0f , GuysHeights.HenRaph_04 , ToiletType.Female );, FloorChanger Node4 = new FloorChanger( 7.0f , 27.0f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );, FloorChanger Node5 = new FloorChanger( 32.1f , 27.19f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2, , Node1.addAllNeighbours( new List<Node>{ Node2 , Node4 } );, Node2.addAllNeighbours( new List<Node>{ Node3 } );, Node3.addAllNeighbours( new List<Node>{ Node1 , Node2 } );, Node4.addAllNeighbours( new List<Node>{ Node1 , Node2 } );, , ====== NODE LISTS =======, , // null:,  , Node1 , Node2 , Node3 , Node4 , Node5]> 
// but was:  <[// Data of Mappin Technologies LTD 2019, // Thu Aug  1 11:48:39 2019, , , Node Node1 = new Node( 1.0f , 2.0f , GuysHeights.HenRaph_04 );, Room Node2 = new Room( 10.0f , 20.0f , GuysHeights.HenRaph_04 , "HR 4.2" );, Toilet Node3 = new Toilet( 14.0f , 24.0f , GuysHeights.HenRaph_04 , ToiletType.Female );, FloorChanger Node4 = new FloorChanger( 7.0f , 27.0f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );, FloorChanger Node5 = new FloorChanger( 32.1f , 27.19f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2, , Node1.addAllNeighbours( new List<Node>{ Node2 , Node4 } );, Node2.addAllNeighbours( new List<Node>{ Node3 } );, Node3.addAllNeighbours( new List<Node>{ Node1 , Node2 } );, Node4.addAllNeighbours( new List<Node>{ Node1 , Node2 } );, , ====== NODE LISTS =======, , // Nodes:,  , Node1 , Node2 , Node3 , Node4 , Node 5]>
