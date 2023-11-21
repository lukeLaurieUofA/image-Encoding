public class NodeQueue {
	
	private int size;
	private Node[] arr;
	
	NodeQueue(int initialSize){
		this.arr = new Node[initialSize];
		this.size = 0;
	}
	NodeQueue(){
		this.arr = new Node[10];
		this.size = 0;
	}
	
	/**
	 * transfers the contents of the passed in array to a new larger array and returns it
	 * @param oldArr: the array to copy
	 * @return the new array
	 */
	private Node[] growArr(Node[] oldArr) {
		Node[] newArr = new Node[oldArr.length*2];
		for (int i = 0; i < oldArr.length; i++) {
			newArr[i] = oldArr[i];
		}
		return newArr;
	}
	
	/**
	 * compares the priority of two nodes
	 * @param N1
	 * @param N2
	 * @return returns true if N1 has a lower frequency than P2
	 */
	private boolean compareNodes(Node N1, Node N2) {
		if (N2 == null) {
			return true;
		}
		if (N1.getFrequency() <= N2.getFrequency()) {
			return true;
		}
		return false;
	}
	
	/**
	 * bubbles the element at the passed in index up the heap recursively until it has a lower frequency than its children
	 * @param index: index of the element to bubble up
	 */
	private void bubbleUp(int index) {
		if (!(index <= 1 || !compareNodes(this.arr[index], this.arr[index/2]))) {
			Node temp = this.arr[index];
			this.arr[index] = this.arr[index/2];
			this.arr[index/2] = temp;
			this.bubbleUp(index/2);
		}
	}
	
	/**
	 * bubbles the element at the passed in index down the heap recursively until it has higher frequency than its parents
	 * @param index: index of the element to bubble down
	 */
	private void bubbleDown(int index) {
		if (index * 2 < this.size) {
			int higherPriorityChild = index * 2;
			if (index * 2 + 1 <= this.size && compareNodes(this.arr[index * 2 + 1], this.arr[index * 2])) {
				higherPriorityChild = index * 2 + 1;
			}
			
			if (compareNodes(this.arr[higherPriorityChild], this.arr[index])) {
				Node temp = this.arr[higherPriorityChild];
				this.arr[higherPriorityChild] = this.arr[index];
				this.arr[index] = temp;
				this.bubbleDown(higherPriorityChild);
			}
		}
	}
	
	/**
	 * queues a node
	 * @param n: Node to add
	 */
	public void add(Node n) {
		if (this.arr.length == this.size + 1) {
			this.arr = growArr(this.arr);
		}
		this.size++;
		this.arr[size] = n;
		this.bubbleUp(size);
	}
	
	/**
	 * checks if the queue is empty
	 * @return true if the queue is empty
	 */
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	/**
	 * Gets the size.
	 * @return Gets the size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * polls the lowest frequency node and returns it
	 * @return: Node that was removed
	 * @throws Exception 
	 */
	public Node poll() throws EmptyQueueException {
		if (this.size == 0) {
			throw new EmptyQueueException("Node queue is empty!");
		}
		Node temp = this.arr[1];
		this.arr[1] = this.arr[size];
		this.arr[size] = null;
		this.size--;
		this.bubbleDown(1);
		return temp;
	}
}
