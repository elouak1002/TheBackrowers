package ALG;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Parser class is responsible for parsing lines from 
 * an input file and creating a list of Node with the
 * data from the file
 */
public class Parser {

    Path path; //Path to the input file

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
    public List<String> getLines() throws IOException{
        try {
            return filter(getAllLines());
        }
        catch (Exception e) {
            throw IOException;
        }
    }

    /**
     * @return All lines from the input file
     * @throws IOException
     */
    public List<String> getAllLines() throws IOException{
        try {
            return Files.readAllLines(path);
        }
        catch (Exception e) {
            throw IOException;
        }
    }

    /**
     * Filters out lines that do not contain data
     * @param lines List of lines from a file
     * @return List of lines with data
     */
    private List<String> filter(List<String> lines){
        for (String line : lines) {
            if(!line.contains("=") || line.contains("NODE LISTS")){
                lines.remove(line);
            }
        }
    }

    /**
     * 
     * @return
     */
    public List<Node> createNodes(){}
}