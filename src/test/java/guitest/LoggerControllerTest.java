package guitest;

import dataprocessors.Debugger;
import dataprocessors.XMLDebugger;
import datastructures.Node;
import datastructures.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test whether the changing of the wrangled file via the debugger is correctly
 * shown in the log.
 */
public class LoggerControllerTest {
    private LinkedHashMap<String, Node> nodeMap;
    private List<Node> nodeList;

    @BeforeEach
    public void setUp() {
        nodeList = new ArrayList<>();
        nodeList.add(new Node("Node1", 10f, 10f));
        nodeList.add(new Node("Node2", 10f, 10f));
        nodeList.add(new Node("Node3", 10f, 10f));

        nodeMap = new LinkedHashMap<>();

        for (Node node : nodeList) {
            nodeMap.put(node.getName(), node);
        }
    }

    /**
     * Test whether the addition of a neighbour for a node gets logged.
     */
    @Test
    public void testLogAddNode() {
        nodeMap.get("Node1").addNeighbour(nodeList.get(1));

        Debugger debugger = new Debugger(nodeMap);

        String expectedOutput = "Node1 added to become a neighbour for Node2.";
        final String loggerOutput = debugger.getLog().get(0).replaceAll("\n", "");

        assertEquals(expectedOutput, loggerOutput);
    }

    /**
     * Test whether the removal of a node if it has no neighbour gets logged.
     */
    @Test
    public void testLogRemoveNode() {
        Debugger debugger = new Debugger(nodeMap);
        
        String expectedOutput = "Node1 has no neighbour, so it was removed.";
        final String loggerOutput = debugger.getLog().get(0).replaceAll("\n", "");

        assertEquals(expectedOutput, loggerOutput);
    }

    /**
     * Test whether multiple addition of neighbours to a node gets logged.
     */
    @Test
    public void testMultipleAddNodes() {
        //add neighbour for Node1.
        nodeMap.get("Node1").addNeighbour(nodeList.get(1));

        //add neighbour for Node2.
        nodeMap.get("Node2").addNeighbour(nodeList.get(2));

        Debugger debugger = new Debugger(nodeMap);

        ArrayList<String> expected = new ArrayList<>();     //expected output for log.
        ArrayList<String> logger;                           //actual output for log.

        expected.add("Node1 added to become a neighbour for Node2.");
        expected.add("Node2 added to become a neighbour for Node3.");

        logger = debugger.getLog();

        assertEquals(expected.size(), logger.size());

        for (int i = 0; i < expected.size(); i++) {

            String loggerOutput = logger.get(i).replaceAll("\n", "");
            String expectedOutput = expected.get(i).replaceAll("\n", "");

            assertEquals(expectedOutput , loggerOutput);
        }
    }

    /**
     * Test whether multiple removal of nodes if it has no neighbours gets logged.
     */
    @Test
    public void testMultipleRemoveNodes() {
        Debugger debugger = new Debugger(nodeMap);

        ArrayList<String> expected = new ArrayList<>();     //expected output for log.
        ArrayList<String> logger;                           //actual output for log.

        expected.add("Node1 has no neighbour, so it was removed.");
        expected.add("Node2 has no neighbour, so it was removed.");
        expected.add("Node3 has no neighbour, so it was removed.");

        logger = debugger.getLog();

        assertEquals(expected.size(), logger.size());

        for (int i = 0; i < expected.size(); i++) {

            String loggerOutput = logger.get(i).replaceAll("\n", "");
            String expectedOutput = expected.get(i).replaceAll("\n", "");

            assertEquals(expectedOutput , loggerOutput);
        }
    }

    /**
     * Test whether the XML debugger log the new line.
     */
    @Test
    public void testRemoveUninitialisedNeighbours() {
        
        ArrayList<String> expected = new ArrayList<>();     //expected output for log.
        ArrayList<String> logger;                           //actual output for log.
        
        expected.add("The neighbour " + "NodeA" + " has not been initialised, so it was removed from " + "Node1" + " neighbours.\n");
        expected.add("The neighbour " + "NodeB" + " has not been initialised, so it was removed from " + "Node1" + " neighbours.\n");
        expected.add("The neighbour " + "NodeA" + " has not been initialised, so it was removed from " + "Node2" + " neighbours.\n");
        expected.add("The neighbour " + "NodeB" + " has not been initialised, so it was removed from " + "Node2" + " neighbours.\n");
        expected.add("The neighbour " + "NodeA" + " has not been initialised, so it was removed from " + "Node3" + " neighbours.\n");
        expected.add("The neighbour " + "NodeB" + " has not been initialised, so it was removed from " + "Node3" + " neighbours.\n");
        
		nodeMap.get("Node1").setNeighbours(new ArrayList<Node>(Arrays.asList(new Node("NodeA",Status.ONLY_NEIGHBOUR),new Node("NodeB",Status.ONLY_NEIGHBOUR))));
		nodeMap.get("Node2").setNeighbours(new ArrayList<Node>(Arrays.asList(new Node("NodeA",Status.ONLY_NEIGHBOUR),new Node("NodeB",Status.ONLY_NEIGHBOUR))));
        nodeMap.get("Node3").setNeighbours(new ArrayList<Node>(Arrays.asList(new Node("NodeA",Status.ONLY_NEIGHBOUR),new Node("NodeB",Status.ONLY_NEIGHBOUR))));
        
        // Add neighbour for node 1
		nodeMap.get("Node1").addNeighbour(nodeList.get(1));
		nodeMap.get("Node1").addNeighbour(nodeList.get(2));
		
		// Add neighbour for node 2
		nodeMap.get("Node2").addNeighbour(nodeList.get(0));
        
        // Add neighbour for node 3
		nodeMap.get("Node3").addNeighbour(nodeList.get(0));
        
        XMLDebugger XMLdebugger = new XMLDebugger(nodeMap);
        logger = XMLdebugger.getLog();

        assertEquals(expected.size(), logger.size());

        for (int i = 0; i < expected.size(); i++) {

            String loggerOutput = logger.get(i).replaceAll("\n", "");
            String expectedOutput = expected.get(i).replaceAll("\n", "");

            assertEquals(expectedOutput , loggerOutput);
        }
    }
}

// The neighbour NodeA has not been initialised, so it was removed from Node2 neighbours.>
// The neighbour NodeA has not been initialised, so it was removed from Node3 neighbours.>