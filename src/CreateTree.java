
/**
 * This will do the conversion from the array of bytes from the image 
 * then will convert it into the proper form to run the encoding algorithm 
 * on It will also then use the tree to convert each of the bytes into their 
 * correct compressed forms. 
 * Date: 10/20/2023
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class CreateTree {

	public static void main(String[] args) {
		// load in the images
		byte[] imageBytes = ImageBitExtraction.getBites("src/snail.bmp");
		ArrayList<Node> theNodes = addNodesToList(imageBytes);
		PriorityQueue<Node> pQueue = addNodesToQueue(theNodes);
		// create a tree from the frequencies
		HuffmanTree huffmanTree = new HuffmanTree();
		Node root = huffmanTree.huffmanEncoding(pQueue);
		// maps each byte to their encoded values
		HashMap<Byte, String> encodingMappings = encodeBytesIntoMap(huffmanTree, root, imageBytes);
		ArrayList<String> encodedBytes = encodeBytes(imageBytes, encodingMappings);
		// decode the bytes back to the original 
		
		byte[] decodedBytes = decode(encodedBytes, huffmanTree);
		System.out.println(decodedBytes);
	}

	private static byte[] decode(ArrayList<String> encodedBytes, HuffmanTree huffmanTree) {
		byte[] decodedBytes = new byte[encodedBytes.size()]; 
		for (int i = 0; i < encodedBytes.size(); i++) {
			decodedBytes[i] = huffmanTree.findByteFromPath(encodedBytes.get(i), huffmanTree.root);
		}
		return decodedBytes;
	}

	/**
	 * This will take all of the encoding mappings from the map, and convert each of
	 * the bytes into their correct compressed forms.
	 * 
	 * @param imageBytes       is the bytes from the image.
	 * @param encodingMappings Is the map containing all of the encodings.
	 * 
	 * @return The list of the encoded strings.
	 */
	private static ArrayList<String> encodeBytes(byte[] imageBytes, HashMap<Byte, String> encodingMappings) {
		ArrayList<String> encodedBytes = new ArrayList<>();
		// adds all the encoded paths to the list
		for (Byte aByte : imageBytes) {
			String path = encodingMappings.get(aByte);
			encodedBytes.add(path);
		}
		return encodedBytes;
	}

	/**
	 * This will take use the tree and map all the needed values into their correct
	 * paths in the tree.
	 * 
	 * @param huffmanTree Is the tree with the methods to get the path.
	 * @param root        Is the root of the tree.
	 * @param imageBytes  Is the bytes from the image.
	 * 
	 * @return The map containing the path mappings.
	 */
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

	/**
	 * This will add all of the nodes to a list and keep track of the frequencies
	 * for each of the nodes.
	 * 
	 * @param imageBytes Is the bytes from the image.
	 * 
	 * @return The list of nodes.
	 */
	private static ArrayList<Node> addNodesToList(byte[] imageBytes) {
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

	/**
	 * This will add all of the nodes into a priority queue based on their specified
	 * frequencies.
	 * 
	 * @param theNodes Is the list of nodes containing the frequencies.
	 * 
	 * @return The priority queue contains all the nodes in the correct order.
	 */
	private static PriorityQueue<Node> addNodesToQueue(ArrayList<Node> theNodes) {
		PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new NodeComparator());
		for (Node curNode : theNodes) {
			pQueue.add(curNode);
		}
		return pQueue;
	}

	/**
	 * This will find the node specified the the byte.
	 * 
	 * @param theNodes Is the list of nodes containing the frequencies.
	 * @param aByte Is the byte to search for.
	 * 
	 * @return The found Nodes
	 */
	private static Node findNode(ArrayList<Node> theNodes, byte aByte) {
		for (Node curNode : theNodes) {
			if (curNode.getValue() == aByte)
				return curNode;
		}
		return null;
	}

}
