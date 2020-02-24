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
    private ArrayList<Integer> usedIds = new ArrayList<>();

    /**
     * Constructor for the Parser class
     *
     * @param path Path to the input file
     */
    public Parser(Path path) {
        this.path = path;
    }

    public Parser(ArrayList<String> paths) {

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
    private List<String> filter(List<String> lines) {
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

    public TreeMap<String, Node> createNodes() throws IOException{
        NodeCreator nodeCreator = new NodeCreator(getLines(), getNeighboursLines());
        return nodeCreator.createNodes();
    }
}