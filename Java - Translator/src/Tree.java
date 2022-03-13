import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 
 */

/**
 * Class to do things to trees and give them a starting point
 * @author Craig Methven
 *
 */
public class Tree {
	private String normalWordDoc;
	private String translationWordDoc;
	private TreeNode root;
	private static String balanceFileOriginal = "balanceFileOrginal.txt";
	private static String balanceFileTranslation = "balanceFileTransition.txt";

	/**
	 * constructor getting the files in and saving them to the correct place
	 * @param file1 original file
	 * @param file2 translation file
	 */
	Tree(String file1, String file2){
		root = null;
		normalWordDoc=file1;
		translationWordDoc=file2;
		readTree(1);
	}
	
	public TreeNode getRoot() {
		return(root);
	}
	
	public String getNormalWordDoc() {
		return(normalWordDoc);
	}
	public String getTranslationWordDoc() {
		return(translationWordDoc);
	}
	
	/**
	 * adds a node given a node
	 * @param addNode
	 */
	public void addNode(TreeNode addNode) {
		if(root==null) {
			root = addNode;
		}
		else {
			root.addNode(addNode,root);
		}
	}
	
	/**
	 * Adds a node to the tree given 2 words
	 * @param tempOriginal the unique word
	 * @param tempTranslation the translation
	 */
	public void addNode(String tempOriginal, String tempTranslation) {
		TreeNode newTreeNode = new TreeNode(tempOriginal, tempTranslation);
		addNode(newTreeNode);
	}
	
	/**
	 * 
	 */
	public void printTreeInOrder() {
		root.printInOrder();
	}
	
	public void printTreePreOrder() {
		root.printPreorder();
	}

	/**
	 * Deletes a node given a word that the node stores
	 * @param deleteWord the word wanting to be deleted
	 */
	public void deleteNode(String deleteWord) {
		TreeNode deleteNode = findTranslation(deleteWord,root);
		if(deleteNode == null) {
			return;
		}
		else {
			deleteNode(deleteNode);
		}
	}
		
	/**
	 * Deletes the node given a node
	 * @param deleteNode the node wanting to be deleted
	 */
	public void deleteNode(TreeNode deleteNode) {
		TreeNode upNodeOfDeleteNode = null;
		
		//This deletes the node!
		//Finds the node above the current one
		if(deleteNode.getUp()!=null) {
			upNodeOfDeleteNode = deleteNode.getUp();
			//Checks to see if the node to delete is to the left and if so deletes that
			if(upNodeOfDeleteNode.getLeft()!=null) {
				if(upNodeOfDeleteNode.getLeft()==deleteNode) {
					upNodeOfDeleteNode.setLeft(null);
				}
				//If it isn't the left that's to be deleted it deletes the right
				else {
					upNodeOfDeleteNode.setRight(null);
				}
			}			
		}
		//If there isn't a node above the one that needs to be deleted then it deletes the root
		else {
			root=null;
		}
		
		//For a branch with one leg
		if(deleteNode.getLeft() == null || deleteNode.getRight() ==null) {
			TreeNode tempNode = null;
			if(deleteNode.getLeft()==null) {
				tempNode = deleteNode.getRight();
			}
			else {
				tempNode = deleteNode.getLeft();
			}
			if(upNodeOfDeleteNode!=null) {
				if(upNodeOfDeleteNode.getOriginal().compareToIgnoreCase(deleteNode.getOriginal())>0) {
					upNodeOfDeleteNode.setLeft(tempNode);
				}
				else {
					upNodeOfDeleteNode.setRight(tempNode);
				}
			}
			else {
				root=tempNode;
			}
		}
		//For a branch with two legs
		else if(deleteNode.getLeft() != null && deleteNode.getRight() != null) {
			//Finds the replacement Node (tempNode)
			TreeNode replacementNode = deleteNode.getLeft();
			while(replacementNode.getRight()!=null) {
				replacementNode = replacementNode.getRight();
			}
			//deletes the replacement node from where it previously was
			deleteNode(replacementNode);
			//Replace the node references with the correct ones
			replacementNode.setLeft(deleteNode.getLeft());
			replacementNode.setRight(deleteNode.getRight());
			replacementNode.setUpNode(deleteNode.getUp());
			//Just wanting to point out that I found out you can chain methods together! I'm so happy that this works otherwise this would double the amount of lines this would be
			deleteNode.getLeft().setUpNode(replacementNode);
			deleteNode.getRight().setUpNode(replacementNode);
			
			//Put the replacement node in the correct place
			if(upNodeOfDeleteNode!=null) {
				if(upNodeOfDeleteNode.getOriginal().compareToIgnoreCase(deleteNode.getOriginal())>0) {
					upNodeOfDeleteNode.setLeft(replacementNode);
				}
				else {
					upNodeOfDeleteNode.setRight(replacementNode);
				}
			}
			else {
				root=replacementNode;
			}
		}
	}
	
	/**
	 * Method to initialise finding a translation
	 * @param findWord the word wanting to be found
	 * @return The translation
	 */
	public String findItemStart(String findWord) {
		TreeNode tempTree =null;
		if(root==null) {
			return(null);
		}
		tempTree = findTranslation(findWord, root);
		if(tempTree==null) {
			return(null);
		}
		return(tempTree.getTranslation());
	}
	
	/**
	 * finds the translation for the word 
	 * @param findWord the word that is wanting to be found
	 * @param currentNode the node in which it is currently looking
	 * @return the node in which the translation was found
	 */
	public TreeNode findTranslation(String findWord,TreeNode currentNode) {
		int tempInt = findWord.compareToIgnoreCase(currentNode.getOriginal());
		//If it is the item we are looking for then return current node
		if(tempInt==0) {
			return(currentNode);
		}
		//if it is greater than the current node look right
		else if(tempInt>0) {
			if(currentNode.getRight()!=null) {
				currentNode = findTranslation(findWord,currentNode.getRight());
				return(currentNode);
			}
		}
		//if it is less than the current node look left
		else if(tempInt<0) {
			if(currentNode.getLeft()!=null) {
				currentNode = findTranslation(findWord,currentNode.getLeft());
				return(currentNode);
			}
		}
		else {
			System.out.println("What did you input? This shouldn't happen! *brain explodes*");
		}
		currentNode=null;
		return(currentNode);
	}
	
	/**
	 * Saves the tree to a file
	 * @param typeOfSave 1 for saving preorder, 2 for saving in order
	 */
	public void saveTreeForOneLanguage(int typeOfSave) {
		root.setCounterForSaving(0);
		int treeSize = root.countTreeSize();
		TreeNode[] treeInArray = new TreeNode[treeSize];
		//Collects the tree in an array
		if(typeOfSave==1) {
			treeInArray = root.saveTreeToArrayPreorder(treeInArray, root);
		}
		else {
			treeInArray = root.saveTreeToArrayInorder(treeInArray,root);
		}
		
		//Regular print to file
		try {
			FileOutputStream stream1 = new FileOutputStream(balanceFileOriginal);
			PrintWriter printer1 = new PrintWriter(stream1);
			FileOutputStream stream2 = new FileOutputStream(balanceFileTranslation);
			PrintWriter printer2 = new PrintWriter(stream2);
			for(int i = 0; i<treeSize;i++) {
				printer1.println(treeInArray[i].getOriginal());
				printer2.println(treeInArray[i].getTranslation());
			}
			printer1.flush();
			printer1.close();
			printer2.flush();
			printer2.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file couldn't be created");
		}
	}
	
	/**
	 * reads in a file and saves it to the tree
	 * @param FileType 1 for the initial word documents, 2 for the documents used to balance the tree
	 * @return an array of tree nodes
	 */
	public TreeNode[] readTree(int FileType) {
		String tempNormalDoc;
		String tempTranslationDoc;
		
		//See where to read the file from
		if(FileType==1) {
			tempNormalDoc=normalWordDoc;
			tempTranslationDoc=translationWordDoc;
		}
		else {
			tempNormalDoc=balanceFileOriginal;
			tempTranslationDoc=balanceFileTranslation;
		}
		
		//Find the tree size by reading the whole file and adding one with each line gone through
		int treeSize = 0;
		try {
			FileReader reader1 = new FileReader(tempNormalDoc);
			BufferedReader buffer1 = new BufferedReader(reader1); 
			String nextLine = buffer1.readLine();
			
			while (nextLine != null)
			{
				nextLine = buffer1.readLine();
				treeSize++;
			}
			buffer1.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			
		}
		//delete current tree
		root = null;
		
		//Create an array of tree nodes to store the incoming data
		TreeNode[] treeInArray = new TreeNode[treeSize];
		try {
			TreeNode tempNode;
			//creates the buffers using input stream reader so that UTF can be used to read in the german characters
			BufferedReader buffer1 = new BufferedReader(new InputStreamReader(new FileInputStream(tempNormalDoc), "UTF-8")); 
			BufferedReader buffer2 = new BufferedReader(new InputStreamReader(new FileInputStream(tempTranslationDoc), "UTF-8")); 
			String nextLineNormal = buffer1.readLine();
			String nextLineTranslation = buffer2.readLine();
			int i=0;
			
			//read in the files line by line till there are no lines left
			while (nextLineNormal != null && nextLineTranslation != null)
			{
				//add the translation to the tree
				tempNode = new TreeNode(nextLineNormal,nextLineTranslation);
				addNode(tempNode);
				treeInArray[i] = tempNode;
				nextLineNormal = buffer1.readLine();
				nextLineTranslation = buffer2.readLine();
				i++;
			}
			buffer1.close();
			buffer2.close();

		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			
		}
		return(treeInArray);
	}
	
	/**
	 * balances the tree
	 */
	public void balanceTree() {
		saveTreeForOneLanguage(2);
		TreeNode[] treeInArray = readTree(2);
		int treeSize = root.countTreeSize();
		root = null;
		int numberOfRuns = 2;
		int i = 1;
		do {
			do {
				//Adds the nodes to the tree in the appropriate order going. For example in a list of 8 going: 4; 2,4,6,8; 1,2,3,4,5,6,7,8;
				addNode(treeInArray[((treeSize/numberOfRuns)*i)-1]);
				i++;
			//while it hasn't cycled through each node it needs to add this cycle
			}while((treeSize/numberOfRuns)*i<treeSize);
			i=1;
			numberOfRuns=numberOfRuns*2;
		}while(treeInArray.length==treeSize&&numberOfRuns<treeSize);
	}
}