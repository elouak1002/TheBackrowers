package datastructures;

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
     * @return Node's data as a string
     */
    public String toString(){ return "Node: " + name + ", X: " + xCoord + ", Y:" + yCoord; }
}