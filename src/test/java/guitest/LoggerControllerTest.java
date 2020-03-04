package guitest;

import GUI.LoggerController;
import dataprocessors.Debugger;
import datastructures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerControllerTest {
    private HashMap<String, Node> nodeMap;
    private List<Node> nodeList;

    @BeforeEach
    public void setUp() {
        nodeList = new ArrayList<Node>();
        nodeList.add(new Node("Node1", 10f, 10f));
        nodeList.add(new Node("Node2", 10f, 10f));
        nodeList.add(new Node("Node3", 10f, 10f));

        nodeMap = new HashMap<String, Node>();
        for (Node node : nodeList) {
            nodeMap.put(node.getName(), node);
        }
    }

    @Test
    public void testLogAddNode() {
        nodeMap.get("Node1").addNeighbour(nodeList.get(1));
        Debugger debugger = new Debugger(nodeMap);

        String loggerOutput = "Node1 added to become a neighbour for Node2.";
        final String cleanOutput = debugger.getLog().get(0).replaceAll("\n", "");

        assertEquals(loggerOutput, cleanOutput);
    }

    @Test
    public void testLogRemoveNode() {
        Debugger debugger = new Debugger(nodeMap);
        
        String loggerOutput = "Node1 has no neighbour, so it was removed.";
        final String cleanOutput = debugger.getLog().get(0).replaceAll("\n", "");

        assertEquals(loggerOutput, cleanOutput);
    }

    @Test
    public void testMultipleAddNodes() {
        nodeMap.get("Node1").addNeighbour(nodeList.get(1));
        nodeMap.get("Node2").addNeighbour(nodeList.get(2));

        Debugger debugger = new Debugger(nodeMap);

        ArrayList<String> output = new ArrayList<>();
        ArrayList<String> logger;
        output.add("Node1 added to become a neighbour for Node2.");
        output.add("Node2 added to become a neighbour for Node3.");

        logger = debugger.getLog();

        assertEquals(output.size(), logger.size());

        for (int i = 0; i < output.size(); i++) {

            String loggerOutput = logger.get(i).replaceAll("\n", "");
            String testOutput = output.get(i).replaceAll("\n", "");

            assertEquals(testOutput , loggerOutput);
        }
    }
}
