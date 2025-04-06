package data_structures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

// A hash table implementation that utilizes robin hood hashing for storing key-value entries, improving the average time of insertion, deletion, and retrieval. 
// Stores key-value entries in separated arrays instead of node objects to minimize memory footprint. Key-value pairs are inserted in insertion order, and maintains that order despite frequent insertions and removal of entries.
// Use of this hash table implementation is limited to single-threaded applications.

public class FlatMap<K,V> implements Iterable<FlatMap.MapEntry<K, V>> { // extends AbstractMap<K,V> implements Map<K,V>{
	
	protected static final int FREE = -1;
	protected static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	protected static final int DEFAULT_CAPACITY = 1 << 2;
	protected static final int MIN_CAPACITY = 1 << 1;
	protected static final int MAX_CAPACITY = 1 << 30;
	
	//SIZES OF ALL ARRAYS MUST BE THE SAME AND ARE ALWAYS POWERS OF 2.
	//Uses RobinHood hashing
	//Contains a list of key-value entries with their probe sequence lengths, in insertion order.
	K[] keyEntries;
	V[] valEntries;
	int[] probeLengths;
	// Table that stores the actual indices of keys, instead of keys themselves 
	// for faster and space-efficient resizing.
	// (This implementation detail is based on Python's implementation of compact dictionaries)
	int[] hashTable;
	
	Entries entryIterator;
	Keys keyIterator;
	Values valueIterator;
	
	
	// Load factor of hash table.
	protected float loadFactor;
	
	// Threshold of hash table size before re-adjusting/resizing hash table.
	protected int threshold;
	
	// Actual table capacity.
	protected int capacity;
	
	// Used for fast modulo operations (only works on table sizes that are powers of 2)
	protected int mask; // mask = capacity - 1;
	
	// number of keys present in the table
	protected int size;
	
	// capacity limit for dynamic shrinking, it is set upon creation
	protected int minCapacity;
	
	// number of filled indices, always increasing with each insertion
	// this parameter is always greater than or equal to the number of keys (size) in the table.
	// only shrinks when insertion is called and filled == table.length, or shrink() method is called.
	protected int filled;
	
	
	@SuppressWarnings("unchecked")
	public FlatMap() {
		loadFactor = DEFAULT_LOAD_FACTOR;
		capacity = DEFAULT_CAPACITY;
		threshold = (int) (capacity * loadFactor);
		mask = capacity - 1;
		minCapacity = capacity;
		size = 0;
		filled = 0;
		
		keyEntries = (K[]) new Object[capacity];
		valEntries = (V[]) new Object[capacity];
		probeLengths = new int[capacity];
		hashTable = new int[capacity];
		
		Arrays.fill(hashTable, FREE);
	}
	
	

	public V put(K key, V value) {
		return putVal(key, value, true);
	}
	
	public V putIfAbsent(K key, V value) {
		return putVal(key, value, false);
	}
	
	protected V putVal(K key, V value, boolean replaceOld) {
		if (key == null) throw new NullPointerException("Null keys are not allowed.");
		if (value == null) throw new NullPointerException("Null values are not allowed.");
		
		if (size == MAX_CAPACITY)
			throw new OutOfMemoryError("Table reached max capacity and cannot accept more entries.");
		
		int hash = hash(key);
		int tableIndex = hash & mask;
		
		int storedEntry = hashTable[tableIndex];
		//int storedProbeLength = storedEntry2 >= 0 ? probeLengths[storedEntry2] : 0;
		// new key, new value
		int probedEntry = filled;
		int probedProbeLength = 0;
		
		while (storedEntry >= 0) {
			int storedProbeLength = probeLengths[storedEntry];
			
			if (key.equals(keyEntries[storedEntry])) {
				
				if (replaceOld) {
					V oldVal = valEntries[storedEntry];
					valEntries[storedEntry] = value;
					return oldVal;
				}
				
				//System.out.println("Key already exists -> Key: "+key+",	Value: "+valEntries[storedEntry]
				//		+",	Entry Order: "+storedEntry+", Slot: "+tableIndex+", Probe Length: "+storedProbeLength+".");
				
				return valEntries[storedEntry];
			}
			
			if (probedProbeLength > storedProbeLength) {
				//System.out.println("Swap Collision");
				//System.out.println(keyEntries[storedEntry]);
				
				hashTable[tableIndex] = probedEntry;
				probeLengths[probedEntry] = probedProbeLength;  /////????
				
				probedEntry = storedEntry;
				probedProbeLength = storedProbeLength;  /////????
			}
			
			System.out.println("Collision");
			
			tableIndex = (tableIndex + 1) & mask;
			storedEntry = hashTable[tableIndex];
			probedProbeLength++;
		}
		
		keyEntries[filled] = key;
		valEntries[filled] = value;
		
		probeLengths[probedEntry] = probedProbeLength; /////????
		hashTable[tableIndex] = probedEntry;
		
		
		if (replaceOld) {
			//System.out.println("PPUUTT | Key: "+key+",	Value: "+value
			//		+",	Entry Order: "+filled+", Slot: "+tableIndex+", Probe Length: "+probeLengths[filled]+".");
		}
		else {
			//System.out.println("INSERT | Key: "+key+",	Value: "+value
			//		+",	Entry Order: "+filled+", Slot: "+tableIndex+", Probe Length: "+probeLengths[filled]+".");
		}
		
		size++;
		filled++;
		if (filled >= threshold && capacity < MAX_CAPACITY) adjustTable();
		
		return null;
	}
	
	
	public V replace(K key, V value) {
		if (key == null) throw new NullPointerException("Null keys does not exist.");
		if (value == null) throw new NullPointerException("Null values are not allowed.");
		
		int index = findKeyIndex(key);
		
		//if (index < 0) return null;
		
		/*
		if (index < 0) {
			System.out.println("=Nothing to replace, key "+key+" doesn't exist.=");
			return null;
		}
		
		int storedEntry2 = hashTable[index];
		
		if (index >= 0) {
			System.out.println("REPLACE | Key: "+key+",	Old Value: "+valEntries[storedEntry2]+", New Value: "+value
					+",	Entry Order: "+storedEntry2+", Slot: "+index+", Probe Length: "+probeLengths[storedEntry2]+".");
		}
		*/
		
		int storedEntry = hashTable[index];
		V oldVal = valEntries[storedEntry];
		valEntries[storedEntry] = value;
		
		return oldVal;
	}
	
	
	
	
	public V get(K key) {
		if (key == null) throw new NullPointerException("Null keys does not exist.");
		int index = findKeyIndex(key);
		
		/*
		if (index < 0) {
			System.out.println("=Key "+key+" doesn't exist.=");
			return null;
		}
		
		int storedEntry2 = hashTable[index];
		
		if (index >= 0) {
			System.out.println("LOOKUP | Key: "+key+",	Value: "+valEntries[storedEntry2]
					+",	Entry Order: "+storedEntry2+", Slot: "+index+", Probe Length: "+probeLengths[storedEntry2]+".");
		}
		*/
		
		return index >= 0 ? valEntries[ hashTable[index] ] : null;
	}
	
	

	
	//@Override
	public V remove(K key) {
		if (key == null) throw new NullPointerException("Null keys does not exist.");
		int index = findKeyIndex(key);
		
		//if (index < 0) return null;
		
		/*
		if (index < 0) {
			System.out.println("=Nothing to remove, key "+key+" doesn't exist.=");
			return null;
		}
		
		int storedEntry2 = hashTable[index];
		
		
		if (index >= 0) {
			System.out.println("REMOVE | Key: "+key+",	Value: "+valEntries[storedEntry2]
					+",	Entry Order: "+storedEntry2+", Slot: "+index+", Probe Length: "+probeLengths[storedEntry2]+".");
		}
		*/
		
		int storedEntry = hashTable[index];
		V oldVal = valEntries[storedEntry];
		valEntries[storedEntry] = null;
		keyEntries[storedEntry] = null;
		probeLengths[storedEntry] = 0;
		hashTable[index] = FREE;
		
		size--;
		shiftBackward(index);
		
		if (size <= threshold / 4) adjustTable();
		
		return oldVal;
	}

	

	
	// 
	protected void shiftBackward(int tableIndex) {
		int nextIndex = (tableIndex + 1) & mask;
		int currIndex = tableIndex;
		int nextEntry = hashTable[nextIndex];

        while (nextEntry >= 0 && probeLengths[nextEntry] > 0) {
        	hashTable[currIndex] = nextEntry;
        	
        	probeLengths[nextEntry] -= 1;
        	hashTable[nextIndex] = FREE;
        	
            nextIndex = (nextIndex + 1) & mask;
            nextEntry = hashTable[nextIndex];
            currIndex = (nextIndex - 1) & mask;
        }
    }
	
	
	
	protected int findKeyIndex(K key) {
		int hash = hash(key);
		int tableIndex = hash & mask;
		int storedEntry = hashTable[tableIndex];
		int probeLength = 0;
		
		while (storedEntry >= 0) {
			// Check if key in entryIndex is equal to the probed key
			if (key.equals(keyEntries[storedEntry]))
				return tableIndex;
			
			// Because of how Robin Hood hashing works in insertions,
			// the probed key must've displaced the current slotted key.
			// If probed key's probe length is greater than that of slotted key's probe length,
			// then the probed key doesn't exist.
			if (probeLength > probeLengths[storedEntry])
				return -1;
			
			tableIndex = (tableIndex + 1) & mask; // Linear-probing tableIndex
			storedEntry = hashTable[tableIndex]; // Entry index from linearly-probed table index
			
			probeLength++;
			
			//System.out.println("Searching...");
		}
		return -1;
    }
	
	// XOR-based hashing to consider hashes that only differ in upper bits and to minimize clustering
	protected int hash(K key) {
		int h = key.hashCode();
		return h ^ (h >>> 16);
	}
	
	
	
	
	
	
	
	// All resizing methods
	// include ensureCapacity(addCap), shrink(maxCap < capacity), clear(maxCap >= capacity), rehashing?
	// compare and contrast keys as hash table slots vs indices to insertion-ordered entries of key-val pairs.
		// in terms of performance and memory footprint
		// keys as hash slots 
			// -> separation of entry order and hash slots (reordering entries doesn't trigger rehashing)
			// -> higher memory footprint
			// -> deletion of entries is slower (linear time complexity)
		// entry indices as hash slots
			// -> lightweight hash tables (smaller memory footprint and cheaper resizing)
			// -> faster insertion and deletion (
			// -> expensive rehashing (after reordering entries or resizing, rehashing is necessary due to their index change)
	
	// used to increase capacity
	public void ensureCapacity(int addCapacity) {
		if (addCapacity <= 0) throw new IllegalArgumentException("addCapacity must be greater than 0: "+addCapacity);
		int newSize = getTableSize(size + addCapacity, loadFactor);
		if (capacity < newSize && capacity < MAX_CAPACITY) {
			//System.out.println("---Increasing---");
			resize(newSize);
		}
	}

	// used for resizing very sparse tables, shrinks into the current size's next power of 2
	public void shrink() {
		int newCapacity = getTableSize(size, loadFactor); // must be smaller than capacity
		int newThreshold = (int) (newCapacity * loadFactor); // smaller than threshold
		// only shrink if newCapacity doesn't violate the loadFactor
		if (newCapacity < capacity && size < newThreshold) {
			//System.out.println("---Shrinking---");
			resize(newCapacity);
		}
		//else
			//System.out.println("---No Shrinks---");
	}
	
	// used for resizing very sparse tables with control to shrinking capacity
	public void shrinkToCapacity(int maxCapacity) {
		int newCapacity = getTableSize(maxCapacity, loadFactor); // must be smaller than capacity
		int newThreshold = (int) (newCapacity * loadFactor); // smaller than threshold
		// only shrink if newCap is bigger than size & doesn't violate the loadFactor 
		if (newCapacity < capacity && size < newThreshold && newCapacity > size) {
			//System.out.println("---Truncating---");
			resize(newCapacity);
		}
		//else
			//System.out.println("---No Cuts---");
	}
	
	//
	public void clear() {
		if (size == 0) return;
		size = 0;
		filled = 0;
		
		for (int i = 0; i < capacity; i++) {
			keyEntries[i] = null;
			valEntries[i] = null;
			probeLengths[i] = 0;
			hashTable[i] = FREE;
		}
	}
	
	protected void adjustTable() {
		if (filled >= threshold && filled > size) {
			//System.out.println("---Rehashing---");
			resize(capacity);
		}
		else if (size >= threshold) {
			//System.out.println("---Doubling---");
			resize(getTableSize(capacity, loadFactor));
		}
		else if (size <= (threshold / 4) && (capacity / 2) > minCapacity) {
			//System.out.println(minCapacity);
			//System.out.println("---Halfing---");
			resize(capacity / 2);
		}
		
	}
	
	protected int getTableSize(int newCapacity, float loadFactor) {
		
		float realCap = (newCapacity / loadFactor);
		int intCap = (int) realCap;
		int tableSize = nextPowerOfTwo( intCap < realCap ? intCap + 1 : intCap );
		if (tableSize < MIN_CAPACITY)
			tableSize = MIN_CAPACITY;
		if (tableSize > MAX_CAPACITY)
			tableSize = MAX_CAPACITY;
		
		return tableSize;
	}
	
	protected int nextPowerOfTwo(int value) {
		value--;
		value |= value >> 1;
		value |= value >> 2;
		value |= value >> 4;
		value |= value >> 8;
		value |= value >> 16;
		return value + 1;
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected void resize(int newCapacity) {
		K[] oldKeyEntries = keyEntries;
		V[] oldValEntries = valEntries;
		
		capacity = newCapacity;
		threshold = (int) (capacity * loadFactor);
		mask = capacity - 1;
		filled = size;
		
		keyEntries = (K[]) new Object[capacity];
		valEntries = (V[]) new Object[capacity];
		probeLengths = new int[capacity];
		hashTable = new int[capacity];
		
		Arrays.fill(hashTable, FREE);
		
		int c = 0;
		for (int i = 0; i < oldKeyEntries.length; i++) {
			K key = oldKeyEntries[i];
			if (key == null) continue;
			reinsert(key, oldValEntries[i], c++);
		}
	}
	
	protected void reinsert(K key, V value, int insertOrder) {
		
		int hash = hash(key);
		int tableIndex = hash & mask;
		
		int storedEntry = hashTable[tableIndex];
		int probedEntry = insertOrder;
		int probedProbeLength = 0;
		
		while (storedEntry >= 0) {
			int storedProbeLength = probeLengths[storedEntry];
			
			if (probedProbeLength > storedProbeLength) {
				//System.out.println("Swap Collision");
				//System.out.println(keyEntries[storedEntry]);
				
				hashTable[tableIndex] = probedEntry;
				probeLengths[probedEntry] = probedProbeLength;
				
				probedEntry = storedEntry;
				probedProbeLength = storedProbeLength;
			}
			
			//System.out.println("Collision");
			
			tableIndex = (tableIndex + 1) & mask;
			storedEntry = hashTable[tableIndex];
			probedProbeLength++;
		}
		
		keyEntries[insertOrder] = key;
		valEntries[insertOrder] = value;
		
		probeLengths[probedEntry] = probedProbeLength; /////????
		hashTable[tableIndex] = probedEntry;
		
		
		//System.out.println("REINSERT | Key: "+key+",	Value: "+value
		//		+",	Entry Order: "+insertOrder+", Slot: "+tableIndex+", Probe Length: "+probeLengths[insertOrder]+".");
	}

	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	//@Override
	public int size() {
		return size;
	}
	
	public int capacity() {
		return capacity;
	}

	public float currentLoad() {
		return size * 1f / capacity;
	}
	
	public float loadFactor() {
		return loadFactor;
	}
	
	//@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size > 0;
	}

	//@Override
	public boolean containsKey(K key) {
		if (key == null) throw new NullPointerException("Null keys does not exist.");
		
		return findKeyIndex(key) >= 0;
	}

	//@Override
	public boolean containsValue(V value) {
		if (value == null) return false;
		for (int i = 0; i < capacity; i++) {
			if (value.equals(valEntries[i])) {
				return true;
			}
		}
		return false;
	}


	


	
	/*
	 
	//@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}
	
	//@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	

	@Override
	public Iterator<MapEntry<K,V>> iterator() {
		// TODO Auto-generated method stub
		if (entryIterator == null) {
			entryIterator = new Entries();
		}
		entryIterator.reset();
		return entryIterator;
	}
	
	
	
	
	public static class MapEntry<K,V> {
		K key;
		V value;
		
		MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	abstract class MapIterator { //ensures inclusivity of current index if next() is called
		int nextIndex, prevIndex;
		int cursor;
		int lastReturned = -1;
		
		protected MapIterator() {
			cursor = 0;
			lastReturned = -1;
			nextIndex = -1;
			prevIndex = -1;
			findNextIndex();
		}
		
		public boolean hasNext() {
			return nextIndex >= 0 && nextIndex < keyEntries.length;
		}
		
		public boolean hasPrevious() {
			return prevIndex >= 0 && prevIndex < keyEntries.length;
		}
		
		void findNextIndex() {
			for (int i = cursor; i < keyEntries.length; i++) { //cursor is not incremented upon search for inclusion of current entry
				if (keyEntries[i] != null) {
					nextIndex = i;
					return;
				}
			}
			nextIndex = -1;
		}
		
		void findPrevIndex() {
			for (int i = cursor - 1; i >= 0; i--) {
				if (keyEntries[i] != null) {
					prevIndex = i;
					return;
				}
			}
			prevIndex = -1;
		}
		
		void setToNext() {
			if (!hasNext()) throw new NoSuchElementException("No next elements.");
			prevIndex = cursor;
			lastReturned = cursor;
			cursor = nextIndex; //cursor is set to nextIndex after being assigned to lastRet as to not skip the current entry
			findNextIndex(); //finds the next index to check if there is a valid entry next to the current one.
		}

		void setToPrevious() {
			if (!hasPrevious()) throw new NoSuchElementException("No previous elements.");
			nextIndex = cursor;
			cursor = prevIndex; //cursor is set to prevIndex before being assigned to lastRet
			lastReturned = cursor;
			findPrevIndex(); //finds the previous index to check if there is a valid entry previous to the current one.
		}
		
		public void remove() {
			if (lastReturned == -1) throw new IllegalStateException("Invalid remove operation. Call next() or previous() before using this method."); 
			FlatMap.this.remove(keyEntries[lastReturned]);
			boolean isNext = cursor != lastReturned; // checks whether the previous method called is either next() or previous().
			cursor = lastReturned; //cursor returns back to lastRet due to (decreased size by removal from next()) and (prevent out of bounds from previous())
			lastReturned = -1;
			//assigns either a new prevIndex or nextIndex after removal operation, based on whether the called method prior is either next() or previous().
			if (isNext) {
				findNextIndex();
			}
			else {
				findPrevIndex();
			}
		}
		
		void reset() {
			cursor = 0;
			lastReturned = -1;
			nextIndex = -1;
			prevIndex = -1;
			findNextIndex();
		}
		
	}

	
	public class Entries extends MapIterator implements BiIterator<MapEntry<K,V>>, Iterable<MapEntry<K,V>> {
		MapEntry<K,V> entry;
		
		public Entries() {
			super();
			entry = new MapEntry<K,V>(null, null);
		}
		
		/** Not that the same MapEntry instance is called every time this method is called.
		 */
		@Override
		public MapEntry<K, V> next() {
			setToNext();
			entry.key = keyEntries[lastReturned];
			entry.value = valEntries[lastReturned];
			return entry;
		}

		/** Not that the same MapEntry instance is called every time this method is called.
		 */
		@Override
		public MapEntry<K, V> previous() {
			setToPrevious();
			entry.key = keyEntries[lastReturned];
			entry.value = valEntries[lastReturned];
			return entry;
		}
		
		@Override
		void reset() {
			entry.key = null;
			entry.value = null;
			super.reset();
		}

		@Override
		public Iterator<MapEntry<K, V>> iterator() {
			return this;
		}
		
		@Override
		public void forEach(Consumer<? super MapEntry<K, V>> action) {
			
		}
	}
	
	public class Keys extends MapIterator implements BiIterator<K>, Iterable<K> {

		public Keys() {
			super();
		}
		
		@Override
		public K next() {
			setToNext();
			return keyEntries[lastReturned];
		}

		@Override
		public K previous() {
			setToPrevious();
			return keyEntries[lastReturned];
		}
		
		public boolean contains(K key) {
			return FlatMap.this.containsKey(key);
		}
		
		@SuppressWarnings("unchecked")
		public K[] toArray() {
			K[] keys = size > 0 ? (K[]) new Object[size] : null;
			for (int i = 0, s = 0; i < keyEntries.length || s < size; i++) {
				if (keyEntries[i] != null) {
					keys[s++] = keyEntries[i];
				}
			}
			return keys;
		}

		@Override
		public Iterator<K> iterator() {
			return this;
		}
		
		@Override
		public void forEach(Consumer<? super K> action) {
			
		}
	}
	
	public class Values extends MapIterator implements BiIterator<V>, Iterable<V> {

		public Values() {
			super();
		}
		
		@Override
		public V next() {
			setToNext();
			return valEntries[lastReturned];
		}

		@Override
		public V previous() {
			setToPrevious();
			return valEntries[lastReturned];
		}
		
		public boolean contains(V value) {
			return FlatMap.this.containsValue(value);
		}
		
		@SuppressWarnings("unchecked")
		public V[] toArray() {
			V[] vals = size > 0 ? (V[]) new Object[size] : null;
			for (int i = 0, s = 0; i < valEntries.length || s < size; i++) {
				if (valEntries[i] != null) {
					vals[s++] = valEntries[i];
				}
			}
			return vals;
		}

		@Override
		public Iterator<V> iterator() {
			return this;
		}
		
		@Override
		public void forEach(Consumer<? super V> action) {
			
		}
	}



	
	
	
	// add methods
	//values
	//keyset
	//entryset
	
	//valueIterator
	//keyIterator
	//swap(index a, index b)?
	//iterator
	//addAll, removeAll, getAll?
	
}

