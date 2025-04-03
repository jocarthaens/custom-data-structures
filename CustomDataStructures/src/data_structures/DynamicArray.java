package data_structures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<E> implements Iterable<E>, IListArray<E>, IDequeArray<E>, IStackArray<E> {
	protected static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
	protected static final int DEFAULT_CAPACITY = 1 << 3;
	
	protected E[] array;
	protected int size;
	protected int front;
	
	protected ArrayIterator arrayIterator;
	
	// a versatile, insertion-ordered array that can serve as a list, stack or circular queue (and soon, priority queue)
	// aims to optimize speed of ordered insertion and post-removal shifting using circular queue implementation
	// where elements are moved either forward or backward, whichever has the least motion.
	// worst case complexity will be n/2 time, best case will be amortized constant time (on either head or tail)
	// due with a slight cost of lookup (conversion of index position to internal index position)
	// linkedlist, arraylist, stack, queue, deque, circular queue, priority queue
	
	
	@SuppressWarnings("unchecked")
	public DynamicArray() {
		array = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
		front = 0;
		//Arrays.sort(array, front, DEFAULT_CAPACITY);
	}
	
	
	
	

	
	public void add(E element) {
		pushBack(element);
	}
	
	public void push(E element) {
		pushFront(element);
	}
	
	public void pushFront(E element) {
		if (element == null) throw new NullPointerException("Null elements are not allowed.");
		// insert checks for maxed array;
		front = wrapIndex(--front);
		array[front] = element;
		size++;
		if (size >= array.length) growCapacity();
	}
	
	public void pushBack(E element) {
		if (element == null) throw new NullPointerException("Null elements are not allowed.");
		// insert checks for maxed array;
		int rear = wrapIndex(front + size);
		array[rear] = element;
		size++;
		if (size >= array.length) growCapacity();
	}
	
	
	
	
	
	public void insert(E element, int index) {
		if (element == null) throw new NullPointerException("Null elements are not allowed.");
		// insert checks for maxed array;
		if (size == array.length) return; // change 
		if (index > size || index < 0) 
			throw new IndexOutOfBoundsException("New index must be bounded within the inclusive range"
					+ " {0, "+(size)+"}. Index = "+index+", Size = "+size+".");
		
		int start = wrapIndex(front + index);
		int capacity = array.length;
		int rightShift = size - index;  // why size instead of (size - 1)? Could be due to insertion of new index
		int leftShift = index;
		
		System.out.println("\nLocal Index:	"+index);
		System.out.println("Front Index:	"+front);
		System.out.println("New Rear Index:	"+wrapIndex(front + size));
		System.out.println("Actual Index:	"+start);
		System.out.println("Forward Shift:	"+rightShift);
		System.out.println("Backward Shift:	"+leftShift);
		
		
		// shifting algorithms work but is somewhat hacky
		
		///*
		if (rightShift <= leftShift) { // shift elements forward
			
			// why size instead of (size - 1), Could be due to insertion of new index, increasing the size.
			int newRear = wrapIndex(front + size);
			
			//System.out.println(start + rightShift);
			if (start + rightShift < capacity) {
				System.out.println("ey");
				//printArray();
				System.arraycopy(array, start, array, start + 1, rightShift);
				//printArray();
			}	
			else {
				System.out.println("bb");
				//printArray();
				System.arraycopy(array, 0, array, 1, newRear); // newRear represents number of elements from 0 towards the end?
				//printArray();
				array[0] = array[capacity - 1];
				//printArray();
				System.arraycopy(array, start, array, start + 1, capacity - (start + 1)); // why cap - (s + 1)?
				//printArray();
				//System.out.println(capacity - size);
				//System.out.println(capacity - (start + 1));
				//System.out.println(capacity - (start + 1) + rear);
			}
			//System.out.println("Forward");
			array[start] = element;
		}
		//*/	
		
		///*
		else { // shift elements backward
			
			if (array[0] == null) {  // if front < (int rear = wrapIndex(front + size - 1)) && front > 0
				System.out.println("Oii");
				//printArray();
				System.arraycopy(array, front, array, front - 1, leftShift);
				//printArray();
			}
			else {
				System.out.println("Oh no");
				//printArray();
				System.arraycopy(array, front, array, wrapIndex(front - 1), leftShift - start); // Remaining shifts after shifting <start> times
				//printArray();
				array[capacity - 1] = array[0];
				//printArray();
				System.arraycopy(array, 1, array, 0, start); // Only shift <start> times than <rear> times to reduce shifting
				//printArray();
			}
			//System.out.println("Backward");
			front = wrapIndex(--front);
			// Decrements start due to decremented front that affects element order.
			array[wrapIndex(start - 1)] = element; 
		}
		//*/
		
		size++;
		if (size >= capacity) growCapacity();
		
	}
	
	
	
	
	
	
	
	public E peek() {
		return peekFront();
	}
	
	public E peekFront() {
		if (size <= 0) return null;
		return array[front];
	}
	
	public E peekBack() {
		if (size <= 0) return null;
		return array[wrapIndex(front + size - 1)];
	}
	
	public E get(int index) {
		if (index < size || index >= 0) {
			return array[wrapIndex(front + index)];
		}
		return null;
	}
	
	
	
	public boolean contains(E element) {
		if (element == null) return false;
		for (int c = 0, i = front; c < size; c++) {
			if ( element.equals(array[i]) ) return true;
			i = wrapIndex(++i);
		}
		return false;
	}

	public int instanceCount(E element) {
		int count = 0;
		if (element != null) {
			for (int c = 0, i = front; c < size; c++) {
				if ( element.equals(array[i]) ) count++;
				i = wrapIndex(++i);
			}
		}
		return count;
	}
	
	
	
	public int find(E element) {
		return firstIndexOf(element);
	}
	
	// starts iterating at front, going forward until >= size
	public int firstIndexOf(E element) { //
		if (element != null) {
			for (int c = 0, i = front; c < size; c++) {;
				if (element.equals(array[i])) 
					return c; // index count from front, going forward
				i = wrapIndex(++i);
			}
		}
		return -1;
	}
	
	// starts iterating at back, going backward until >= size
	public int lastIndexOf(E element) { //
		if (element != null) {
			for (int c = 0, i = wrapIndex(front + size - 1); c < size; c++) {
				if (element.equals(array[i])) 
					return (size - 1 - c);  // index count from front, going forward
				i = wrapIndex(--i);
			}
		}
		return -1;
	}
	
	
	
	
	
	public E pop() {
		return popFront();
	}

	public E popFront() {
		if (size <= 0) return null;
		E element = array[front];
		array[front] = null;
		front = wrapIndex(++front);
		size--;
		// insert size checks for shrinks
		return element;
	}
	
	public E popBack() {
		if (size <= 0) return null;
		int rear = wrapIndex(front + size - 1);
		E element = array[rear];
		array[rear] = null;
		size--;
		// insert size checks for shrinks
		return element;
	}
	
	// unfinished code
	public E remove(int index) {
		if (size <= 0) return null;
		if (index >= size || index < 0) 
			throw new IndexOutOfBoundsException("Index must be bounded within the inclusive range"
					+ " {0, "+(size - 1)+"}. Index = "+index+", Size = "+size+".");
		
		int start = wrapIndex(front + index);
		E element = array[start];
		int rear = wrapIndex(front + size - 1);
		int capacity = array.length;
		int rightShift = index;
		int leftShift = (size - 1) - index;
		
		System.out.println("\nLocal Index:	"+index);
		System.out.println("Front Index:	"+front);
		System.out.println("Rear Index: 	"+rear);
		System.out.println("Actual Index:	"+start);
		System.out.println("Forward Shift:	"+rightShift);
		System.out.println("Backward Shift:	"+leftShift);
		
		
		// shifting algorithms work but is somewhat hacky
		
		///*
		if (rightShift < leftShift) { // shift elements forward
			if (front <= start) {
				System.arraycopy(array, front, array, front + 1, rightShift);
			}
			else {
				//System.out.println("Tee");
				System.arraycopy(array, 0, array, 1, start);
				array[0] = array[capacity - 1];
				System.arraycopy(array, front, array, front + 1, rightShift - (start + 1));
			}
			array[front] = null;
			front = wrapIndex(++front);
			//System.out.println(front);
		}
		//*/	
		
		///*
		else { // shift elements backward
			if (rear >= start) {
				System.arraycopy(array, start + 1, array, start, leftShift);
			}
			else {
				//System.out.println("uwu");
				System.arraycopy(array, start + 1, array, start, capacity - (start + 1)); // why cap - (s+1)?
				array[capacity - 1] = array[0];
				System.arraycopy(array, 1, array, 0, rear);
				//printArray();
			}
			array[rear] = null;
		}
		//*/
		
		size--;
		return element;
	}

	public boolean removeFirstOccurence(E element) {
		if (element != null) {
			for (int c = 0, i = front; c < size; c++) {;
				if (element.equals(array[i])) {
					remove(i);
					return true;
				}
				i = wrapIndex(++i);
			}
		}
		return false;
	}
	
	public boolean removeLastOccurence(E element) {
		if (element != null) {
			for (int c = 0, i = wrapIndex(front + size - 1); c < size; c++) {;
				if (element.equals(array[i])) {
					remove(i);
					return true;
				}
				i = wrapIndex(--i);
			}
		}
		return false;
	}
	
	
	public E unorderedRemove(int index) {
		int a = wrapIndex(front + index);
		int rear = wrapIndex(front + size - 1);
		
		E temp = array[a];
		array[a] = array[rear];
		array[rear] = null;
		
		return temp;
	}
	
	
	
	
	
	
	
	
	
	public void swap(int a, int b) {
		//check a and b for index bounds
		if (size <= 0) throw new IllegalStateException("Array is empty.");
		if (a >= size || a < 0) 
			throw new IndexOutOfBoundsException("Index a must be within inclusive range"
					+ " {0, "+(size-1)+"}. Index a = "+a+", Size = "+size+".");
		if (b >= size || b < 0) 
			throw new IndexOutOfBoundsException("Index b must be within inclusive range"
					+ " {0, "+(size-1)+"}. Index b = "+b+", Size = "+size+".");
		
		a = wrapIndex(front + a);
		b = wrapIndex(front + b);
		
		E temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	public void sort() {
		if (front > wrapIndex(front + size - 1)) resize(size);
		Arrays.sort(array, 0, wrapIndex(front + size - 1));
	}
	
	public void sort(Comparator<? super E> c) {
		if (front > wrapIndex(front + size - 1)) resize(size);
		Arrays.sort(array, front, wrapIndex(front + size - 1), c);
	}
	
	public void reverse() {
		if (front > wrapIndex(front + size - 1)) resize(size);
		int i = front, j = wrapIndex(front + size - 1);
		while (i < j) {
			E temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}
	
	protected int wrapIndex(int i) {
		int capacity = array.length;
		if (i >= 0 && i < capacity) { // within bounds
			return i;
		}
		else if (i >= capacity) { // exceeds capacity range
			return i - capacity;
		}
		return capacity + i; // i < 0, negative
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void ensureCapacity(int additionalCapacity) {
		if (additionalCapacity <= 0)
			throw new IllegalArgumentException("addionalCapacity must be greater than 0: "+additionalCapacity);
		if (size + additionalCapacity > array.length)
			resize(size + additionalCapacity);
	}
	public void shrinkToCapacity(int maxCapacity) {
		if (maxCapacity < array.length && maxCapacity >= size) resize(maxCapacity);
	}
	
	public void shrink() {
		if (size < array.length) resize(size);
	}
	
	public void clear() {
		if (size == 0) return;
		size = 0;
		front = 0;
		Arrays.fill(array, null);
	}
	
	protected void growCapacity() {
		if (size >= array.length) {
			int capacity = array.length;
			int newSize = capacity <= 64 ? capacity * 2 : capacity + capacity << 1;
			resize(newSize);
		}
	}
	
	/*
	protected void shrinkCapacity() {
		if (size <= array.length / 5 && size > minCapacity) {
			resize(size);
		}
	}
	*/
	
	@SuppressWarnings("unchecked")
	protected void resize(int newSize) {
		E[] newArray = (E[]) new Object[newSize];
		int minSize = Math.min(newSize, array.length); // (to avoid out of bounds errors)
		int rear = wrapIndex(front + size - 1);
		if (front < rear) {
			System.arraycopy(array, front, newArray, 0, minSize);
		}
		else {
			System.out.println("Oww");
			int extra = array.length - front;
			System.arraycopy(array, front, newArray, 0, extra);
			System.arraycopy(array, 0, newArray, extra, rear + 1);
		}
		array = newArray;
		front = 0;
	}
	
	
	
	

	
	
	
	
	
	
	

	public boolean isEmpty() {
		return size <= 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void printArray() {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if (i < array.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.print("]\n");
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		E[] newArray = (E[]) new Object[size];
		int rear = wrapIndex(front + size - 1);
		if (front < rear) {
			System.arraycopy(array, front, newArray, 0, size);
		}
		else {
			int extra = array.length - front;
			System.arraycopy(array, front, newArray, 0, extra);
			System.arraycopy(array, 0, newArray, extra, rear + 1);
		}
		return newArray;
	}





	@Override
	public Iterator<E> iterator() {
		return getArrayIterator();
	}
	
	
	public ArrayIterator getArrayIterator() {
		if (arrayIterator == null) {
			arrayIterator = new ArrayIterator();
		}
		arrayIterator.reset();
		return arrayIterator;
	}
	
	
	
	
	
	protected class ArrayIterator implements BiIterator<E> {
		int cursor; // index order where front == 0
		int lastReturned = -1;
		
		@Override
		public boolean hasPrevious() {
			return cursor > 0;
		}
		
		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException("No next elements.");
			lastReturned = cursor++; //cursor is incremented after being assigned to lastRet as to not skip the current element
			return array[wrapIndex(lastReturned + front)];
		}

		@Override
		public E previous() {
			if (!hasPrevious()) throw new NoSuchElementException("No previous elements.");
			lastReturned = --cursor; //cursor is decremented before being assigned to lastRet
			return array[wrapIndex(lastReturned + front)];
		}

		@Override
		public void remove() {
			if (lastReturned == -1) throw new IllegalStateException("Invalid remove operation. Call next() or previous() before using this method."); 
			DynamicArray.this.remove(lastReturned);
			cursor = lastReturned; //cursor returns back to lastRet due to (decreased size by removal from next()) and (prevent out of bounds from previous())
			lastReturned = -1;
		}
		
		public void reset() {
			cursor = 0;
			lastReturned = -1;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Object clone() {
		DynamicArray<E> arrayClone = new DynamicArray<E>();
		
		E[] cloner = this.toArray();
		arrayClone.array = cloner;
		arrayClone.size = this.size;
		arrayClone.front = 0;
		
		return arrayClone;
	}
	
	
	
	
	
	
	
	
	// To Add:
	
		//filter
		//slice
		//fill
		//spliterator
		//hashcode
		//shuffle
		//get random
		//minmax
		//copy
		//map?
		//?removeOccurences
		//?removeRange
		//!addAll, removeAll, getAll?
		//subList
		//!swapAndPopBack()
		//!swapandPopFront()
		//! include read-only version of the  data structure
		
		//what is Predicate???
		
		
		//partial heap sort (for min max) //separate for heapArray [for priority queues and other heap data structures]

		
		
		
		
		/*
		public void removeAllOccurences(E element) { // return boolean or occurrence count?
			if (element != null) {
				//
			}
		}
		*/
}
