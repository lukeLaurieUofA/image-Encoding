/**
 * This will provide all the needed tree operations to perform a huffman encoding algorithm. 
 * The program will allow the user to both encode and decode a frequency table such that the 
 * bytes are minimized. 
 * Date: 10/20/2023
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class HuffmanTree {	
	Node root;
	
	public Node huffmanEncoding(HashMap<Node, Integer> frequencyTable) {
		// just testing feel free to remove 
		Node test0 = new Node(null, null, "x"); 
		Node test1 = new Node(null, test0, "a"); 
		Node test2 = new Node(null, test1, "b"); 
		Node test3 = new Node(null, null, "c"); 
		Node test4 = new Node(test2, test3, "d"); 
		Node test5 = new Node(null, null, "e"); 
		root = combineTrees(test4, test5);
		System.out.println(findPathToNode("x", root)); 
		printTree();
		return null;
		//TODO -> constantly combine max until only one tree node left
	} 
	
	/**
	 * This will print the tree out using a DFS search.
	 */
	public void printTree() {
		LinkedList<Node> queue = new LinkedList<Node>(); 
		queue.add(root); 
		while (!queue.isEmpty()) {
			// print each layer of the tree row by row 
			Node curNode = queue.removeFirst(); 
			if (null != curNode.getValue()) System.out.print(curNode.getValue() + " "); 
			if (null != curNode.getLeft()) queue.addLast(curNode.getLeft());
			if (null != curNode.getRight()) queue.addLast(curNode.getRight());
		}
	}
	
	/**
	 * This will find the path to a Node such that every left child gives 
	 * a value of 0 and every right child gives a value of 1.
	 * 
	 * @param nodeValue is the value of the node to search for.
	 * @param curNode    Is the current node being searched in the recursion
	 * 
	 * @return The String containing the path or null if it does not exist.
	 */
	private String findPathToNode(String nodeValue, Node curNode) {
		if (null == curNode) return null; 
		if (null != curNode.getValue() && curNode.getValue().equals(nodeValue)) return ""; 
		// search left and right subtrees to find a node
		String leftSearch = findPathToNode(nodeValue, curNode.getLeft());
		if (null != leftSearch) return "0" + leftSearch;
		String rightSearch = findPathToNode(nodeValue, curNode.getRight());
		if (null != rightSearch) return "1" + rightSearch; 
		return null;
	}
	
	/**
	 * Checks if a node is a leaf.
	 * 
	 * @param curNode    Is the node to check.
	 * 
	 * @return If the node is a leaf or not.
	 */
	private boolean isLeaf(Node node) {
		return node.getLeft() == null && node.getRight() == null;
	}
	
	/**
	 * This will create a new tree out of two given subtrees.
	 * 
	 * @param leftTree is the left subtree.
	 * @param rightTree is the right subtree.
	 * 
	 * @return The new parent of the nodes.
	 */
	private Node combineTrees(Node leftTree, Node rightTree) {
		Node combinedTree = new Node(leftTree, rightTree, null); 
		return combinedTree;
	}
}
