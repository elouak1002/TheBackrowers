package parser;	

import java.io.IOException;	
import java.nio.file.Files;	
import java.nio.file.Path;	
import java.util.*;	

import javafx.util.Pair;	


/**	
 * Parser class is responsible for parsing lines from 	
 * an input file and creating a list of Node with the	
 * data from the file	
 */	
public class Parser {	

    private Path path; //Path to the input file	


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
    public List<String> getLines() throws IOException { return filter(getAllLines()); }	

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
	 * @return The position of the first data line in the input file,	
     * -1 otherwise.	
	 */	
	public int beginOfDataLines(List<String> fullLines, List<String> filteredLines) { 	
        if (filteredLines.size() > 0) {	
            return fullLines.indexOf((filteredLines.get(0)));	
        }	
        return -1;	
	}	

    /**	
	 * @return The position of the last data line in the input file,	
     * -1 otherwise.	
	 */	
	public int endOfDataLines(List<String> fullLines, List<String> filteredLines) { 	
        return fullLines.indexOf(filteredLines.get(filteredLines.size() - 1));   	
	}	

    /**	
     * Method to populate the hashMap with Node objects, mapping each to its name	
     * @param filteredLines list of lines which contain data	
     * @return a hashMap of Node objects	
     */	
    public TreeMap<String,Node> createNodes(List<String> filteredLines){	
        // Main hashMap for storing each Node with its name	
        TreeMap<String, Node> nodeMap = new TreeMap<>();	
        for(String line : filteredLines){	
            String name = extractName(line);	
            Pair<Float, Float> coordinates = extractData(line);	
            Node node = new Node(name, coordinates.getKey(), coordinates.getValue());	
            nodeMap.put(name, node);	
        }	

        return nodeMap;	
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