
/**
 * This will do the conversion from the array of bytes from the image 
 * then will convert it into the proper form to run the encoding algorithm 
 * on It will also then use the tree to convert each of the bytes into their 
 * correct compressed forms. 
 * Date: 10/20/2023
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class CreateTree {

	public static void main(String[] args) {
		String imagePath = "src/snail.bmp";
		String encodedFilePath = "src/encodedBytesFile";
		// load in the images
		byte[] imageBytes = ImageBitExtraction.getBites(imagePath);
		ArrayList<Node> theNodes = addNodesToList(imageBytes);
		PriorityQueue<Node> pQueue = addNodesToQueue(theNodes);
		// create a tree from the frequencies
		HuffmanTree huffmanTree = new HuffmanTree();
		Node root = huffmanTree.huffmanEncoding(pQueue);
		// maps each byte to their encoded values
		HashMap<Byte, String> encodingMappings = encodeBytesIntoMap(huffmanTree, root, imageBytes);
		ArrayList<String> encodedBytes = encodeBytes(imageBytes, encodingMappings);
		byte[] theBytes = generateAllBytes(encodedBytes);
		// writes the compressed bytes to the file
		WriteToFile.write(theBytes, encodedFilePath);
		compareFileSizes(encodedFilePath, imagePath);
		// decode the bytes back to the original
		byte[] decodedBytes = decode(encodedBytes, huffmanTree);
		checkFilesMatch(imageBytes, decodedBytes);
	}

	/**
	 * This will compare two files of bytes to make sure that they match.
	 * 
	 * @param imageBytes   is the bytes from the image.
	 * @param decodedBytes is the bytes that where decoded.
	 * 
	 * @return The list of the encoded strings.
	 */
	private static void checkFilesMatch(byte[] imageBytes, byte[] decodedBytes) {
		// verify that each of the bytes match
		if (imageBytes.length != decodedBytes.length) {
			System.out.println("The decoded file is not the same as the original!");
			return;
		}
		for (int i = 0; i < imageBytes.length; i++) {
			if (imageBytes[i] != decodedBytes[i]) {
				System.out.println("The decoded file is not the same as the original!");
				return;
			}
		}
		System.out.println("The file was decoded successfully!");
	}

	/**
	 * This will compare the size of two files in order to see how much we where
	 * able to compress the images by.
	 * 
	 * @param encodedFile Is the encoded file of bytes.
	 * @param ImageFile   is the original image file.
	 * 
	 * @return The list of the encoded strings.
	 */
	private static void compareFileSizes(String encodedFile, String ImageFile) {
		File file1 = new File(encodedFile);
		File file2 = new File(ImageFile);
		// gets the number of bytes used in each file
		long file1Length = file1.length();
		long file2Length = file2.length();
		// calculate the percentage that was saved or lost
		if (file1Length < file2Length) {
			long bytesSaved = file2Length - file1Length;
			String percentage = String.format("%.2f", ((bytesSaved * 1.0) / (file2Length * 1.0)) * 100);
			System.out.println("The image was compressed by " + percentage + "%");
		} else {
			long bytesSaved = file1Length - file2Length;
			String percentage = String.format("%.2f", ((bytesSaved * 1.0) / (file2Length * 1.0)) * 100);
			System.out.println("The image used an extra " + percentage + "%");
		}
	}

	/**
	 * This will convert the array of strings into the correct form of bytes.
	 * 
	 * @param encodedBytes The encoded path on the tree.
	 * 
	 * @return The converted list of bytes.
	 */
	private static byte[] generateAllBytes(ArrayList<String> encodedBytes) {
		String curByte = "";
		// get the bytes in forms of 8's
		ArrayList<String> allBytes = new ArrayList<>(); 
		for (int i = 0; i < encodedBytes.size(); i += 8) {
			// determine when the byte is full
			String byteSequence = allBytes.get(i); 
			int byteSize = curByte.length() + byteSequence.length();  
			if (byteSize < 8) {
				curByte += byteSequence;
			} else {
				String firstSequence = byteSequence.substring(0, byteSize - 8);
				String secondSequence = byteSequence.substring(byteSize - 8, byteSequence.length()); 
				curByte += firstSequence; 
				allBytes.add(curByte); 
				curByte = "";
			}
		}
		byte[] theBytes = new byte[allBytes.size()];
		for (int i = 0; i < allBytes.size(); i++) {
			theBytes[i] = (byte) Integer.parseInt(allBytes.get(i), 2);
		}
//		return theBytes;
//
//		// convert to a single string of bytes
//		StringBuilder allBits = new StringBuilder();
//		for (String encodedString : encodedBytes) {
//			allBits.append(encodedString);
//		}
//		// get the bytes in forms of 8's
//		byte[] theBytes = new byte[allBits.length() / 8];
//		for (int i = 0; i < encodedBytes.size(); i += 8) {
//			int end = i + 8 < allBits.length() ? i : allBits.length();
//			allBits.substring(i, end);
//		}
//		for (int i = 0; i < allBits.length() / 8; i++) {
//			theBytes[i] = (byte) Integer.parseInt(encodedBytes.get(i), 2);
//		}
		return theBytes;
	}

	/**
	 * This will decode the original path into its original list of bytes.
	 * 
	 * @param encodedBytes The encoded path on the tree.
	 * @param huffmanTree  The tree used for encoding.
	 * 
	 * @return The converted list of bytes.
	 */
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
	 * @param aByte    Is the byte to search for.
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
