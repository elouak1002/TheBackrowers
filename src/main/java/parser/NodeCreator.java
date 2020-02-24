package parser;

import datastructures.Node;
import javafx.util.Pair;
import java.util.*;
import java.util.stream.Collectors;

public class NodeCreator {

    private TreeMap<String, Node> nodeMap;
    private List<String> neighbourLines;
    private List<String> dataLines;
    private int lastID;


    public NodeCreator(List<String> dataLines, List<String> neighbourLines){
        lastID = 0;
        this.dataLines = dataLines;
        this.neighbourLines = neighbourLines;
        nodeMap = new TreeMap<>();
    }

    /**
     * Method to populate the hashMap with Node objects, mapping each to its name;
     * Assigns ids to each node
     */

    public TreeMap<String, Node> createNodes() {
        for(String line : dataLines){
            String name = extractName(line);
            String type = extractType(line);
            String specialTrait  = extractSpecialTrait(line);
            int floor = extractFloor(line);
            Pair<Float, Float> coordinates = extractData(line);
            int nodeId = lastID++;
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue());
            node.setId(nodeId);
            node.setType(type);
            node.setFloor(floor);
            assignSpecialTrait(node,specialTrait);
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
                    case "ToiletType.Male": //TODO Verify numbers with client
                        specialType = "1";
                        break;
                    case "ToiletType.Female":
                        specialType = "2";
                        break;
//                    case "ToiletType.Disabled":
//                        specialType = "3";
//                        break;
//                    case "ToiletType.Neutral":
//                        specialType = "4";
//                        break;
                    default: break;
                }
                break;
            case "FloorChanger":
                switch (specialTrait) {
                    case "FloorChangerType.Stairs":
                        specialType = "1";
                        break;
                    case "FloorChangerType.Lift":
                        specialType = "2"; //TODO Verify numbers with client
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
            if (nodeMap.containsKey(nodeName)) {
                List<Node> neighbours = extractNeighbours(line).stream().map(name -> nodeMap.get(name)).filter(Objects::nonNull).collect(Collectors.toList());
                nodeMap.get(nodeName).setNeighbours(neighbours);
            }
        }
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
     *
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
            //System.out.println(parsedLine[parsedLine.length-1]);
            return parsedLine[parsedLine.length - 1];
        } else {
            return "";
        }
    }


    public int extractFloor(String line){
        return Integer.parseInt(line.substring(line.indexOf('_')+1, line.indexOf('_' )+3));
    }

}


