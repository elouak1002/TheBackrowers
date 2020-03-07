package guitest;

import GUI.LoggerController;
import dataprocessors.Debugger;
import datastructures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test whether the changing of the wrangled file via the debugger is correctly
 * shown in the log.
 */
public class LoggerControllerTest {
    private HashMap<String, Node> nodeMap;
    private List<Node> nodeList;

    @BeforeEach
    public void setUp() {
        nodeList = new ArrayList<>();
        nodeList.add(new Node("Node1", 10f, 10f));
        nodeList.add(new Node("Node2", 10f, 10f));
        nodeList.add(new Node("Node3", 10f, 10f));

        nodeMap = new HashMap<>();

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
        expected.add("Node3 has no neighbour, so it was removed.");
        expected.add("Node2 has no neighbour, so it was removed.");

        logger = debugger.getLog();

        assertEquals(expected.size(), logger.size());

        for (int i = 0; i < expected.size(); i++) {

            String loggerOutput = logger.get(i).replaceAll("\n", "");
            String expectedOutput = expected.get(i).replaceAll("\n", "");

            assertEquals(expectedOutput , loggerOutput);
        }
    }
}
