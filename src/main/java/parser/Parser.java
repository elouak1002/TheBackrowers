package parser;


import datastructures.Node;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;



/**	
 * Parser class is responsible for parsing lines from 	
 * an input file and creating a list of Node with the	
 * data from the file	
 */	
public class Parser {
    //Path to the input file
    private Path path;
    //Path to the log file
    private Path idLogFilePath = Paths.get("src/main/java/parser/logs/idLog.txt");
    //ArrayList of used ids
    private ArrayList<Integer> usedIds = new ArrayList<>();
    // Map of the node from the file.
    private TreeMap<String,Node> nodeMap;



    /**
     * Constructor for the Parser class
     * @param path Path to the input file
     */
    public Parser(Path path) {
        this.path = path;

        nodeMap = new TreeMap<>();
        try {
            clearIDLog(idLogFilePath);
            createNodes();
            setNeighbours();
        } catch (IOException e) {
        }
    }

    /**
     * @return List of lines that contain data
     * @throws IOException if the file was not found
     */
    public List<String> getLines() throws IOException {
        return filter(getAllLines());
    }

    /**
     * @return List of lines that contain neighbours data
     * @throws IOException if the file was not found
     */
    public List<String> getNeighboursLines() throws IOException {
        return filterNonNeighbours(getAllLines());
    }

    /**
     * @return All lines from the input file
     * @throws IOException if the file was not found
     */
    public List<String> getAllLines() throws IOException{ return Files.readAllLines(path); }

    /**
     * Filters out lines that do not contain data
     * @param lines List of lines from a file
     * @return List of lines with data
     */
    private List<String> filter(List<String> lines){
        lines.removeIf(line ->
                line.contains("NODE LISTS")
                        || line.startsWith("//")
                        || line.contains("addAllNeighbours")
                        || line.startsWith(" ")
                        || line.equals(""));

        return lines;
    }

    /**
     * Filters out lines that does not contain neighbours data
     * @param lines List of lines from a file.
     * @return List of lines with neighbours data.
     */
    private List<String> filterNonNeighbours(List<String> lines) {
        lines.removeIf(line ->
            !line.contains("addAllNeighbours")
        );

        return lines;
    }

	/**
	 * @return The position of the first data line in the input file,
     * -1 otherwise.
	 */
	public int beginOfDataLines() throws IOException {
        if (getLines().size() > 0) {
            return getAllLines().indexOf((getLines().get(0)));
        }
        return -1;
	}

    /**
	 * @return The position of the last data line in the input file,
     * -1 otherwise.
	 */
	public int endOfDataLines() throws IOException {
        return getAllLines().indexOf(getLines().get(getLines().size() - 1));
	}

    /**
	 * @return The position of the first neighbour line in the input file,
     * -1 otherwise.
	 */
	public int beginOfNeighbourLines() throws IOException {
        if (getNeighboursLines().size() > 0) {
            return getAllLines().indexOf((getNeighboursLines().get(0)));
        }
        return -1;
	}

    /**
	 * @return The position of the last neighbour line in the input file,
     * -1 otherwise.
	 */
	public int endOfNeighbourLines() throws IOException {
        return getAllLines().indexOf(getNeighboursLines().get(getNeighboursLines().size() - 1));
	}

    /**
     * Method to populate the hashMap with Node objects, mapping each to its name;
     * Assigns ids to each node
     */

    private void createNodes() throws IOException {
        List<String> lines = getLines();
        for(String line : lines){
            String name = extractName(line);
            String type = extractType(line);
            String specialTrait  = extractSpecialTrait(line);
            String floor = extractFloor(line);
            Pair<Float, Float> coordinates = extractData(line);
            int nodeId=generateNodeId(idLogFilePath);
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue(), floor);

            node.setId(nodeId);
            node.setType(type);
            assignSpecialTrait(node,specialTrait);
            nodeMap.put(name, node);
        }
    }

    /**
     * Assigns special trait based on the nodes type aka room, toilet, floorChanger
     * @param node
     */
    private void assignSpecialTrait(Node node,String specialTrait) {
        String type= node.getType();

        switch (type){
            case "room":
            //    node.setRoomName(specialTrait)
                break;
            case "toilet":
                //    node.setToiletType(specialTrait)
                break;
            case "floorchanger":
                // node.setFloorChangerType(specialTrait)
                break;

            default: break;
        }
    }

    /**
     * Set the neighbours list for each node in the node map.
     * @throws IOException
     */
    private void setNeighbours() throws IOException {
        List<String> lines = getNeighboursLines();
        for (String line : lines) {
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
     * resets the counter for the id's to always start from 0;
     * @param idLogFilePath
     * @throws IOException
     */
    private void clearIDLog(Path idLogFilePath) throws IOException {
        Files.write(idLogFilePath,"".getBytes());
    }

    /**
     * Goes through the log file and extracts the last recorded
     * id so we know from which one to start generating new ones.
     * @param idLogFilePath path to the log file
     * @return int last used id, -1 if the log file is empty
     * @throws IOException if the input is illegal
     */
    public int getLastUsedID(Path idLogFilePath) throws IOException {
        List<String> listLines = Files.readAllLines(idLogFilePath);
        if(listLines.isEmpty()){
            return -1;
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
     * Method to extract the coordinates of a node from a line
     * @param line to extract the data from
     * @return Node's coordinates enclosed in a Pair object
     */
    public Pair<Float, Float> extractData(String line) {
        String dataString = line.substring(line.indexOf('(')+1, line.indexOf(')'));

        List<String> dataList = new ArrayList<>(Arrays.asList(dataString.trim().split(" , ")));

        // Float xPos = Math.round(Float.parseFloat(dataList.get(0))*100.0f)/100.0f;	
        // Float yPos = Math.round(Float.parseFloat(dataList.get(1))*100.0f)/100.0f;	

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


    public String extractFloor(String line){ return line.substring(line.indexOf('_')+1, line.indexOf('_' )+2);}

}
