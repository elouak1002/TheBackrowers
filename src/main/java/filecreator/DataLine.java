package filecreator;

import java.util.List;

/**
 * A Data Line in the input file.
 * @version 04.02.2020
 */
public class DataLine {

	// private int position; // Position of the line in the file.
	private String xCoord;
	private String yCoord;
	private List<String> otherArguments;
	private String staticType;
	private String dynamicType;
	private String dataName;
	private String endLine;

	/**
	 * @param line a line in the input file.
	 * @param position // Should I store it ?
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

	public String getDataName() {
		return dataName;
	}

	public String getxCoord() {
		return xCoord;
	}
	
	public String getyCoord() {
		return yCoord;
	}

	public void setxCoord(Float xCoordIn) {
		xCoord = String.valueOf(xCoordIn) + "f";
	}
	
	public void setyCoord(Float yCoordIn) {
		yCoord = String.valueOf(yCoordIn) + "f";
	}

	public String otherArgumentsToString() {
		String result = otherArguments.stream().reduce("", (acc,element) -> acc + " , " + element.toString().trim());
		return result.substring(2, result.length());
	}

	public String toString() {
		return staticType + " " + dataName + " = new " + dynamicType + "( " + xCoord + " , " + yCoord + " ," + otherArgumentsToString() + " )" + endLine;
	}
}