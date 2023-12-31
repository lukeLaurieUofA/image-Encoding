/**
 * This will provide all the needed data to keep track of a single node in a tree
 * Date: 10/20/2023
 */
public class Node{

	private Node left;
	private Node right;
	private Byte value;
	private int frequency;

	public Node(Node left, Node right, Byte value, int frequency) {
		this.left = left;
		this.right = right;
		this.value = value;
		this.frequency = frequency;
	}

	/**
	 * @return the left
	 */
	public Node getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Node left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public Node getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Node right) {
		this.right = right;
	}

	/**
	 * @return the value
	 */
	public Byte getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Byte value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getFrequency() {
		return frequency;
	}
	
	/**
	 * @param value of the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public String toString() {
		Byte value = this.value==null ? null : this.value;
		return "{value: " + value + ", frequency = " + this.frequency + "}";
	}
}
