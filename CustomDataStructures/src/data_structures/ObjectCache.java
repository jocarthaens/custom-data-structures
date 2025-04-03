package data_structures;

import java.util.Stack;

public abstract class ObjectCache<T> {
	private final Stack<T> cache;
	private int maxObjectCount;
	private final int initialCount;
	
	public ObjectCache(int initialCount, int cacheLimit){
		cache = new Stack<>();
		this.initialCount = initialCount;
		this.maxObjectCount = cacheLimit;
		_initialize();
	}
	
	public final int initialObjectCount() {
		return initialCount;
	}
	
	public final int ObjectCacheLimit() {
		return maxObjectCount;
	}
	
	public final void setCacheLimit(int limit) {
		maxObjectCount = limit;
		if (cache.size() > limit) {
			while (cache.size() > limit) {
				cache.pop();
			}
		}
	}
	
	public final int currentCache() {
		return cache.size();
	}
	
	public final T request() {
		T data = null;
		if (!cache.isEmpty()) {
			data = cache.pop();
			activate(data);}
		else {
			data = create();}
		return data;
	}
	
	public final void store(T data) {
		reset(data);
		if (cache.size() < maxObjectCount) {
			cache.push(data);
		}
	}
	
	public final void resetCache() {
		cache.clear();
		_initialize();
	}
	
	public final void clearAllCache() {
		cache.clear();
	}
	
	private final void _initialize() {
		for (int i = 0; i < this.initialCount; i++) {
			cache.push(create());
		}
	}
	
	protected abstract void reset(T data);
	protected abstract void activate(T data);
	protected abstract T create();
}
