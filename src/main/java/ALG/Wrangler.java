package ALG;


import java.util.TreeMap;

/**
 * The Wrangler class represents objects that allow transformations on the list of coordonates
 * given in order to get the desired output
 */
public class Wrangler {


    //Fields:
    private TreeMap<String, Node> outputNodes;

    /**
     * Constructor for the Node class
     * @param inputNodes: a list of all the nodes we want to change
     */
    public Wrangler(TreeMap<String, Node> inputNodes){
        outputNodes = new TreeMap<>();
        copyNodes(inputNodes);
    }

//------------------Helper methods for the class------------------------------------

    /**
     *  This method creates a copy of the initial list of nodes to apply the 
     * changes we want. This is just a safety measure: in case something
     * goes wrong with the transformations we don't actually modifiy the 
     * original node list
     * 
     * @param nodes: initial list of nodes
     */
    private void copyNodes(TreeMap<String, Node> nodes){
        for(Node index: nodes.values()){
            Node copy = new Node(index.getName(), index.getX(), index.getY());
            outputNodes.put(index.getName(), copy);
        }
    }


//---------------------Transformation methods---------------------------------------

    /**
     *  This is the main method for the Wrangler class.
     * Once all the parameters are set the method runs the entire set of transformations 
     * to get to the desired coordonate configuration
     *  The main concept here is a class that runs through the list and passes individual
     * nodes to other methods to be transformed
     * 
     * @param rotationAngle: the angle we want to rotate the nodes by (in DEGREES)
     * @param scaleX: as X scale factor
     * @param scaleY: as Y scale factor
     * @param targetX: the final X position of the pivot node
     * @param targetY: the final Y position of the pivot node
     * @param pivotNode: a reference node (can either belong to the list or a completly new one)
     * @return A TreeMap with transformed nodes
     */
    public TreeMap<String, Node> runTransformations(float rotationAngle,float scaleX, float scaleY,
                                                    float targetX, float targetY, Node pivotNode){

        //First part of the transformation representing rotation and scaling
        double theta = Math.toRadians(rotationAngle);
        for(Node index: outputNodes.values()){
            setRotation(index, theta);
            setScale(index, scaleX, scaleY);
        }

        //distance we want to shift by to match pivot node with the target
        float deltaX = getDeltaX(pivotNode, targetX);
        float deltaY = getDeltaY(pivotNode, targetY);
        
        //Last part of the transformation representing shifting and reducing to 2 point decimal
        for(Node index: outputNodes.values()){
            setShift(index, deltaX, deltaY);
        }
        return outputNodes;
    }
   
    /**
     *  Method for rotating one point
     * @param node: the node we want to rotate
     * @param theta: the angle we want to rotate the nodes by (in RADIANS)
     */
    private void setRotation(Node node, double theta){
        float x = node.getX();
        float y = node.getY();

        float cos = (float)Math.cos(theta);
        float sin = (float)Math.sin(theta); 
        
        float xTemp = x*cos - y*sin;
        float yTemp = x*sin + y*cos;
        node.setX(xTemp);
        node.setY(yTemp);
    }

    /**
     *  Method for scaling the nodes is the list by a given X and Y factor
     * @param node: the node we want to scale
     * @param scaleX: the ammount we want to scale X by
     * @param scaleY: the ammount we want to scale Y by
     */
    private void setScale(Node node, float scaleX, float scaleY){
        node.setX(node.getX() * scaleX);
        node.setY(node.getY() * scaleY);
    }

    /**
     *  Method for moving a node along the X-Y axis   
     * @param node: the node we want to shift
     * @param deltaX: X distance from the pivot point to the target
     * @param deltaY: Y distance form the pivot point to the target
     */
    private void setShift(Node node, float deltaX, float deltaY){
        node.setX(node.getX() + deltaX);
        node.setY(node.getY() + deltaY);
    }

    /**
     *  Method for measuring the X distance from the pivot point to the target
     * @param pivotNode: the reference node for calculating shifting distance
     * @param targetX: the location we want the pivotNode to be moved at
     * @return the distance from pivotNode to targetX
     */
    private float getDeltaX(Node pivotNode, float targetX){
        return targetX - pivotNode.getX();
    }
    
    /**
     *  Method for measuring the Y distance from the pivot point to the target
     * @param pivotNode: the reference node for calculating shifting distance
     * @param targetY: the location we want the pivotNode to be moved at
     * @return the distance from pivotNode to targetY
     */
    private float getDeltaY(Node pivotNode, float targetY){
        return targetY - pivotNode.getY();
    }
   
}
