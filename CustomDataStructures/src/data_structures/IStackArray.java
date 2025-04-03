package data_structures;

public interface IStackArray<E> extends ArrayInterface<E> {
	
	
	public void push(E element);
	
	public E peek();
	
	public E pop();
}
