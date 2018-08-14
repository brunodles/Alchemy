package com.brunodles.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple LRUCache, its implementation from LinkedHashMap.
 *
 * <p>The {@link LinkedHashMap} contains a protected method that is called to remove the eldest entry.
 * Overriding it we create our own LRU Cache.</p>
 *
 * @param <K>
 * @param <V>
 * @see <a href="https://www.javaspecialists.eu/archive/Issue246.html"></a>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxEntries;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRUCache(int initialCapacity,
            float loadFactor,
            int maxEntries) {
        super(initialCapacity, loadFactor, true);
        this.maxEntries = maxEntries;
    }

    public LRUCache(int initialCapacity,
            int maxEntries) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, maxEntries);
    }

    public LRUCache(int maxEntries) {
        this(DEFAULT_INITIAL_CAPACITY, maxEntries);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }
}
