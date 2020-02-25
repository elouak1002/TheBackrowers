package dataprocessors;

import GUI.OutputController;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class Logger {
    private ArrayList<String> logger = new ArrayList<>();
    public Logger() {

    }

    public void logAdd(String nodeNameA, String nodeNameB) {
        String neighbourAdded = nodeNameB + "added to become a neighbour for " + nodeNameA + ".";
        logger.add(neighbourAdded);
    }

    public void logRemove(String nodeName) {
        String removedNode = nodeName + "with no neighbour removed.";
        logger.add(removedNode);
    }

    public void saveLoggerToFile() {
        String fileName = "LogFile.txt";
        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            int size = logger.size();
            for (int i = 0; i < size; i++) {
                output.write(logger.get(i).toString() + "\n");
            }
            output.close();
        }
        catch (IOException e) {
            java.util.logging.Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
