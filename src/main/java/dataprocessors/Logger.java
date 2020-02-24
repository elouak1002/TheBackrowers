package dataprocessors;

import java.util.ArrayList;

public class Logger {
    private ArrayList<String> logger = new ArrayList<>();
    public Logger() {

    }

    public void logAdd(String nodeNameA, String nodeNameB) {
        String neighbour = nodeNameB + "added to become a neighbour for " + nodeNameA;
        logger.add(neighbour);
    }

    public void logRemove(String nodeName) {

    }
}
