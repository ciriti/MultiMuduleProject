The `LruCache` and `LruHashMap` classes in the `androidx.collection` package serve different
purposes and have distinct characteristics:

### `LruCache`

- **Purpose**: A cache that holds strong references to a limited number of values, with entries
  evicted in least-recently-used order.
- **Thread-Safety**: It is thread-safe.
- **Features**:
    - Provides methods to customize the size of the cache (`sizeOf`).
    - Allows overriding `create` to compute values on cache misses.
    - Allows overriding `entryRemoved` to handle evictions and removals.
    - Provides statistics like hit count, miss count, and eviction count.
    - Can be resized dynamically.

### `LruHashMap`

- **Purpose**: A hash map with a predictable iteration order, which is the order in which keys were
  last accessed.
- **Thread-Safety**: It is not thread-safe by itself.
- **Features**:
    - Extends `LinkedHashMap` with access order.
    - Does not provide built-in methods for cache size customization or eviction handling.
    - Does not provide statistics or dynamic resizing.

In summary, `LruCache` is a higher-level, thread-safe cache implementation with additional features
for managing cache entries, while `LruHashMap` is a lower-level, non-thread-safe map with
access-order iteration.

Here are the methods in the `LruCache` class with a clear explanation for each, specifying which
methods throw an exception and what exception is thrown:

| Method                                                              | Explanation                                                                                                                  | Throws Exception           |
|---------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| `resize(maxSize: Int)`                                              | Sets the new maximum size of the cache.                                                                                      | `IllegalArgumentException` |
| `get(key: K): V?`                                                   | Returns the value for the given key if it exists in the cache or can be created by the `create` method.                      | No                         |
| `put(key: K, value: V): V?`                                         | Caches the value for the given key and returns the previous value mapped by the key.                                         | No                         |
| `trimToSize(maxSize: Int)`                                          | Removes the eldest entries until the total of remaining entries is at or below the requested size.                           | No                         |
| `remove(key: K): V?`                                                | Removes the entry for the given key if it exists and returns the previous value mapped by the key.                           | No                         |
| `entryRemoved(evicted: Boolean, key: K, oldValue: V, newValue: V?)` | Called for entries that have been evicted or removed.                                                                        | No                         |
| `create(key: K): V?`                                                | Called after a cache miss to compute a value for the corresponding key.                                                      | No                         |
| `safeSizeOf(key: K, value: V): Int`                                 | Returns the size of the entry for the given key and value in user-defined units.                                             | `IllegalArgumentException` |
| `sizeOf(key: K, value: V): Int`                                     | Returns the size of the entry for the given key and value in user-defined units.                                             | No                         |
| `evictAll()`                                                        | Clears the cache, calling `entryRemoved` on each removed entry.                                                              | No                         |
| `size(): Int`                                                       | Returns the size of the cache in units.                                                                                      | No                         |
| `maxSize(): Int`                                                    | Returns the maximum size of the cache.                                                                                       | No                         |
| `hitCount(): Int`                                                   | Returns the number of times `get` returned a value that was already present in the cache.                                    | No                         |
| `missCount(): Int`                                                  | Returns the number of times `get` returned null or required a new value to be created.                                       | No                         |
| `createCount(): Int`                                                | Returns the number of times `create` returned a value.                                                                       | No                         |
| `putCount(): Int`                                                   | Returns the number of times `put` was called.                                                                                | No                         |
| `evictionCount(): Int`                                              | Returns the number of values that have been evicted.                                                                         | No                         |
| `snapshot(): MutableMap<K, V>`                                      | Returns a mutable copy of the current contents of the cache, ordered from least recently accessed to most recently accessed. | No                         |
| `toString(): String`                                                | Returns a string representation of the cache.                                                                                | No                         |
