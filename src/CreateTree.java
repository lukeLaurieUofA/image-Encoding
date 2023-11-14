import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class CreateTree {

	public static void main(String[] args) {
		// load in the images
		ImageBitExtraction imageBitClass = new ImageBitExtraction();
		byte[] imageBytes = imageBitClass.getBites("smileyface.jpg");
		HashMap<Byte, Integer> freqTable = new HashMap<>();
		// generate a list of nodes containing the frequencies
		ArrayList<Node> theNodes = new ArrayList<>();
		for (Byte aByte : imageBytes) {
			Node curNode = findNode(theNodes, aByte);
			// adds the new node or updates if already exists
			if (null == curNode) {
				curNode = new Node(null, null, aByte, 1);
				theNodes.add(curNode);
			} else {
				curNode.setFrequency(curNode.getFrequency() + 1);
			}
		}
		PriorityQueue<Node> pQueue = addNodesToQueue(theNodes);
		HuffmanTree huffmanTree = new HuffmanTree();
		Node root = huffmanTree.huffmanEncoding(pQueue);
	}

	private static PriorityQueue<Node> addNodesToQueue(ArrayList<Node> theNodes) {
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new NodeComparator());
		for (Node curNode : theNodes) {
			pQueue.add(curNode);
		} 
		return pQueue;
	}

	private static Node findNode(ArrayList<Node> theNodes, byte aByte) {
		for (Node curNode : theNodes) {
			if (curNode.getValue() == aByte) return curNode;
		}
		return null;
	}

}
