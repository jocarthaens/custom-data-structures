package data_structures;

import java.util.Iterator;

// A BiIterator can iterate elements either forward or backward.
// This iterator is used in FlatMap and DynamicArray data structures.

public interface BiIterator<T> extends Iterator<T> {
	
    boolean hasNext();

    T next();

    boolean hasPrevious();

    T previous();

    void remove();
}
