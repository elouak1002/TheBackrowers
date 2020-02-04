package ALG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Parser class is responsible for parsing lines from 
 * an input file and creating a list of Node with the
 * data from the file
 */
public class Parser {

    Path path; //Path to the input file
    HashMap<String,List<Float>> nodeData = new HashMap<>(); // HashMap to store the data for creating the objects
    HashMap<String,Node> nodeMap; // Main hashMap for storing each Node with its name


    /**
     * Constructor for the Parser class
     * 
     * @param path Path to the input file
     */
    public Parser(Path path){
        this.path = path;
    }

    /**
     * @return List of lines that contain data
     * @throws IOException
     */
    public List<String> getLines() throws IOException {

            return filter(getAllLines());

    }

    /**
     * @return All lines from the input file
     * @throws IOException
     */
    public List<String> getAllLines() throws IOException{

            return Files.readAllLines(path);

    }

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
     * @param  filteredLines - only the relevant lines, which contain data
     * @return  - the final hashMap of Node objects
     */
    public HashMap<String,Node> createNodes(List<String> filteredLines){
        nodeMap = new HashMap<>();
        for(String line : filteredLines){
            extractData(line);
        }
        for(String key: nodeData.keySet()){
            Node newNode  = new Node(key,nodeData.get(key).get(0),nodeData.get(key).get(1));
            nodeMap.put(key,newNode);
        }
        return nodeMap;
    }

    /**
     * Method to extract the data between the brackets in each line from the file
     * We then add this data to the hashMap to build Nodes with later
     * @param line Single line of the type  - "Node HenRaph_04_491_365 = new Node( 49.176838f , 36.575871f , GuysHeights.HenRaph_04 );"
     */
    public void extractData(String line) {
        String name   = line.substring(0,line.indexOf("=")-1);
        String dataString =line.substring(line.indexOf('(')+1, line.indexOf(')'));

        List<String> dataList = new ArrayList<String>(Arrays.asList(dataString.trim().split(" , ")));

        Float xPos = Float.valueOf(dataList.get(0));
        Float yPos = Float.valueOf(dataList.get(1));

        List<Float> coordinates = new ArrayList<>();
        coordinates.add(xPos);
        coordinates.add(yPos);

        nodeData.put(name,coordinates);
    }


}