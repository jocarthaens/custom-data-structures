An attempt at creating a custom implementation of data structures such as dynamic arrays and hash tables.

## Features
+ Flat Hash Map that utilizes robin hood hashing for storing key-value, and uses arrays instead of node pointer objects in storing key-value pairs.
+ Versatile Dynamic Array that can be used as either a list, double-ended queue, or stack (Use interfaces to lock the array into one type.).
+ Caching data structure that stores unused object of the same type for future reuse.
+ Object pool that generates and pools its own objects .
+ Write-protected tuple where its stored data can only be overriden if an inputted key object has matched the tuple's key object that was introduced to the  upon the tuple's creation.

## To Add
+ Flat Hash Set that shares similar features with FlatMaps, but only manages and stores unique objects.
+ Read-Only version of data structures to prevent unauthorized modification of its elements.
+ Priority Queue.
+ Tree-based data structures, hopefully if concept is fully understood. (AVL Trees, RB Trees, Balanced Trees) 