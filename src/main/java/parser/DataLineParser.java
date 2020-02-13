package parser; // File Creation package.

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
	public void parseNewLine(String line) {
		this.line = line;
		this.namePart = getNamePart();
		this.argumentPart = getArgumentPart();
		this.endPart = getEndPart();
	}

	/**
	 * @return the name part of the data line, everything before the "=" sign.
	 */
	private List<String> getNamePart() {
		return Arrays.asList(line.substring(0, line.indexOf("=")).split(" ")).stream().map(String::trim).collect(Collectors.toList()); // trim each element.
	}

	/**
	 * @return the argument part of the data line, split by commas.
	 */
	private List<String> getArgumentPart() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return Arrays.asList(assignmentPart.substring(assignmentPart.indexOf("(")+1, assignmentPart.indexOf(")")).trim().split(",")).stream().map(String::trim).collect(Collectors.toList()); // trim each element.
	}

	/**
	 * @return end part of the data line, everything after the closing parenthesis of the node creation.
	 */
	public String getEndPart() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return assignmentPart.substring(assignmentPart.indexOf(")")+1).trim();
	}

	/**
	 * @return the dynamic type of the node created in the data line.
	 */
	public String getDynamicType() {
		String assignmentPart = line.substring(line.indexOf("=")+1);
		return assignmentPart.substring(0,assignmentPart.indexOf("(")).replace("new", "").trim();
	}

	/**
	 * @return the static type of the node created in the data line.
	 */
	public String getStaticType() {
		if (namePart != null && namePart.size() > 0)
			return namePart.get(0);
		return "";
	}

	/**
	 * @return the variable name of the node created in the data line.
	 */
	public String getDataName() {
		if (namePart != null && namePart.size() > 1)
			return namePart.get(1);
		return "";
	}

	/**
	 * @return the X Coordinate of the node created in the data line.
	 */
	public String getxCoord() {
		if (argumentPart != null && argumentPart.size() > 0)
			return argumentPart.get(0);
		return "";
	}

	/**
	 * @return the Y Coordinate of the node created in the data line.
	 */
	public String getyCoord() {
		if (argumentPart != null && argumentPart.size() > 1) 
			return argumentPart.get(1);
		return "";
	}

	/**
	 * @return A list of the remaining arguments of the node created in the data line.
	 */
	public List<String> getOtherArguments() {
		LinkedList<String> tempArgList = new LinkedList<>(argumentPart);
		for (int i=0; i<2; i++)
			if (tempArgList.size() > 0)
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