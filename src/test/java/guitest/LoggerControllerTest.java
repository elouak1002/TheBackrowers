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

        nodeMap = new HashMap<String, Node>();
        for (Node node : nodeList) {
            nodeMap.put(node.getName(), node);
        }
    }
    
    @Test
    public void testlogAddNode() {
        nodeMap.get("Node1").addNeighbour(nodeList.get(1));
        Debugger debugger = new Debugger(nodeMap);
        String result = "Node1 added to become a neighbour for Node2.";
        final String cleanOutput = debugger.getLog().get(0).toString().replaceAll("\n", "").replaceAll("\r", "");
        assertEquals(result, cleanOutput);
    }
}
