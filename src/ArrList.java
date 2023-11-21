/*
 * File: ArrList.java
 * Class: CSC 345
 * Professor: Melanie Lotz
 * Purpose: Implements an ArrayList data type using a circular array for efficiency.
 */

public class ArrList {
	private Array arr;
	private int front;
	private int back;
	private int size;

	/**
	 * Default constructor, initializes array length to 10
	 */
	public ArrList() {
		arr = new Array(10);
		front = 0;
		back = -1;
		size = 0;
	}

	/**
	 * Paramaterized constructor initializes variables
	 * 
	 * @param cap: integer length of the underlying array
	 */
	public ArrList(int cap) {
		arr = new Array(cap);
		front = 0;
		back = -1;
		size = 0;
	}

	/**
	 * adds an element to the last position and updates the size and back pointers
	 * (O(1))
	 * 
	 * @param num: integer value to add to the array
	 */
	public void addLast(int num) {
		int length = arr.length();
		resize(true);

		if (back == arr.length() - 1) {
			back = 0;
		} else {
			back++;
		}

		arr.setVal(back, num);
		size++;
	}

	/**
	 * adds an element to the last position and updates the size and back pointers
	 * (O(1))
	 * 
	 * @param num: integer value to add to the array
	 */
	public void addFirst(int num) {
		int length = arr.length();
		resize(true);

		if (front == 0) {
			front = arr.length() - 1;
		} else {
			front--;
		}

		arr.setVal(front, num);
		size++;
	}

	/**
	 * returns the value at the passed index (O(1))
	 * 
	 * @param i: index to get value of
	 * @return integer value at index
	 */
	public int get(int i) {
		return arr.getVal((front + i) % arr.length());
	}

	/**
	 * finds the index of the first occurrence of <num> (O(N))
	 * 
	 * @param num: integer value in array
	 * @return integer index of num or -1 if not in list
	 */
	public int indexOf(int num) {
		int length = arr.length();
		for (int i = 0; i < size; i++) {
			if (arr.getVal((front + i) % length) == num) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * checks if the array contains <num> (O(N))
	 * 
	 * @param num: integer value to check
	 * @return True if the list contains <num> and False otherwise
	 */
	public boolean contains(int num) {
		for (int i = 0; i < arr.length(); i++) {
			if (arr.getVal(i) == num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * returns if the array is empty(O(1))
	 * 
	 * @return True if empty, false otherwise
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * returns the last value (O(N))
	 * 
	 * @param num: integer value to search for
	 * @return integer index of num or -1 if not found
	 */
	public int lastIndexOf(int num) {
		int length = arr.length();
		for (int i = size; i >= 0; i--) {
			if (arr.getVal((front + i) % length) == num) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * removes the first element of the array (O(1))
	 * 
	 * @return removed integer value
	 * @throws EmptyListException
	 */
	public int removeFirst(){
		int removedVal = -1;
		if (!this.isEmpty()) {
			removedVal = arr.getVal(front);
			size--;
			int length = arr.length();

			if (front == length - 1) {
				front = 0;
			} else {
				front++;
			}

			this.resize(false);
		}
		return removedVal;
	}

	/**
	 * removes the last element of the array (O(1))
	 * 
	 * @return removed integer value
	 * @throws EmptyListException
	 */
	public int removeLast(){
		int removedVal = -1;
		if (!this.isEmpty()) {
			removedVal = arr.getVal(back);
			size--;
			int length = arr.length();

			if (back == 0) {
				back = length - 1;
			} else {
				back--;
			}

			this.resize(false);
		}

		return removedVal;
	}

	/**
	 * removes value at index in array (O(N)
	 * 
	 * @param i: index of value to remove
	 * @return integer of removed value or -1 if not found
	 * @throws EmptyListException
	 */
	public int removeByIndex(int i){
		int removedVal = -1;
		if (!this.isEmpty()) {
			int index = (front + i) % arr.length();
			removedVal = arr.getVal(index);
			moveOverArray(index);
		}

		return removedVal;
	}

	/**
	 * Removes passed value from array (O(N)
	 * 
	 * @param num: integer value to remove
	 * @return true if successful, false otherwise
	 * @throws EmptyListException
	 */
	public boolean removeByValue(int num){
		boolean wasRemoved = false;
		if (!this.isEmpty()) {
			int length = arr.length();
			int index = -1;

			for (int i = 0; i < size; i++) {
				if (arr.getVal((front + i) % length) == num) {
					index = (front + i) % length;
					break;
				}
			}
			if (index >= 0) {
				moveOverArray(index);
				wasRemoved = true;
			}
		}

		return wasRemoved;
	}

	/**
	 * moves all elements after the removed value to the left
	 * 
	 * @param index: removed value index
	 */
	private void moveOverArray(int index) {
		for (int j = index; j != back; j = (j + 1) % arr.length()) {
			int nextVal = 0;
			if (j == arr.length() - 1) {
				nextVal = arr.getVal(0);
			} else {
				nextVal = arr.getVal(j + 1);
			}

			arr.setVal(j, nextVal);
		}

		size--;
		back--;

		resize(false);
	}

	/**
	 * Sets the passed index in array to the passed value <num>(O(1))
	 * 
	 * @param index: integer index in array
	 * @param num:   integer value to set
	 * @return 0
	 */
	public int set(int index, int num) {
		arr.setVal((front + index) % arr.length(), num);
		return 0;
	}

	/**
	 * return the number of elements in the list (O(1))
	 * 
	 * @return integer size
	 */
	public int size() {
		return size;
	}

	/**
	 * returns the front variable (used for own testing)
	 * 
	 * @return integer front index
	 */
	public int getFront() {
		return front;
	}

	/**
	 * returns the back variable (used for own testing)
	 * 
	 * @return integer back index
	 */
	public int getBack() {
		return back;
	}

	/**
	 * returns the array (used for own testing)
	 * 
	 * @return local Array object
	 */
	public Array getArr() {
		return arr;
	}

	/**
	 * resizes the array if it is full or less than 1/4 full
	 * 
	 * @param makeBigger: True if doubling the array, False if dividing it by 2
	 */
	private void resize(boolean makeBigger) {
		int length = arr.length();
		if (makeBigger) {
			if (size >= length) {
				arr.resize(2 * length, front, size);
				front = 0;
				back = size - 1;
			}
		} else {
			if (length >= 20 && size < (length / 4)) {
				arr.resize((length / 2), front, size);
				front = 0;
				back = size - 1;
			}
		}
	}
}
