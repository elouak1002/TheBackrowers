package parser;

import datastructures.Node;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class NodeCreator {

    private TreeMap<String, Node> nodeMap;
    private List<String> neighbourLines;
    private List<String> dataLines;
    //Path to the log file
    private Path idLogFilePath = Paths.get("src/main/java/parser/logs/idLog.txt");
    //ArrayList of used ids
    private ArrayList<Integer> usedIds;
    private ArrayList<String> nodesAsPerInsertion =new ArrayList<>(); //nodes in insertion order


    /**
     * Constructor for the node creator class that is responsible
     * for extracting node data from input lines and creating the Node objects
     *
     * @param dataLines input lines containing node data
     * @param neighbourLines input lines containing node's neighbours data
     */
    public NodeCreator(List<String> dataLines, List<String> neighbourLines){ //add Path to the arguments
        usedIds = new ArrayList<>();
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
        try{
            clearIDLog();
        }
        catch (Exception e ){
            System.out.println("An exception has occurred while clearing an ID list");
        }
        for(String line : dataLines){
            String name = extractName(line);
            String type = extractType(line);
            String specialTrait  = extractSpecialTrait(line);
            int floor = extractFloor(line);
            Pair<Float, Float> coordinates = extractData(line);
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue());
            try{
                node.setId(generateNodeId(idLogFilePath));
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

    /**
     * Method to extract the node's floor from a line
     * @param line to extract data from
     * @return Node's floor
     */
    public int extractFloor(String line){
        return Integer.parseInt(line.substring(line.indexOf('_')+1, line.indexOf('_' )+3));
    }

    /**
     * Generates a unique integer id each time it is called.
     * @param idLogFilePath path to the file
     * @return int unique id
     * @throws IOException
     */
    public int generateNodeId(Path idLogFilePath) throws IOException {
        int lastUsedId=getLastUsedID(idLogFilePath);
        usedIds.add(lastUsedId+1);
        saveUsedIds(usedIds,idLogFilePath);
        return lastUsedId;
    }

    /**
     * Goes through the log file and extracts the last recorded
     * id so we know from which one to start generating new ones.
     * @param idLogFilePath path to the log file
     * @return int last used id, 0 if the log file is empty
     * @throws IOException if the input is illegal
     */
    public int getLastUsedID(Path idLogFilePath) throws IOException {
        List<String> listLines = Files.readAllLines(idLogFilePath);
        if(listLines.isEmpty()){
            return 0;
        }
        String lastLine =listLines.get(listLines.size() - 1).replace("[","").replace("]","").trim();
        List<String> numbersInFilteredLine = Arrays.asList(lastLine.trim().split(","));
        int last = Integer.parseInt(numbersInFilteredLine.get(numbersInFilteredLine.size()-1).trim());
        return last;
    }

    /**
     * Writes ids from the last wrangled file to the idLog.txt so
     * that we can generate unique ones for the next file
     * @param usedIds to write to the files
     * @param idLogFilePath path to the log file
     * @throws IOException
     */
    public void saveUsedIds(ArrayList<Integer> usedIds, Path idLogFilePath) throws IOException{
        Files.write(idLogFilePath,usedIds.toString().getBytes());
    }

    /**
     * A method to clear the ID log text file
     * @throws IOException
     */
    private void clearIDLog() throws IOException {
        Files.write(idLogFilePath,"".getBytes());
    }

    /**
     * A method to get the nodes in the order they were inserted
     * @return  ArrayList the names of the nodes in their order
     */
    public ArrayList<String> getNodesAsPerInsertion() {
        return nodesAsPerInsertion;
    }
}


