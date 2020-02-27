package parser;

import datastructures.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * An XML parser class that extends the Parser and is responsible
 * for combining input files and extracting data from them for later
 * XML file creation
 */
public class XMLParser extends Parser{

    /**
     * A constructor for the XMLParser class that inherits from one of
     * the Parser constructors and takes multiple file URIs as a List of string
     * @param paths Input file URIs as a list of strings
     */
    public XMLParser(List<String> paths) {
        super(paths);
    }

    /**
     * A method that combines Nodes' data and neighbour lines into one list,
     * separated by a '#" line
     * @return combined data and neighbour lines
     * @throws IOException
     */
    @Override
    public List<String> getAllLines() throws IOException {
        List<String> combinedLines = new ArrayList<>();
        combinedLines.addAll(getLines());
        combinedLines.add("#");
        combinedLines.addAll(getNeighboursLines());
        return combinedLines;
    }

    /**
     * A method that extracts Nodes' data lines out of multiple
     * input files
     *
     * @return combined data lines
     * @throws IOException
     */
    @Override
    public List<String> getLines() throws IOException{
        List<String> nodeLines = new ArrayList<>();
        for (String filePath: paths) {
            List<String> lines;
            lines = Files.readAllLines(Paths.get(filePath));
            nodeLines.addAll(filter(lines));
        }
        return nodeLines;
    }

    /**
     * A method that extracts Nodes' neighbour lines out of multiple
     * input files
     *
     * @return combined neighbour lines
     * @throws IOException
     */
    @Override
    public List<String> getNeighboursLines() throws IOException {
        List<String> neighbourLines = new ArrayList<>();
        for (String filePath: paths) {
            List<String> lines;
            lines = Files.readAllLines(Paths.get(filePath));
            neighbourLines.addAll(filterNonNeighbours(lines));
        }
        return neighbourLines;
    }

}
