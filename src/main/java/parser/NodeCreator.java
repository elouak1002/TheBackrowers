package parser;

import datastructures.Node;
import datastructures.Status;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class NodeCreator {

    private TreeMap<String, Node> nodeMap;
    private List<String> neighbourLines;
    private List<String> dataLines;
    private ArrayList<String> nodesAsPerInsertion =new ArrayList<>(); //nodes in insertion order
    private static int initialId = 0; //initial id, nodes always start from 0
    private int lastUsedId; //to keep track of the last used id and assure uniqueness

    /**
     * Constructor for the node creator class that is responsible
     * for extracting node data from input lines and creating the Node objects
     *
     * @param dataLines input lines containing node data
     * @param neighbourLines input lines containing node's neighbours data
     */
    public NodeCreator(List<String> dataLines, List<String> neighbourLines){ //add Path to the arguments
        this.dataLines = dataLines;
        this.neighbourLines = neighbourLines;
        nodeMap = new TreeMap<>();
    }

    /**
     * Method to populate the hashMap with Node objects, mapping each to its name;
     * Assigns ids to each node
     */

    public TreeMap<String, Node> createNodes() {
        int reserveIDs = 0;
        lastUsedId =initialId;
        for(String line : dataLines){
            String name = extractName(line);
            String type = extractType(line);
            String specialTrait  = extractSpecialTrait(line);
            int floor = extractFloor(line);
            Pair<Float, Float> coordinates = extractData(line);
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue());
            try{
                node.setId(lastUsedId);
                lastUsedId+=1;
            }
            catch (Exception e){
                node.setId(reserveIDs);
                reserveIDs += 1;
            }
            node.setType(type);
            node.setFloor(floor);
            assignSpecialTrait(node,specialTrait);
            this.nodesAsPerInsertion.add(node.getName());
            nodeMap.put(name, node);

        }
        setNeighbours();
        return nodeMap;
    }

    /**
     * Assigns special trait based on the nodes type aka room, toilet, floorChanger
     * @param node
     */
    private void assignSpecialTrait(Node node,String specialTrait) {
        String type = node.getType();
        String specialType = "None";
        switch (type){
            case "Room":
                specialType = specialTrait;
                break;
            case "Toilet":
                switch (specialTrait){
                    case "Default":
                        specialType = "0";
                        break;
                    case "ToiletType.Male":
                        specialType = "1";
                        break;
                    case "ToiletType.Female":
                        specialType = "2";
                        break;
                    case "ToiletType.Disabled":
                        specialType = "3";
                        break;
                    case "ToiletType.MaleAndFemale":
                        specialType = "4";
                        break;
                    case "ToiletType.Male_FemaleAndDisabled":
                        specialType = "5";
                        break;
                    case "ToiletType.GenderNeutral":
                        specialType = "6";
                        break;
                    case "ToiletType.Everything":
                        specialType = "7";
                        break;
                    default: break;
                }
                break;
            case "FloorChanger":
                switch (specialTrait) {
                    case "FloorChangerType.Stairs":
                        specialType = "1";
                        break;
                    case "FloorChangerType.Lift":
                        specialType = "2";
                        break;
                    default: break;
                }
                break;
            default: break;
        }
        node.setSpecialType(specialType);
    }

    /**
     * Set the neighbours list for each node in the node map.
     */
    private void setNeighbours() {
        for (String line : neighbourLines) {
            String nodeName = extractNodeFromNeighboursLine(line);

            List<Node> neighbours = getNodeNeighbours(line);

            if (!nodeMap.containsKey(nodeName)) {
                Node node = new Node(nodeName,Status.UNINITIALISED);
                nodeMap.put(nodeName, node);
            }
            nodeMap.get(nodeName).setNeighbours(neighbours);
        }
    }

    /**
     * @return a Node's list of neighbours declared in a neighbour line.
     */
    private List<Node> getNodeNeighbours(String neighbourLine) {
        return extractNeighbours(neighbourLine).stream()
                                        .map(name -> nodeMap.containsKey(name) ? nodeMap.get(name) : new Node(name,Status.ONLY_NEIGHBOUR))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());
    }

    /**
     * @return The node map with node and neighbours set.
     */
    public TreeMap<String,Node> getNodes() {
        return nodeMap;
    }

    /**
     * @param line a line that set the neighbours of a node
     * @return The name of the node to which the neighbours are added in the file line.
     */
    public String extractNodeFromNeighboursLine(String line) {
        return line.substring(0, line.indexOf("."));
    }

    /**
     * @param line a line that set the neighbours of a node
     * @return A list of the neighbours added to the node.
     */
    private List<String> extractNeighbours(String line) {
        return Arrays.asList(line.substring(line.indexOf("{") + 1, line.indexOf("}")).split(",")).stream().map(String::trim).collect(Collectors.toList());
    }


    /**
     * Method to extract the coordinates of a node from a line
     * @param line to extract the data from
     * @return Node's coordinates enclosed in a Pair object
     */
    public Pair<Float, Float> extractData(String line) {
        String dataString = line.substring(line.indexOf('(')+1, line.indexOf(')'));

        List<String> dataList = new ArrayList<>(Arrays.asList(dataString.trim().split(" , ")));

        return new Pair<>(Float.parseFloat(dataList.get(0)), Float.parseFloat(dataList.get(1)));
    }

    /**
     * Method to extract the name of a node from a line
     * @param line to extract data from
     * @return Node's name
     */
    public String extractName(String line){ return line.substring(0,line.indexOf("=")).split(" ")[1]; }

    /**
     * Method to extract the type of a node from a line
     * @param line to extract data from
     * @return Node's type aka Node, Room, Elevator etc.
     */
    public String extractType(String line){ return line.substring(0,line.indexOf("=")).split(" ")[0]; }

    /**
     * Method to extract the special trait from a line
     * @param line to extract data from
     * @return Node's special trait aka type of room, type of toilet, name of room etc.
     */
    public String extractSpecialTrait(String line) {
        String[] parsedLine = line.substring(line.indexOf('(') + 1, line.indexOf(')')).trim().split(", ");
        if (parsedLine.length > 3) {
            return parsedLine[parsedLine.length - 1];
        } else {
            return "";
        }
    }

    /**
     * Method to extract the node's floor from a line
     * @param line to extract data from
     * @return Node's floor
     */
    public int extractFloor(String line){
        return Integer.parseInt(line.substring(line.indexOf('_')+1, line.indexOf('_' )+3));
    }
    
    /**
     * A method to get the nodes in the order they were inserted
     * @return  ArrayList the names of the nodes in their order
     */
    public ArrayList<String> getNodesAsPerInsertion() {
        return nodesAsPerInsertion;
    }
}


