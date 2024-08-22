/**
 * This class represents a singular node in a quadrant tree which will be used in representing an image
 * @author maharshiipatel
 */

public class QTreeNode {
	
	private int x, y; // Coordinates of the upper left corner of the image piece represented
	private int size; // Size of the quadrant
	private int color; // Average color of the pixels in the quadrant
	private QTreeNode parent; // The parent of the tree node
	private QTreeNode[] children; // The children of the tree node
	
	/**
	 * Constructor sets every variable to null except the children, which will be a 
	 * QTreeNode list of which all of the children will be set to null
	 */
	public QTreeNode() {
		
		this.parent = null;
		this.children = new QTreeNode[4];
		
		for(int i = 0; i < 4; i++) { this.children[i] = null; }
		
		this.x = 0;
		this.y = 0;
		this.size = 0; 
		this.color = 0;
		
	}
	
	/**
	 * Another constructor, which sets all the variables to the givens from the parameter
	 * except the parent, which is set to null
	 * @param theChildren Children of the node
	 * @param xcoord x coordinate of the top left corner of the quadrant
	 * @param ycoord y coordinate of the top left corner of the quadrant
	 * @param theSize The size of the quadrant
	 * @param theColor The average color of the quadrant
	 */
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		
		this.parent = null;
		this.children = theChildren;		
		this.x = xcoord;
		this.y = ycoord;
		this.size = theSize; 
		this.color = theColor;
		
	}
	
	/**
	 * Checks if the given coordinates are within the quadrant of the node 
	 * @param xcoord The x coordinate
	 * @param ycoord The y coordinate
	 * @return true if the coordinate is within the quadrant, otherwise false
	 */
	public boolean contains(int xcoord, int ycoord) {
		return x+size > xcoord && y+size > ycoord && xcoord >= x && ycoord >= y;
	}
	
	/**
	 * Getter method to get the x coordinate
	 * @return The x coordinate
	 */
	public int getx() {
		return this.x;
	}
	
	/**
	 * Getter method to get the y coordinate
	 * @return The y coordinate
	 */
	public int gety() {
		return this.y;
	}
	
	/**
	 * Getter method to get the size
	 * @return The size of the quadrant
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Getter method to get the average color
	 * @return The average color of the quadrant
	 */
	public int getColor() {
		return this.color;
	}
	
	/**
	 * Getter method to get the parent of the node
	 * @return The parent of the node
	 */
	public QTreeNode getParent() {
		return this.parent;
	}
	
	/**
	 * Getter method to get a specific child
	 * @param index The index of the child node that is wanted
	 * @return The child node requested based off the index
	 * @throws QTreeException Exception thrown if there are no children (null) or index is out of range
	 */
	public QTreeNode getChild(int index) throws QTreeException {
		if(this.children == null) {
			
			throw new QTreeException("QTreeException in getChild() method: null children");
			
		} else if(index < 0 || index > 3) {
			
			throw new QTreeException("QTreeException in getChild() method: index out of range");
			
		}else {
			
			return this.children[index];
			
		}
	}
	
	/**
	 * Setter method to set the x coordinate
	 * @param newx The new x coordinate
	 */
	public void setx(int newx) {
		this.x = newx;
	}
	
	/**
	 * Setter method to set the y coordinate
	 * @param newy The new y coordinate
	 */
	public void sety(int newy) {
		this.y = newy;
	}
	
	/**
	 * Setter method to set the size
	 * @param newSize The new size of the quadrant 
	 */
	public void setSize(int newSize) {
		this.size = newSize;
	}
	
	/**
	 * Setter method to set to the new color
	 * @param newColor The new average color of the quadrant
	 */
	public void setColor(int newColor) {
		this.color = newColor;
	}
	
	/**
	 * Setter method to set the parent of the node
	 * @param newParent The new parent node 
	 */
	public void setParent(QTreeNode newParent) {
		this.parent = newParent;
	}
	
	/**
	 * Setter method to set the child nodes based off the index given
	 * @param newChild The new child node
	 * @param index The index at which the child will be inserted
	 * @throws QTreeException Throws exception if the children don't exist or index is out of range
	 */
	public void setChild(QTreeNode newChild, int index) throws QTreeException {
		if(this.children == null) {
			
			throw new QTreeException("QTreeException in setChild() method: children is null");
			
		} else if(index < 0 || index > 3) {
			
			throw new QTreeException("QTreeException in setChild() method: index out of range");
			
		} else {
			
			this.children[index] = newChild;
			
		}
	}
	
	/**
	 * Checks if the node is a leaf, which is true if the node has no children
	 * @return True if the node is a leaf (no children), otherwise false
	 */
	public boolean isLeaf() {
		
		if(this.children == null) {
			
			return true;
			
		} else {
			
			for(QTreeNode child : this.children) {
				
				if(child != null) {
					return false;
				}
				
			}
			
			return true;
			
		}
		
	}
	
}