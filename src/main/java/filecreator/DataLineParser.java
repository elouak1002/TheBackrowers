package filecreator; // File Creation package.

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

/**
 * Parse the information from a line of data of the input file.
 * and create a DataLine representation of it.
 * Call the createDataLine method in order to make use of it.
 */
public class DataLineParser {

	// The name part of the data line. (Static Type + Variable Name)
	private List<String> namePart;
	
	// The argument part of the data line. (Xcoord + Ycoord + The remaining arguments)
	private List<String> argumentPart;
	
	// The end part of the data line. (The end of the line as a string)
	private String endPart;
	
	// The full data line.
	private String line;


	/**
	 * Create a DataLineParser and set all fields to their default values.
	 */
	public DataLineParser() {
		namePart = null;
		argumentPart = null;
		endPart = "";
		line = "";
	}
	
	/**
	 * Parse the line and set the fields to their corresponding values.
	 * @param line The line to be parsed.
	 */
	private void parseNewLine(String line) {
		this.line = line;
		this.namePart = getNamePart();
		this.argumentPart = getArgumentPart();
		this.endPart = getEndPart();
	}

	/**
	 * @return the name part of the data line, everything before the "=" sign.
	 */
	private List<String> getNamePart() {
		return Arrays.asList(line.substring(0, line.indexOf("=")).split(" "));
	}

	/**
	 * @return the argument part of the data line, split by commas.
	 */
	private List<String> getArgumentPart() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return Arrays.asList(assignmentPart.substring(assignmentPart.indexOf("(")+1, assignmentPart.indexOf(")")).trim().split(","));
	}

	/**
	 * @return end part of the data line, everything after the closing parenthesis of the node creation.
	 */
	private String getEndPart() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return assignmentPart.substring(assignmentPart.indexOf(")")+1).trim();
	}

	/**
	 * @return the dynamic type of the node created in the data line.
	 */
	private String getDynamicType() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return assignmentPart.substring(0,assignmentPart.indexOf("(")).replace("new", "").trim();
	}

	/**
	 * @return the static type of the node created in the data line.
	 */
	private String getStaticType() {
		return namePart.get(0);
	}

	/**
	 * @return the variable name of the node created in the data line.
	 */
	private String getDataName() {
		return namePart.get(1);
	}

	/**
	 * @return the X Coordinate of the node created in the data line.
	 */
	private String getxCoord() {
		return argumentPart.get(0);
	}

	/**
	 * @return the Y Coordinate of the node created in the data line.
	 */
	private String getyCoord() {
		return argumentPart.get(1);
	}

	/**
	 * @return A list of the remaining arguments of the node created in the data line.
	 */
	private List<String> getOtherArguments() {
		LinkedList<String> tempArgList = new LinkedList<>(argumentPart);
		tempArgList.removeFirst();
		tempArgList.removeFirst();
		return tempArgList;
	}

	/**
	 * @return a DataLine using the String representation of it in the input file.
	 */
	public DataLine createDataLine(String line) {	
		// Parse the line and set fields values.
		parseNewLine(line); 

		// Return a new DataLine object, calling the method of the parser to retrieve the needed information.
		return new DataLine(getDataName(),getxCoord(),getyCoord(),getOtherArguments(),getStaticType(),getDynamicType(),endPart); 
	}

}