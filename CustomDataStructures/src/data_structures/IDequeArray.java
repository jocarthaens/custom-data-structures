package data_structures;

public interface IDequeArray<E> extends ArrayInterface<E> {

	
	public void pushFront(E element);
	
	public void pushBack(E element);
	
	
	public E peekFront();
	
	public E peekBack();
	
	
	public E popFront();
	
	public E popBack();
}
