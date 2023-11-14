import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class CreateTree {

	public static void main(String[] args) {
		// load in the images
		byte[] imageBytes = ImageBitExtraction.getBites("smileyface.jpg");
		ArrayList<Node> theNodes = addNodesToMap(imageBytes);
		PriorityQueue<Node> pQueue = addNodesToQueue(theNodes);
		// create a tree from the frequencies
		HuffmanTree huffmanTree = new HuffmanTree();
		Node root = huffmanTree.huffmanEncoding(pQueue);
		// maps each byte to their encoded values
		HashMap<Byte, String> encodingMappings = encodeBytesIntoMap(huffmanTree, root, imageBytes);
		ArrayList<String> encodedBytes = encodeBytes(imageBytes, encodingMappings);
		System.out.println(encodedBytes);
	}

	private static ArrayList<String> encodeBytes(byte[] imageBytes, HashMap<Byte, String> encodingMappings) {
		ArrayList<String> encodedBytes = new ArrayList<>(); 
		// adds all the encoded paths to the list
		for (Byte aByte : imageBytes) { 
			String path = encodingMappings.get(aByte);
			encodedBytes.add(path);
		}
		return encodedBytes;
	}

	private static HashMap<Byte, String> encodeBytesIntoMap(HuffmanTree huffmanTree, Node root, byte[] imageBytes) {
		HashMap<Byte, String> encodingMappings = new HashMap<>();
		for (Byte aByte : imageBytes) { 
			// only searches and adds to the tree when not yet exist
			if (!encodingMappings.containsKey(aByte)) {
				String path = huffmanTree.findPathToNode(aByte, root); 
				encodingMappings.put(aByte, path);
			}
		}
		return encodingMappings;
	}

	private static ArrayList<Node> addNodesToMap(byte[] imageBytes) {
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
		return theNodes;
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
			if (curNode.getValue() == aByte)
				return curNode;
		}
		return null;
	}

}
