package parser;



import datastructures.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;




/**	
 * Parser class is responsible for parsing lines from 	
 * an input file and creating a list of Node with the	
 * data from the file	
 */	
public class Parser {

    private Path path; //Path to the input file
    protected List<String> paths;
    protected ArrayList<Integer> usedIds = new ArrayList<>();
    protected ArrayList<String> nodeOrder = new ArrayList<>();

    /**
     * Constructor for the Parser class used with data wrangling
     *
     * @param path Path to the input file
     */
    public Parser(Path path) {
        this.path = path;
    }

    /**
     * Constructor for the Parser class which takes several URIs as strings in a list
     * and is used for XML creation
     *
     * @param paths URIs to the input files as a List
     */
    public Parser(List<String> paths) {
        this.paths = paths;
        path = Paths.get(paths.get(0)); //
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
    public List<String> getAllLines() throws IOException {
        return Files.readAllLines(path);
    }

    /**
     * Filters out lines that do not contain data
     *
     * @param lines List of lines from a file
     * @return List of lines with data
     */
    protected List<String> filter(List<String> lines) {
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
     *
     * @param lines List of lines from a file.
     * @return List of lines with neighbours data.
     */
    protected List<String> filterNonNeighbours(List<String> lines) {
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
     * A method that creates a node creator object and passes the lines with node data
     * and their neighbours data
     * @return a TreeMap of Nodes and their names
     * @throws IOException
     */
    public TreeMap<String, Node> createNodes() throws IOException{
        NodeCreator nodeCreator = new NodeCreator(getLines(), getNeighboursLines());
        this.nodeOrder= nodeCreator.getNodesAsPerInsertion();
        return nodeCreator.createNodes();
    }

    /**
     * Returns the nodeOrderFrom the Parser
     * @return arrayList of the nodeOrder
     */
    public ArrayList<String> getNodeOrder(){
        return  nodeOrder;
    }
}