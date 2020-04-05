package dataprocessors;

import datastructures.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The xmlCreator class takes a map of Nodes and their order
 * and creates a new List of string lines which can be written to an xml file.
 */
public class XMLCreator{
    private List<String> finalXMLData;
    private TreeMap<String, Node> nodes;
    private ArrayList<String> nodeOrder;

    /**
     * Constructor of the xmlCreator class - takes a map of Nodes and an arrayList of their order .
     * @param nodes - map of Node objects mapped to strings
     * @param nodeOrder - ArrayList of the nodes which gives the input order
     */
    public XMLCreator(TreeMap<String, Node> nodes, ArrayList<String> nodeOrder){
        this.nodes = nodes;
        this.nodeOrder = nodeOrder;
        finalXMLData = new ArrayList<>();
    }

    /**
     * This method when called fills the empty finalXMLData list
     * with the xml string lines.
     * @return a list of String
     */
    public List<String> createXMLFile() {
        createHeader();
        createNodeContent();
        createFooter();
        return finalXMLData;
    }

    /**
     * Creates the final line of the xml file.
     */
    public void createFooter() {
        String footer = "</MappinData>";
        this.finalXMLData.add(footer);
    }

    /**
     * Makes sure each node is parsed into correct xml format.
     */
    public void createNodeContent() {
        reAssignIds(nodes);
        for(String nodename: nodeOrder){
            addXMLentry(nodes.get(nodename));
        }

    }

    /**
     * Method that takes the node map as a parameter and reassigns the id's after the debugging has taken place
     * @param nodes the map of all the nodes
     */
    private void reAssignIds(TreeMap<String, Node> nodes) {
        int initialId = 0;
        for(String nodeName: nodeOrder){
            nodes.get(nodeName).setId(initialId);
            initialId+=1;
        }
    }

    /**
     * Method that takes as a parameter a node and converts it into an xml line.
     * Then adds that line to the finalXMLData arrayList.
     * @param node the node to be transformed
     */
    public void addXMLentry(Node node) {
        String xmlLine = " <" + node.getType().toLowerCase() + " id=" + "\"" + node.getId() + "\""
        + " x=" + "\""+node.getX() + "\"" + " y=" + "\""+node.getY()+"\""
                + " Floor=" + "\"" + node.getFloor() + "\"";

        if(node.hasSpecialType()){
            if (node.getType().equals("Room")){
                xmlLine += " name=" + node.getSpecialType();
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

    /**
     * This method ensures that the file begins with the appropriate
     * date, time, year, xml version and encoding.
     */
    public void createHeader() {
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

    /**
     * This method returns the finalXmlData at any point in time.
     * Mostly used for testing purposes.
     * @return list of the final XML lines
     */
    public List<String> getFinalXMLData(){
        return finalXMLData;
    }

}