/**
 * This class represents the entire quadrant tree representing the image, each node in the tree
 * is an object of class QTreeNode
 * @author maharshiipatel
 */

public class QuadrantTree {
	
	private QTreeNode root; // The root node of the tree
	
	/**
	 * The constructor which takes the pixels of the image as a 2D matrix and then makes the
	 * quadrant tree recursively, more detail in code
	 * @param thePixels The 2D matrix representing the image
	 */
	public QuadrantTree(int[][] thePixels) {
		
		if(thePixels.length == 1) {
			// Case for if there is only one pixel
			this.root = new QTreeNode(null, 0, 0, 1, Gui.averageColor(thePixels, 0, 0, 1));
			
		} else {
			
			// Make a node for the root and make sure to set it to null
			
			this.root = new QTreeNode(new QTreeNode[4], 0, 0, thePixels.length, Gui.averageColor(thePixels, 0, 0, thePixels.length));
			this.root.setParent(null);
			
			// Call the helper method which has more parameters which helps in the recursive calls
			
			QTreeNode R1 = buildQuadrantTree(0, 0, thePixels.length/2, thePixels);
			QTreeNode R2 = buildQuadrantTree(thePixels.length/2, 0, thePixels.length/2, thePixels);
			QTreeNode R3 = buildQuadrantTree(0, thePixels.length/2, thePixels.length/2, thePixels);
			QTreeNode R4 = buildQuadrantTree(thePixels.length/2, thePixels.length/2, thePixels.length/2, thePixels);
			
			// Set the parent of the children and set the children of the parent
			
			this.root.setChild(R1, 0);
			this.root.setChild(R2, 1);
			this.root.setChild(R3, 2);
			this.root.setChild(R4, 3);
			
			R1.setParent(this.root);
			R2.setParent(this.root);
			R3.setParent(this.root);
			R4.setParent(this.root);
			
		}
	}
	
	/**
	 * Helper method for the constructor, recursively done, more detail in code
	 * @param xcoord x coordinate of the upper left corner of the quadrant
	 * @param ycoord y coordinate of the upper left corner of the quadrant
	 * @param size Size of the quadrant
	 * @param matrix Matrix representing the pixels of the image
	 * @return Returns the children nodes
	 */
	private QTreeNode buildQuadrantTree(int xcoord, int ycoord, int size, int[][] matrix) {
		
		if(size > 1) {
			
			// Recursive case which is done if the size is greater than 1
			// Create a new node and then make the children through a recursive call
			
			QTreeNode node = new QTreeNode(new QTreeNode[4], xcoord, ycoord, size, Gui.averageColor(matrix, xcoord, ycoord, size));
			
			int newSize = size / 2;
			
			QTreeNode R1 = buildQuadrantTree(xcoord, ycoord, newSize, matrix);
			QTreeNode R2 = buildQuadrantTree(xcoord + newSize, ycoord, newSize, matrix);
			QTreeNode R3 = buildQuadrantTree(xcoord, ycoord + newSize, newSize, matrix);
			QTreeNode R4 = buildQuadrantTree(xcoord + newSize, ycoord + newSize, newSize, matrix);
			
			// Set the parents and children
			
			node.setChild(R1, 0);
			node.setChild(R2, 1);
			node.setChild(R3, 2);
			node.setChild(R4, 3);
			
			R1.setParent(node);
			R2.setParent(node);
			R3.setParent(node);
			R4.setParent(node);
			
			return node;
			
		} else {
			// Base case for if the size is equal to 1, so only one pixel
			return new QTreeNode(null, xcoord, ycoord, 1, Gui.averageColor(matrix, xcoord, ycoord, 1));
			
		}
	}
	
	/**
	 * Getter method to get the root node of the tree
	 * @return Root node of the tree
	 */
	public QTreeNode getRoot() {
		return this.root;
	}
	
	/**
	 * This method takes a specific level of the quadrant tree and makes
	 * a linked list from left to right of that level; if theLevel is greater
	 * than the number of levels in tree, then it just gets the last level instead
	 * @param r The root node 
	 * @param theLevel The level which will be turned into a linked list
	 * @return Returns the head of the linked list 
	 */
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
		
		// Base case, in case only the root node is wanted
		
		if(r.isLeaf() || theLevel == 0) {
			return new ListNode<QTreeNode>(r);
		} 
		
		// Recursive case to make the linked list using a helper method which keeps track of the head of the linked list
		
		QTreeNode temp = new QTreeNode();
		ListNode<QTreeNode> head = helpPixels(new ListNode<QTreeNode>(temp), r, theLevel);;
		return head.getNext();
			
	}
	
	/**
	 * Helper method for help pixels
	 * @param head Head of the linked list
	 * @param r The root/parent node 
	 * @param theLevel The level which will be turned into a linked list
	 * @return The head of the linked list
 	 */
	private ListNode<QTreeNode> helpPixels(ListNode<QTreeNode> head, QTreeNode r, int theLevel) {
		
		// Base case
		
		if(r.isLeaf() || theLevel == 0) {
			getEnd(head).setNext(new ListNode<QTreeNode>(r));
			return null;
		} 
		
		// Recursive case, theLevel is subtracted by 1 to go down in the tree
		
		for(int i = 0; i <= 3; i++) {
			QTreeNode child = r.getChild(i);
			helpPixels(head, child, theLevel-1);
		}
		
		return head;
		
	}
 	
	/**
	 * Helper method to get the end of a linked list
	 * @param node Head node of the linked list
	 * @return The end node of the linked list
	 */
	private ListNode<QTreeNode> getEnd(ListNode<QTreeNode> node) {
		while(node.getNext() != null) {
			node = node.getNext();
		}
		
		return node;
	}
	
	/**
	 * This method is essentially the same as getPixels in its logic, the only
	 * different being that the nodes being put into the linked list need to be
	 * a similar color to the given color in the parameter
	 * @param r The root/parent node
	 * @param theColor The color which the nodes should be similar to
	 * @param theLevel The level at which get the nodes to put into the linked list
	 * @return A duple object which has both the head of the linked list and the length of the linked list 
	 */
	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
		
		// Base case
		
		if((r.isLeaf() || theLevel == 0) && (Gui.similarColor(r.getColor(), theColor))) {
			return new Duple(new ListNode<QTreeNode>(r), 1);
		} 
		
		// Recursive case
		
		QTreeNode temp = new QTreeNode();
		ListNode<QTreeNode> head = helpMatching(new ListNode<QTreeNode>(temp), r, theColor, theLevel);
		head = head.getNext();
		return new Duple(head, getNumNodes(head));

	}
	
	/**
	 * Helper method for findMatching method, just has the head of the linked list as a parameter
	 * as it helps keep track
	 * @param head Head of the linked list
	 * @param r The root/parent node
	 * @param theColor The color to which should be a similar node
	 * @param theLevel The level at which get the nodes to put into the linked list
	 * @return The head of the linked list
	 */
	private ListNode<QTreeNode> helpMatching(ListNode<QTreeNode> head, QTreeNode r, int theColor, int theLevel) {
		
		if((r.isLeaf() || theLevel == 0) && (Gui.similarColor(r.getColor(), theColor))) {
			getEnd(head).setNext(new ListNode<QTreeNode>(r));
			return null;
		} else if(r.isLeaf() || theLevel == 0) {
			return null;
		}
		
		for(int i = 0; i <= 3; i++) {
			QTreeNode child = r.getChild(i);
			helpMatching(head, child, theColor, theLevel-1);
		}
		
		return head;
		
	}
	
	/**
	 * Helper method to get the number of nodes in a linked list
	 * @param node Head node of the linked list
	 * @return int number which represents the number of nodes in the linked list
	 */
	private int getNumNodes(ListNode<QTreeNode> node) {
		
		int num = 0;
		
		while(node != null) {
			num++;
			node = node.getNext();
		}
		
		return num;
		
	}
	
	/**
	 * This essentially uses DFS and finds the node which has the given
	 * level and coordinate
	 * @param r The root/parent node
 	 * @param theLevel The level at which the node should be
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return The tree node if found, otherwise null
	 */
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
		
		if(theLevel == 0 && r.contains(x, y)) { 
			// Base case 1: If its the correct and has the right coordinates
			return r;
		} else if(theLevel == 0) {
			// Base case 2: If its the correct level but doesn't have the right coordinate
			return null;
		} else {
			
			// Recursive case: Calls findNode on the children of the root node
			for(int i = 0; i <= 3; i++) {
				
				QTreeNode node = findNode(r.getChild(i), theLevel-1, x, y);
				if(node != null) {
					return node;
				}

			}
		}
		
		// Return null if no node is found which meets the conditions
		return null;
		
	}
	
}