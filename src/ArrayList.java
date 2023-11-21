/*
 * File: DynamicArray.java
 * Purpose: This program creates a class that represents a resizeable array
 *          that you can do certain operations on.
 * Course: CSC 210, 1st semester
 */

/**
 * The DynamicArray class contains a 3 instance variables,
 * a default constructor, 2 getter methods, 7 other methods.
 */
public class ArrayList<T>
{
	private T array[];
	private int size;
	
	/**
	 * This default constructor initializes the array as an empty array of
	 * size 10 and sets the size to 0.
	 */
	public ArrayList()
	{
		array = (T[]) new Object[10];
		size=0;
	}
	
	/**
	 * This method adds a value to the end of the array. If there is no
	 * space left it will resize it to 2x the array.
	 * @param value: value of type T to add to the array
	 */
	public void add(T value)
	{
		if (size >= array.length)
			resize(2*array.length);
		array[size] = value;
		size++;
	}
	
	/**
	 * This method returns the element at the specified index in the array.
	 * @param index: integer index if array
	 * @return integer value in the array
	 */
	public T get(int index)
	{
		return array[index];
	}
	
	/**
	 * This method removes the element at the index and moves all the
	 * elements after it one space left.
	 * @param index: integer index of array
	 */
	public void remove(int index)
	{
		for(int i = index; i < size-1; i++)
			array[i] = array[i+1];
	}
	
	/**
	 * This method sets the size to 0 (effectively clears it)
	 */
	public void removeAll()
	{
		size = 0;
	}
	
	/**
	 * This method resizes the array to the size of capacity.
	 * @param capacity: integer length for the new array
	 */
	private void resize(int capacity)
	{
		T temp[] =(T[]) new Object[capacity];
		size = capacity < size ? capacity:size;
		for (int i=0; i < size; i++)
			temp[i] = array[i];
		array = temp;
	}
	
	/**
	 * This method returns a string of the elements of an array
	 */
	public String toString()
	{
		String str = "{";
		for(int i = 0; i < size; i++)
		{
			str += array[i];
			if(i != size-1)
				str += ",";
		}
		return str + "}";
	}
	
	/**
	 * This method returns the number of elements in the array.
	 * @return integer size
	 */
	public int size()
	{
		return size;
	}
}
