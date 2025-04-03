package data_structures;

import java.util.Iterator;

import data_structures.DynamicArray.ArrayIterator;

// general interface for all array-related data structures

public interface ArrayInterface<E> extends Cloneable {
	
	
	
	public boolean contains(E element);
	
	public int instanceCount(E element);
	
	public int find(E element);
	
	public int firstIndexOf(E element);
	
	public int lastIndexOf(E element);
	
	public boolean removeFirstOccurence(E element);
	
	public boolean removeLastOccurence(E element);
	
	public void ensureCapacity(int additionalCapacity);
	
	public void shrinkToCapacity(int maxCapacity);
	
	public void shrink();
	
	public void clear();
	
	public boolean isEmpty();
	
	public E[] toArray();
	
	public Iterator<E> iterator();
	
	
	
	@SuppressWarnings("rawtypes")
	ArrayIterator getArrayIterator();
	
	
	public Object clone();
	
	// getViewerArray();
}
