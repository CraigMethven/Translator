/**
 * Stores all the data in the tree and has a few functions 
 * @author Craig
 *
 */

public class TreeNode {
	private TreeNode left;
	private TreeNode right;
	private TreeNode up;
	private String original;
	private String translation;
	private static int counterForSaving = 0;
	
	TreeNode(){
		left = null;
		right = null;
		original = null;
		translation = null;
		up = null;
	}
	
	TreeNode(String tempOriginal, String tempTranslation){
		left = null;
		right = null;
		original = tempOriginal;
		translation = tempTranslation;
		up = null;
	}
	
	public TreeNode getLeft() {
		return(left);
	}
	
	public TreeNode getRight() {
		return(right);
	}
	
	public TreeNode getUp() {
		return(up);
	}
	
	public String getOriginal() {
		return(original);
	}
	
	public String getTranslation() {
		return(translation);
	}
	
	public void setUpNode(TreeNode tempUp) {
		up = tempUp;
	}
	
	public void setLeft(TreeNode tempNode) {
		left = tempNode;
	}
	
	public void setRight(TreeNode tempNode) {
		right = tempNode;
	}
	
	public void setCounterForSaving(int tempCounter) {
		counterForSaving = tempCounter;
	}
	
	/**
	 * prints tree inorder
	 */
	public void printInOrder() {
		if(left!=null) {
			left.printInOrder();
		}
		printNode();
		if(right!=null) {
			right.printInOrder();	
		}
	}
	
	/**
	 * prints tree post order
	 */
	public void printPreorder() {
		printNode();
		if(left!=null) {
			left.printInOrder();
		}
		if(right!=null) {
			right.printInOrder();	
		}
	}
	
	/**
	 * prints the node for the dictionary
	 */
	public void printNode() {
		System.out.println("Original:  " + original+"\t\t\t\tTranslation:  "+translation);
	}
	
	/**
	 * counts the size of the tree using recursion
	 * @return the size of the tree
	 */
	public int countTreeSize() {
		int total = 0;
		if(left!=null) {
			total +=left.countTreeSize();
		}
		if(right!=null) {
		}
		return(total+1);
	}
	
	/**
	 * Saves the tree to an array in preorder
	 * @param array the array being saved to
	 * @param current the node that the program is currently on
	 * @return the array
	 */
	public TreeNode[] saveTreeToArrayPreorder(TreeNode[] array, TreeNode current) {
		array[counterForSaving] = current;
		counterForSaving++;
		if(left!=null) {
			left.saveTreeToArrayPreorder(array,left);
		}
		if(right!=null) {
			right.saveTreeToArrayPreorder(array,right);
		}
		return(array);
	}
	
	/**
	 * Saves the tree to an array in inorder
	 * @param array the array being saved to
	 * @param current the node that the program is currently on
	 * @return the array
	 */
	public TreeNode[] saveTreeToArrayInorder(TreeNode[] array, TreeNode current) {
		if(left!=null) {
			left.saveTreeToArrayPreorder(array,left);
		}
		array[counterForSaving] = current;
		counterForSaving++;
		if(right!=null) {
			right.saveTreeToArrayPreorder(array,right);
		}
		return(array);
	}
	
	/**
	 * adds a node to the tree
	 * @param addNode the node to be added
	 * @param currentNode the node the recurssion is currently on
	 */
	public void addNode(TreeNode addNode,TreeNode currentNode) {
		int tempInt = addNode.getOriginal().compareToIgnoreCase(original);
		if(tempInt==0) {
		}
		else if(tempInt<0) {
			if(left==null) {
				addNode.setUpNode(currentNode);
				left=addNode;
			}
			else {
				left.addNode(addNode,left);
			}
		}
		else if(tempInt>0) {
			if(right==null) {
				addNode.setUpNode(currentNode);
				right=addNode;
			}
			else {
				right.addNode(addNode,right);
			}
		}
		else {
			System.out.println("This isn't correct! What did you do? Soemthing went wrong with node references");
		}
	}
}