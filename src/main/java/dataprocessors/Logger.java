package dataprocessors;

import java.util.ArrayList;

public class Logger {
    private ArrayList<String> logger = new ArrayList<>();
    public Logger() {

    }

    public void logAdd(String nodeNameA, String nodeNameB) {
        String neighbourAdded = nodeNameB + "added to become a neighbour for " + nodeNameA;
        logger.add(neighbourAdded);
    }

    public void logRemove(String nodeName) {
        String removedNode = nodeName + "removed";
        logger.add(removedNode);
    }

    public void outputLogger() {

    }
}
