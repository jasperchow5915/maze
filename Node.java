package finalproject;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class that constructs each node in a maze
 * @author Daniel Kapit
 *
 */
public class Node {
	int distance;
	protected Point key;
	LinkedList<Node> neighbors;
	int index;
	
	/**
	 * Creates an association for an object, which is a list of all of its 
	 * neighbors
	 * @param key the object of the association
	 * @param neighbors the list of neighbors
	 */
	public Node(Point key, int index) {
		this.index = index;
		this.key = key;
		this.distance = key.distance;
		
	}
	
	public void setNeighbors(LinkedList<LinkedList<Node>> neighbors) {
		LinkedList<Node> seen = new LinkedList<Node>();
		this.generateN(neighbors, seen);
	}
	
	public void generateN(LinkedList<LinkedList<Node>> neighbor, LinkedList<Node> seen) {
		this.neighbors = new LinkedList<Node>();
		Node temp = null;
		Iterator<Node> iter = neighbor.get(this.index).iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			this.neighbors.add(temp);
		}
		for (Node p : this.neighbors) {
			if (!seen.contains(p)) {
				seen.add(p);
				p.generateN(neighbor, seen);
			}	
		}
	}
	
	/**
	 * Checks whether the key of one list equals the key of another
	 * @param other the other list
	 * @return true if the keys are equal, or false if they are not
	 */
	public boolean equals(Node other) {
		return this.key.equals(other.key);
	}
	
	public int getDistanceTo(Node other) {
		return this.distance + other.distance;
	}
	
	/**
	 * Recursively looks for a Node in an adjacency list
	 * @param n the node to find
	 * @param seen the list of nodes already seen
	 * @return the node
	 */
	public Node find(Node n, LinkedList<Node> seen) {
		Node found = null;
		for (Node neighbor : this.neighbors){
			if (!seen.contains(neighbor)) {
				if (neighbor.equals(n))
					return neighbor;
				found = neighbor.find(n, seen);
			}
		}
		
		return found;
	}
	
	/**
	 * Recursively prints a list, by remembering nodes that have
	 * already been seen
	 * @param seen a list of objects seen
	 */
	public void print(LinkedList<Node> seen) {
		System.out.print(this.key);
		for (Node neighbor : this.neighbors){
			if (!seen.contains(neighbor))
				seen.add(this);
				neighbor.print(seen);
		}
	}
}

class Point {
	int distance;
	
	public Point(int d) {
		this.distance = d;
	}
}



