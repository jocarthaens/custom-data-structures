package data_structures;

import java.util.Iterator;

public interface BiIterator<T> extends Iterator<T> {
	
    boolean hasNext();

    T next();

    boolean hasPrevious();

    T previous();

    void remove();
}
