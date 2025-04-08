package data_structures;

import java.util.Comparator;

public interface ListArray<E> extends ArrayInterface<E> {
	
	

	public void add(E element);
	
	public void insert(E element, int index);
	
	public E remove(int index);
	
	public E unorderedRemove(int index);
	
	public E get(int index);
	
	
	
	
	public void pushFront(E element);
	
	public void pushBack(E element);
	
	public E peekFront();
	
	public E peekBack();
	
	public E popFront();
	
	public E popBack();
	
	
	
	
	
	public void swap(int a, int b);
	
	public void sort();
	
	public void reverse();
	
	public void sort(Comparator<? super E> c);
	
}
