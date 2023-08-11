import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * Your implementation of a LinearProbingHashMap.
 *
 * @author Minkun Lei
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 *
 * Resources: 1332 live coding
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     * The backing array should have an initial capacity of initialCapacity.
     * You may assume initialCapacity will always be positive.
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        int delete = -1;
        if (key == null || value == null) {
            throw new IllegalArgumentException("either the key or the value is null");
        }
        if ((size + 1.0) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int idx = Math.abs(key.hashCode() % table.length);
        int probe = 0;
        while (table[idx] != null && probe < size) {
            if (delete == -1 && table[idx].isRemoved()) {
                delete = idx;
            }
            if (table[idx].getKey().equals(key) && !table[idx].isRemoved()) {
                V dummy = table[idx].getValue();
                table[idx].setValue(value);
                return dummy;
            }
            if (!table[idx].isRemoved()) {
                probe++;
            }
            idx = (idx + 1) % table.length;
        }
        if (delete != -1) {
            table[delete] = new LinearProbingMapEntry<K, V>(key, value);
        } else {
            table[idx] = new LinearProbingMapEntry<K, V>(key, value);
        }
        size++;
        return null;
    }

    /**
     * Given the key of a specific map entry, this helper method finds and returns that entry.
     * However, the method returns null if the target entry is not found in the map.
     *
     * @param key the key of the entry that we want to find
     * @return the LinearProbingMapEntry of the corresponding key
     */
    private LinearProbingMapEntry<K, V> getEntry(K key) {
        int idx = Math.abs(key.hashCode() % table.length);
        int probe = 0;
        while (probe < size && table[idx] != null && !table[idx].getKey().equals(key)) {
            if (!table[idx].isRemoved()) {
                probe++;
            }
            idx = (idx + 1) % table.length;
        }
        if (table[idx] != null && !table[idx].isRemoved() && table[idx].getKey().equals(key)) {
            return table[idx];
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null, cannot remove it");
        }
        LinearProbingMapEntry<K, V> get = getEntry(key);
        if (get != null) {
            size--;
            get.setRemoved(true);
            return get.getValue();
        }
        throw new java.util.NoSuchElementException("the key is not in the hashmap");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null");
        }
        LinearProbingMapEntry<K, V> haha = this.getEntry(key);
        if (haha == null) {
            throw new java.util.NoSuchElementException("the key is not in the map");
        }
        return haha.getValue();
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key is null");
        }
        return this.getEntry(key) != null;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use java.util.HashSet.
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> haha = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                K key = table[i].getKey();
                haha.add(key);
            }
        }
        return haha;
    }

    /**
     * Returns a List view of the values contained in this map.
     * Use java.util.ArrayList or java.util.LinkedList.
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     * @return list of values in this map
     */
    public List<V> values() {
        LinkedList<V> haha = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                V value = table[i].getValue();
                haha.add(value);
            }
        }
        return haha;
    }

    /**
     * Resize the backing table to length.
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed. 
     * You should NOT copy over removed elements to the resized backing table.
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("the new length is less than the number of items in the hashmap");
        }
        LinearProbingMapEntry<K, V>[] oldArr = table;
        table = new LinearProbingMapEntry[length];
        for (int i = 0; i < oldArr.length; i++) {
            if (oldArr[i] != null && !oldArr[i].isRemoved()) {
                int idx = Math.abs(oldArr[i].getKey().hashCode() % table.length);
                while (table[idx] != null) {
                    idx = (idx + 1) % table.length;
                }
                table[idx] = new LinearProbingMapEntry<K, V>(oldArr[i].getKey(), oldArr[i].getValue());
            }
        }
    }

    /**
     * Clears the map.
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the table of the map.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
