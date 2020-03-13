package linecreators;

import java.nio.file.Path;
import java.io.IOException;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;

import datastructures.Node;

/**
 * Process the creation of the node list lines.
 * 
 * @version 11.03.2020
 */
public class NodeListLineCreator extends LineCreator {

	// Map type's node to a list of nodes (of that type).
	private Map<String,List<Node>> typeMap;

	/**
	 * Constructor for the Node List Creator class.
	 * @param nodeMap The node Map
	 * @param originPath The origin path, used by the parser.
	 * @throws IOException
	 */
	public NodeListLineCreator(Map<String, Node> nodeMap, Path originPath) throws IOException {
		// Call to parent constructor
		super(nodeMap, originPath);
		
		// A list of the neighbour lines to be modified.
		this.lines = new LinkedList<>();

		// Create the type map.
		createTypeMap();

		if ()
		// Create the nodes list lines.
		createLines();
	}

	/**
	 * @param nodeList A list of nodes.
	 * @return a String representation of the nodes name.0
	 */
	private String formatEachList(List<Node> nodeList) {	
		return nodeList.stream().map(node -> node.getName()).reduce("", (acc,element) -> acc + " , " + element.trim());
	}

	/**
	 * Create the type map.
	 * Each type map a list of nodes of that type.
	 */
	public void createTypeMap() {
		typeMap = new LinkedHashMap<>();
		for (String nodeName : nodeMap.keySet()) {
			String type = nodeMap.get(nodeName).getType();
			if (typeMap.containsKey(type)) {
				typeMap.get(type).add(nodeMap.get(nodeName));
			}
			else {
				List<Node> nodeList = new LinkedList<>();
				nodeList.add(nodeMap.get(nodeName));
				typeMap.put(type,nodeList);
			}
		}
	}

	/**
	 * Modify the node list lines, adding the
	 * initialised nodes to the corresponding list (by type).
	 */
	@Override
	protected void createLines() {
		lines.add("====== NODE LISTS =======");
		for (String type : typeMap.keySet()) {
			lines.add("");
			lines.add("// " + type + "s:");
			lines.add(formatEachList(typeMap.get(type)));
		}
	}

}