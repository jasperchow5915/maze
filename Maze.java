package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Creates an adjacency list based maze, constructed of nodes.  
 * @author Daniel Kapit
 *
 * @param <T>
 */
public class Maze implements Iterable<Node> {
	public int size = 0;
	public Node value;
	
	protected Maze() {}
	
	/**
	 * The maze itself
	 * @param val its value
	 * @param neighbors all of its neighbors
	 */
	public Maze(Node val, LinkedList<Node> neighbors) {
		size = neighbors.size();
		this.value = val;
	}
	
	/**
	 * Prints the maze
	 */
	public void printPath() {
		LinkedList<Node> seen = new LinkedList<Node>();
		this.value.print(seen);
	}
	
	public Node findNode(Node n) {
		LinkedList<Node> seen = new LinkedList<Node>();
		Node f = this.value.find(n, seen);
		return f;
	}
	
	/**
	 * Gets the point value of the maze
	 * @param i
	 * @return
	 */
	public Point get(int i) {
		return this.value.neighbors.get(i).key;
	}
	
	/**
	 * Checks if the maze contains a certain point
	 * @param key the value to check for
	 * @return whether the point is contained
	 */
	public boolean contains(Point key) {
		for (Node assoc: this.value.neighbors) {
			if (assoc.key.equals(key))
				return true;
		}
		return false;
	}

	@Override
	public Iterator iterator() {
		return null;
	}
}

