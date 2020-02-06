package ALG;

import java.util.*;

/**
 * The Algorithm class represents objects that allow transformations on the list of coordonates
 * given in order to get the desired output
 */
public class Algorithm{


    //Fields:
    private ArrayList<Node> nodeList;

    /**
     * Constructor for the Node class
     * @param nodeList: a list of all the nodes we want to change
     */
    public Algorithm(ArrayList<Node> listOfNodes){
        nodeList = new ArrayList<Node>();
        copyNodes(listOfNodes);
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
    private void copyNodes(ArrayList<Node> nodes){
        for(Node index: nodes){
            nodeList.add(index);
        }
    }

    /**
     * @return the transformed node list
     */
    public ArrayList<Node> getList(){ return nodeList;}

//---------------------Transformation methods---------------------------------------

    /**
     *  This is the main method for the Algorithm class. 
     *  The method has been set to PUBLIC so it can be accessed from outside the class.
     * Once all the parameters are set the method runs the entire set of transformations 
     * to get to the desired coordonate configuration
     *  The main concept here is a class that runs through the list and passes individual
     * nodes to other methods to be transformed
     * 
     * @param theta: the angle we want to rotate the nodes by (in DEGREES)
     * @param scaleX: as X scale factor
     * @param scaleY: as Y scale factor
     * @param targetX: the final X position of the pivot node
     * @param targetY: the final Y position of the pivot node
     * @param pivotNode: a reference node (can either belong to the list or a completly new one)
     */
    public void runTransformations(float rotationAngle,float scaleX, float scaleY,
         float targetX, float targetY, Node pivotNode){

        //First part of the transformation representing rotation and scaling
        double theta = Math.toRadians(rotationAngle);
            System.out.println(scaleX);
        for(Node index: nodeList){
            setRotation(index, theta);
            setScale(index, scaleX, scaleY);
        }

        //distance we want to shift by to match pivot node with the target
        float deltaX = getDeltaX(pivotNode, targetX);
        float deltaY = getDeltaY(pivotNode, targetY);
        
        //Last part of the transformation representing shifting and reducing to 2 point decimal
        for(Node index: nodeList){
            setShift(index, deltaX, deltaY);
            roundAnswer(index);
        }
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
     * @param node
     * @param scaleX
     * @param scaleY
     */
    private void setScale(Node node, float scaleX, float scaleY){
        node.setX(node.getX() * scaleX);
        node.setY(node.getY() * scaleY);
    }

    /**
     *  Method for moving a node along the X-Y axis   
     * @param node: 
     * @param deltaX: X distance from the pivot point to the target
     * @param deltaY: Y distance form the pivot point to the target
     */
    private void setShift(Node node, float deltaX, float deltaY){
        node.setX(node.getX() + deltaX);
        node.setY(node.getY() + deltaY);
    }

    /**
     *  Method for measuring the X distance from the pivot point to the target
     * @param pivotNode
     * @param targetX
     * @return
     */
    private float getDeltaX(Node pivotNode, float targetX){
        float deltaX = targetX - pivotNode.getX();
        return deltaX;
    }

    /**
     *  Method for measuring the Y distance from the pivot point to the target
     * @param pivotNode
     * @param targetX
     * @return
     */
    private float getDeltaY(Node pivotNode, float targetY){
        float deltaY = targetY - pivotNode.getY();
        return deltaY;
    }

    /**
     *  Method for reducing the coordonates of a node to a 2 point decimal
     * @param node
     */
    private void roundAnswer(Node node){
        float x = node.getX();
        float y = node.getY();
        x = (float) (Math.round(x * 100.0) / 100.0);
        y = (float) (Math.round(y * 100.0) / 100.0);
        node.setX(x);
        node.setY(y);
    }
   

}
