package parser;

import datastructures.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;	


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


    /**	
     * Constructor for the Parser class	
     * @param path Path to the input file	
     */	
    public Parser(Path path){	
        this.path = path;	
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
     * @param filteredLines list of lines which contain data	
     * @return a hashMap of Node objects	
     */	
    public TreeMap<String,Node> createNodes(List<String> filteredLines) throws IOException {
        // Main hashMap for storing each Node with its name	
        TreeMap<String, Node> nodeMap = new TreeMap<>();
        for(String line : filteredLines){	
            String name = extractName(line);
            Pair<Float, Float> coordinates = extractData(line);
            int nodeId=generateNodeId(idLogFilePath);
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue());
            node.setId(nodeId);
            nodeMap.put(name, node);

        }
        return nodeMap;
    }

    /**
     * Set the neighbours list for each node in the node map.
     * @param NeighboursLines list of lines that contain the neighbours information.
     * @param nodeMap the map of node
     * @return the modified node map with neighbours added.
     * @throws IOException
     */
    public TreeMap<String, Node> setNeighbours(List<String> neighboursLines, TreeMap<String,Node> nodeMap) throws IOException {
        for (String line : neighboursLines) {
            String nodeName = extractNodeFromNeighboursLine(line);
            if (nodeMap.containsKey(nodeName)) {
                List<Node> neighbours = extractNeighbours(line).stream().map(name -> nodeMap.get(name)).filter(Objects::nonNull).collect(Collectors.toList());
                nodeMap.get(nodeName).setNeighbours(neighbours);
            }
        }
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

        Float xPos = Math.round(Float.parseFloat(dataList.get(0))*100.0f)/100.0f;	
        Float yPos = Math.round(Float.parseFloat(dataList.get(1))*100.0f)/100.0f;	

        return new Pair<>(xPos, yPos);	
    }	

    /**	
     * Method to extract the name of a node from a line	
     * @param line to extract data from	
     * @return Node's name	
     */	
    public String extractName(String line){ return line.substring(0,line.indexOf("=")).split(" ")[1]; }	


} 