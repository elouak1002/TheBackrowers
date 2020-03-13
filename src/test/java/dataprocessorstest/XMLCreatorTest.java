package dataprocessorstest;

import dataprocessors.XMLCreator;
import datastructures.Node;
import org.junit.jupiter.api.Test;
import parser.XMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class XMLCreatorTest {

    private List<String> paths = Arrays.asList("src/test/resources/fullInputData.txt","src/test/resources/XMLParserTestData1.txt", "src/test/resources/XMLParserTestData2.txt");

    @Test
    public void createsHeaderInCorrectFormat() throws IOException {

        XMLParser parser = new XMLParser(paths);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);
        DateFormat df=new SimpleDateFormat("YYYY");
        xmlc.createHeader();

        assertEquals(xmlc.getFinalXMLData().get(1).contains(df.format(new Date())),true);
    }
    @Test
    public void setsEncodingInCorrectFormat() throws IOException {

        XMLParser parser = new XMLParser(paths);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);
       String encoding ="utf-8";
        xmlc.createHeader();

        assertEquals(xmlc.getFinalXMLData().get(0).contains(encoding),true);

    }
    @Test
    public void createsFooterInCorrectFormat() throws IOException {

        XMLParser parser = new XMLParser(paths);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);
        String footer = "</MappinData>";
        xmlc.createFooter();

        assertEquals(xmlc.getFinalXMLData().get(xmlc.getFinalXMLData().size()-1).equals(footer),true);

    }
    @Test
    public void parsesNodeInCorrectFormat() throws IOException {
        //setup
        XMLParser parser = new XMLParser(paths);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);


        //node creation
        Node simpleNode = new Node("node1",49.312683f , 26.463207f);
        simpleNode.setFloor(4);  simpleNode.setType("node");
        Node roomNode = new Node("room1",49.312683f , 26.463207f);
        roomNode.setFloor(5); roomNode.setType("room"); roomNode.setSpecialType("HR \"4.2");
        Node toiletNode = new Node("toilet1",49.312683f , 26.463207f);
        toiletNode.setFloor(6); toiletNode.setType("toilet"); toiletNode.setSpecialType("Female");
        Node floorChangerNode = new Node("floorChanger1",49.312683f , 26.463207f);
        floorChangerNode.setFloor(7); floorChangerNode.setType("floorchanger"); floorChangerNode.setSpecialType("Stairs");
        //testing creator
        xmlc.addXMLentry(simpleNode);
        xmlc.addXMLentry(roomNode);
        xmlc.addXMLentry(toiletNode);
        xmlc.addXMLentry(floorChangerNode);
        List<String> expectedOutPutNode = Arrays.asList(" <node id=\"0\" x=\"49.312683\" y=\"26.463207\" Floor=\"4\">"," </node>");
        List<String> expectedOutPutRoom =Arrays.asList(" <room id=\"0\" x=\"49.312683\" y=\"26.463207\" Floor=\"5\" type=\"HR \"4.2\">"," </room>");
        List<String> expectedOutPutToilet = Arrays.asList(" <toilet id=\"0\" x=\"49.312683\" y=\"26.463207\" Floor=\"6\" type=\"Female\">"," </toilet>");
        List<String> expectedOutPutFloorChanger =Arrays.asList(" <floorchanger id=\"0\" x=\"49.312683\" y=\"26.463207\" Floor=\"7\" type=\"Stairs\">"," </floorchanger>");
        //tests
        assertEquals(xmlc.getFinalXMLData().contains(expectedOutPutNode.get(0)),true);
        assertEquals(xmlc.getFinalXMLData().contains(expectedOutPutRoom.get(0)),true);
        assertEquals(xmlc.getFinalXMLData().contains(expectedOutPutToilet.get(0)),true);
        assertEquals(xmlc.getFinalXMLData().contains(expectedOutPutFloorChanger.get(0)),true);
    }
    @Test
    public void finalFileMathesExpectations() throws IOException {
        XMLParser parser = new XMLParser(paths);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);
        List<String> finalFile = Files.readAllLines(Paths.get("src/test/resources/XMLexample.txt"));
        for(int i = 2;i<finalFile.size();i++){
            assertEquals(finalFile.get(i), xmlc.createXMLFile().get(i));
        }

    }
}
