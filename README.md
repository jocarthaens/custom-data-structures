A custom implementation of data structures such as dynamic arrays and hash tables. Features a flat hash map that utilizes robin hood hashing for storing key-value entries, improving the average time of insertion, deletion, and retrieval. Also features a dynamic array that can be used 



a versatile, insertion-ordered array that can serve as a list, stack or circular queue (and soon, priority queue)
	// aims to optimize speed of ordered insertion and post-removal shifting using circular queue implementation
	// where elements are moved either forward or backward, whichever has the least motion.
	// worst case complexity will be n/2 time, best case will be amortized constant time (on either head or tail)
	// due with a slight cost of lookup (conversion of index position to internal index position)
