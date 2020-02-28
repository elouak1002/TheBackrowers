package dataprocessors;

import datastructures.Node;
import parser.Parser;
import parser.XMLParser;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XMLCreator{
    private List<String> data;
    private List<String> finalXMLData;
    private TreeMap<String, Node> nodes;
    private ArrayList<String> nodeOrder;

    public XMLCreator(TreeMap<String, Node> nodes, ArrayList<String> nodeOrder){
        this.nodes = nodes;
        this.nodeOrder = nodeOrder;
        finalXMLData = new ArrayList<>();
    }

    public List<String> createXMLFile() throws IOException {
        createHeader();
        createNodeContent();
        createFooter();
        return finalXMLData;
    }

    private void createFooter() {
        String footer = "</MappinData>";
        this.finalXMLData.add(footer);
    }

    private void createNodeContent() {
        for(String nodename: nodeOrder){
            addXMLentry(nodes.get(nodename));
        }

    }

    private void addXMLentry(Node node) {
        String xmlLine = " <" + node.getType().toLowerCase() + " id=" + "\"" + node.getId() + "\""
        + " x=" + "\""+node.getX() + "\"" + " y=" + "\""+node.getY()+"\""
                + " Floor=" + "\"" + node.getFloor() + "\"";

        if(node.hasSpecialType()){
            if (node.getType().equals("room")){
                xmlLine += " name=" + "\"" + node.getSpecialType() + "\"";
            }
            else {
                xmlLine += " type=" + "\"" + node.getSpecialType() + "\"";
            }
        }
        xmlLine += ">";
        finalXMLData.add(xmlLine);
        for (Node neighbour : node.getNeighbours()){
            finalXMLData.add("  <neighbour id="+"\""+neighbour.getId()+"\"" +"/>");
        }
        finalXMLData.add(" </"+node.getType().toLowerCase() +">");
    }

    private void createHeader() {
        this.finalXMLData.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
        LocalTime timeObj= LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = formatter.format(timeObj);
        df=new SimpleDateFormat("YYYY");
        String documentTag = "<MappinData version=\"1.0\" created=\""+date+" "+time+ "\" copyright=\"Mappin Technologies LTD\" "+ df.format(new Date())+">";
        this.finalXMLData.add(documentTag);
    }

    public static void main(String[] args) throws IOException {
        List<String> path = new ArrayList<>();
        path.add("src/test/resources/fullInputData.txt");
        XMLParser parser = new XMLParser(path);
        TreeMap<String, Node> nodes = parser.createNodes();
        ArrayList<String> order = parser.getNodeOrder();
        XMLCreator xmlc = new XMLCreator(nodes, order);
        List<String> data  = xmlc.createXMLFile();
        for(String line : data){
            System.out.println(line);
        }

    }
}