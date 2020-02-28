package datastructures;

/**
 * An Enum class representing the Status type of a Node.
 */
public enum Status {
	INITIALISED, // The node is clearly initialised in the file.
	UNINITIALISED, // The node is not initialised in the file, and nodes are tried to be added to it.
	ONLY_NEIGHBOUR // The node is not initialised in the file, but it is only added as a neighbour of another node.
}