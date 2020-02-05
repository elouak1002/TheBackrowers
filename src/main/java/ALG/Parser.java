package ALG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Parser class is responsible for parsing lines from 
 * an input file and creating a list of Node with the
 * data from the file
 */
public class Parser {

    Path path; //Path to the input file
    HashMap<String,Node> nodeMap; // Main hashMap for storing each Node with its name


    /**
     * Constructor for the Parser class
     * @param path Path to the input file
     */
    public Parser(Path path){
        this.path = path;
    }

    /**
     * @return List of lines that contain data
     * @throws IOException
     */
    public List<String> getLines() throws IOException { return filter(getAllLines()); }

    /**
     * @return All lines from the input file
     * @throws IOException
     */
    public List<String> getAllLines() throws IOException{ return Files.readAllLines(path); }

    /**
     * Filters out lines that do not contain data
     * @param lines List of lines from a file
     * @return List of lines with data
     */
    public List<String> filter(List<String> lines){
        lines.removeIf(line ->
                line.contains("NODE LISTS")
                        || line.contains("//")
                        || line.contains("addAllNeighbours")
                        || line.startsWith(" ")
                        || line.equals(""));

        return lines;
    }

    /**
     * Method to populate the hashMap with Node objects, mapping each to its name
     * @param filteredLines list of lines which contain data
     * @return a hashMap of Node objects
     */
    public HashMap<String,Node> createNodes(List<String> filteredLines){
        nodeMap = new HashMap<>();
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

        List<String> dataList = new ArrayList<String>(Arrays.asList(dataString.trim().split(" , ")));

        Float xPos = Float.valueOf(dataList.get(0));
        Float yPos = Float.valueOf(dataList.get(1));

        Pair <Float, Float> coordinates = new Pair<>(xPos, yPos);
        return coordinates;
    }

    /**
     * Method to extract the name of a node from a line
     * @param line to extract data from
     * @return Node's name
     */
    public String extractName(String line){ return line.substring(0,line.indexOf("=")-1); }


}