package finalproject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class that allows navigation through an maze, constructed from an 
 * adjacency list
 * @author Daniel Kapit
 *
 */
public class Navigator {
	
	public static void main(String[] args) {
		Point p1 = new Point(5);
		Point p2 = new Point(9);
		Point p3 = new Point(7);
		Point p4 = new Point(13);
		Point p5 = new Point(19);
		Point p6 = new Point(2);
		Point p7 = new Point(5);
		Point p8 = new Point(11);
		Point p9 = new Point(17);
		
		Node n1 = new Node(p1, 0);
		Node n2 = new Node(p2, 1);
		Node n3 = new Node(p3, 2);
		Node n4 = new Node(p4, 3);
		Node n5 = new Node(p5, 4);
		Node n6 = new Node(p6, 5);
		Node n7 = new Node(p7, 6);
		Node n8 = new Node(p8, 7);
		Node n9 = new Node(p9, 8);
		
		LinkedList<Node> nei1 = new LinkedList<Node>();
		nei1.add(n2);
		nei1.add(n3);
		LinkedList<Node> nei2 = new LinkedList<Node>();
		nei2.add(n4);
		nei2.add(n1);
		nei2.add(n5);
		LinkedList<Node> nei3 = new LinkedList<Node>();
		nei3.add(n1);
		nei3.add(n5);
		nei3.add(n9);
		LinkedList<Node> nei4 = new LinkedList<Node>();
		nei4.add(n7);
		nei4.add(n2);
		nei4.add(n6);
		LinkedList<Node> nei5 = new LinkedList<Node>();
		nei5.add(n2);
		nei5.add(n3);
		nei5.add(n7);
		LinkedList<Node> nei6 = new LinkedList<Node>();
		nei6.add(n9);
		nei6.add(n4);
		LinkedList<Node> nei7 = new LinkedList<Node>();
		nei7.add(n8);
		nei7.add(n4);
		nei7.add(n5);
		LinkedList<Node> nei8 = new LinkedList<Node>();
		nei8.add(n7);
		LinkedList<Node> nei9 = new LinkedList<Node>();
		nei9.add(n6);
		nei9.add(n3);
		
		LinkedList<LinkedList<Node>> neighbors = new LinkedList<LinkedList<Node>>();
		neighbors.add(nei1);
		neighbors.add(nei2);
		neighbors.add(nei3);
		neighbors.add(nei4);
		neighbors.add(nei5);
		neighbors.add(nei6);
		neighbors.add(nei7);
		neighbors.add(nei8);
		neighbors.add(nei9);
		
		n1.setNeighbors(neighbors);
		
		Maze m = new Maze(n1, n1.neighbors);
		Navigator n = new Navigator(m);
		
		System.out.println(n.navigate(n8));
	}
	
	
	
	public Maze maze;
	boolean end = false;
	int d = 0;
	Node current;
	ArrayList<Integer> dir = new ArrayList<Integer>();
	ArrayList<Node> result = new ArrayList<Node>();
	int traveled;
	int nodes;
	
	/**
	 * Constructs the navigator itself
	 * @param m the maze to navigate
	 */
	public Navigator(Maze m) {
		this.maze = m;
		this.current = m.value;
		this.traveled = 0;
		this.nodes = 0;
	}
	
	/**
	 * Sets the base case for the navigation
	 * @param n the node to find
	 */
	public void start(Node n, Node find) {
		int ntemp = 0;
		while (n.neighbors != null && !n.equals(find)) {
			this.traveled += n.getDistanceTo(n.neighbors.get(0));
			ntemp++;
			n = n.neighbors.get(0);
			//n = this.maze.findNode(n);
		}
		this.nodes = ntemp;
	}
	
	/**
	 * Sets the base case for the nav_aux method.
	 * @param n the starting node
	 * @param find the node to find
	 * @return
	 */
	public ArrayList<Node> s(Node n, Node find) {
		ArrayList<Node> t = new ArrayList<Node>();
		Node c = n;
		while (!c.equals(find) && c != null) {
			t.add(c);
			//NULL POINTER
			c = c.neighbors.get(0);
		}
		return t;
	}
	
	/**
	 * Navigates through the list to the end node
	 * @param n the end node
	 * @return an arraylist of indexes, which are directions through the maze,
	 * the last two of which are the amount of stops and the distance of travel
	 */
	public ArrayList<Integer> navigate(Node n) {
		start(this.maze.value, n);
		ArrayList<Node> seen = new ArrayList<Node>();
		ArrayList<Node> end = new ArrayList<Node>();
		end.add(this.maze.value);
		ArrayList<Node> temp = nav_aux(this.maze.value, n, end, seen);
		ArrayList<Integer> directions = new ArrayList<Integer>();
		for (Node m : temp) {
			directions.add(m.index + 1);
		}
		directions.add(temp.size());
		directions.add(addTogether(temp));
		return directions;
	}
	
	/**
	 * 
	 * @param n the node from which the navigation starts
	 * @param f the node at which the navigation will end
	 * @return an arraylist of integer indexes
	 */
	public ArrayList<Node> nav_aux(Node n, Node f, ArrayList<Node> end, ArrayList<Node> seen) {
		
		if (n.neighbors == null) {
			return null;
		}

		for (Node z : n.neighbors) {
			if (!seen.contains(z)) {
				ArrayList<Node> curr = (ArrayList<Node>) end.clone();
				curr.add(z);
				seen.add(z);
				if (z.equals(f)) {
					return curr;
				}
				else if (!z.equals(f)) {
					end = nav_aux(z, f, curr, seen);
				}
				
				int d = addTogether(curr);
				if (d < this.traveled) {
					this.traveled = d;
					this.nodes = end.size();
					this.result = end;
				}
				
				else if (d ==  this.traveled) {
					if (curr.size() < this.nodes) {
						this.traveled = d;
						this.nodes = end.size();
						this.result = end;
					}
				}
				
			}
			else {
				continue;
			}
		}
		return this.result;
	}	
	
	/**
	 * Adds together the distances of the nodes in a list of nodes
	 * @param list a list of nodes
	 * @return the sum of the distances in the list
	 */
	public int addTogether(ArrayList<Node> list) {
		if (list == null)
			return 0;
		int sum = 0;
		for (Node i : list) {
			if (list.size() > list.indexOf(i) + 1)
				sum += i.getDistanceTo(list.get(list.indexOf(i) + 1));
		}
		return sum;
	}
}