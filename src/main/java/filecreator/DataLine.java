package filecreator; // File Creation package.

import java.util.List;

/**
 * A Data Line in the input file.
 * A Data Line is responsible for the creation of a Node object in the input file.
 * @version 07.02.2020
 */
public class DataLine {

	// The X Coordinate of the created Node.
	private String xCoord;
	
	// The Y Coordinate of the created Node.
	private String yCoord;
	
	// A List of the remaining arguments of the created nodes.
	private List<String> otherArguments;

	// The static type of the created node.
	private String staticType;
	
	// The dynamic type of the created node.
	private String dynamicType;
	
	// The variable name of the created node.
	private String dataName;
	
	// The end of the data line, i.e. comments made to the line.
	private String endLine;

	/**
	 * Create a new data line.
	 * @param DataName
	 * @param xCoord
	 * @param yCoord
	 * @param otherArguments
	 * @param staticType
	 * @param dynamicType
	 * @param endLine
	 */
	public DataLine(String dataName, String xCoord, String yCoord, List<String> otherArguments, String staticType, String dynamicType, String endLine) {
		this.dataName =  dataName;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.otherArguments = otherArguments;
		this.staticType = staticType;
		this.dynamicType = dynamicType;
		this.endLine = endLine;
	}

	/**
	 * @return The variable name of the assigned node.
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @return The X coordinate of the node in the data line.
	 */
	public String getxCoord() {
		return xCoord;
	}
	
	/**
	 * @return The Y coordinate of the node in the data line.
	 */
	public String getyCoord() {
		return yCoord;
	}

	/**
	 * Set a new value for the X coordinate in the data line.
	 * @param xCoordIn
	 */
	public void setxCoord(Float xCoordIn) {
		xCoord = String.valueOf(xCoordIn) + "f";
	}
	
	/**
	 * Set a new value for the Y coordinate in the data line.
	 * @param yCoorIn
	 */
	public void setyCoord(Float yCoordIn) {
		yCoord = String.valueOf(yCoordIn) + "f";
	}

	/**
	 * @return the other arguments of the node as a string,
	 * with space on the right and the left of each arguments,
	 * separated by commas.
	 */
	public String otherArgumentsToString() {
		String result = otherArguments.stream().reduce("", (acc,element) -> acc + " , " + element.toString().trim());
		return result.substring(2, result.length());
	}

	/**
	 * @return The Data Line as a string with the expected format.
	 */
	public String toString() {
		return staticType + " " + dataName + " = new " + dynamicType + "( " + xCoord + " , " + yCoord + " ," + otherArgumentsToString() + " )" + endLine;
	}
}