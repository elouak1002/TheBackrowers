package datastructures;

import java.util.List;
import java.util.ArrayList;

/**
 * The Node class represents location objects and allows modification of their
 * data
 */
public class Node {
    //Fields
    private String name;
    private int id;
    private float xCoord;
    private float yCoord;

    private int floor;
    private String type;
    private String specialType;
    private List<Node> neighbours;

    private Status status;

    /**
     * Constructor for the Node class, only with a name
     * @param name
     */
    public Node(String name, Status status) {
        this(name, 0,0);
        this.status = status;
    }

    /**
     * Constructor for the Node class
     * @param name of the node
     * @param xCoord of the node
     * @param yCoord of the node
     */
    public Node (String name, float xCoord, float yCoord){
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.neighbours = new ArrayList<>();
        this.status = Status.INITIALISED;
        specialType = "None";
    }

    /**
     * Getter method for the Node's name
     * @return Node's name
     */
    public String getName(){ return name; }

    /**
     * Getter method for the Node's X coordinate
     * @return X coordinate
     */
    public float getX(){ return xCoord; }

    /**
     * Getter method for the Node's Y coordinate
     * @return Y coordinate
     */
    public float getY(){ return yCoord; }

    /**
     * A method for setting new X coordinate
     * @param value X coordinate to be set
     */
    public void setX(float value) { xCoord = value; }

    /**
     * A method for setting new Y coordinate
     * @param value Y coordinate to be set
     */
    public void setY(float value) { yCoord = value; }

    /**
     * Getter method for the Node's unique id
     * @return id number
     */
    public int getId(){ return id; }

    /**
     * A method for setting new id value
     * @param value id  to be set
     */
    public void setId(int value) { this.id = value; }

    /**
     * A method for setting neighbours of a Node.
     * @param neighbours list of neighbours to be set.
     */
    public void setNeighbours(List<Node> neighbours) { this.neighbours = neighbours; }

    /**
     * A method for adding a neighbour to the neighbour list.
     * @param neighbour the neighbour to be added to the neighbour list.
     */
    public void addNeighbour(Node neighbour) { neighbours.add(neighbour); }

    /**
     * @param node
     * @return true if the node is a neighbour of the node.
     */
    public boolean hasNeighbour(Node node) { return neighbours.contains(node); }

    /**
     * @return Neighbour's list.
     */
    public List<Node> getNeighbours() { return neighbours; }

    /**
     * @return Node's floor
     */
    public int getFloor(){ return floor; }

    /**
     * @param floor value to set
     */
    public void setFloor(int floor) { this.floor = floor; }

    /**
     * @param type value to set
     */
    public void setSpecialType(String type) { specialType = type; }

    /**
     * @return Node's special type
     */
    public String getSpecialType(){ return specialType; }

    /**
     * @return true if a special type has been set
     */
    public boolean hasSpecialType() { return !specialType.equals("None"); }

    /**
     * @return Node's data as a string
     */
    public String toString(){ return "Node: " + name + ", X: " + xCoord + ", Y:" + yCoord + ", Floor: " + floor + ", Type: " + type; }

    /**
     * Set a node's type.
     * @param type
     */
    public void setType(String type) {
        this.type= type;
    }

    /**
     *
     * @return a node's type
     */
    public String getType(){
        return type;
    }

    /**
     * @return the status number of the node.
     */
    public Status getStatus() {
        return status;
    }
}