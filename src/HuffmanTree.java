/**
 * This will provide all the needed tree operations to perform a huffman encoding algorithm. 
 * The program will allow the user to both encode and decode a frequency table such that the 
 * bytes are minimized. 
 * Date: 10/20/2023
 */
import java.util.LinkedList;
import java.util.PriorityQueue;

public class HuffmanTree {	
	Node root;
	
	public Node huffmanEncoding(NodeQueue pQueue) throws EmptyQueueException {
		//loop until there is only one Node in the queue.
		//This node will be the root of the final tree.
		while (pQueue.size() > 1) {
			Node n1 = pQueue.poll();
			Node n2 = pQueue.poll();
			pQueue.add(combineTrees(n1, n2));
		}
		root = pQueue.poll();
		return root;
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
			//if (null != curNode.getValue()) System.out.print(curNode.getValue() + " "); 
			System.out.println(curNode.toString() + " "); 
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
	public String findPathToNode(Byte nodeValue, Node curNode) {
		if (null == curNode) return null; 
		if (null != curNode.getValue() && curNode.getValue() == nodeValue) return ""; 
		// search left and right subtrees to find a node
		String leftSearch = findPathToNode(nodeValue, curNode.getLeft());
		if (null != leftSearch) return "0" + leftSearch;
		String rightSearch = findPathToNode(nodeValue, curNode.getRight());
		if (null != rightSearch) return "1" + rightSearch; 
		return null;
	}
	
	/**
	 * This will search a specified path on the tree in order to find the 
	 * byte which is searching for.
	 * 
	 * @param path is the path in which to search for the byte for
	 * @param curNode    Is the current node being searched in the recursion
	 * 
	 * @return The Byte that was found at the specified path.
	 */
	public Byte findByteFromPath(String path, Node curNode) {
		if (null == curNode) return null; 
		if (path.equals("")) {
			return curNode.getValue();
		}
		// determine to search the left or right node 
		Node searchNode = path.charAt(0) == '0' ? curNode.getLeft() : curNode.getRight();
		return findByteFromPath(path.substring(1), searchNode);
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
		Node combinedTree = new Node(leftTree, rightTree, null, leftTree.getFrequency() + rightTree.getFrequency()); 
		return combinedTree;
	}
}
