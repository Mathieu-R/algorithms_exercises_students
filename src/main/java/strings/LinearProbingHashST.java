package strings;

public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    // Please do not add/remove variables/modify their visibility.
    protected int n;           // number of key-value pairs in the symbol table
    protected int m;           // size of linear probing table
    protected Key[] keys;      // the keys
    protected Value[] vals;    // the values


    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }

    public int size() {
        return n;
    }

    public int capacity() {
        return m;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * resizes the hash table to the given capacity by re-hashing all the keys
     */
    private void resize(int capacity) {
		// create a temp table with the new desired capacity
		LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(capacity);

		// for each entry in this table
		for (int i = 0; i < m; i++) {
			// if entry contains a key
			if (keys[i] != null) {
				// get its key-value pair
				Key key = keys[i];
				Value val = vals[i];

				// put this key-value pair in the new table
				temp.put(key, val);
			}
		}

		// update table with new capacity keys and values
		keys = temp.keys;
	    vals = temp.vals;
		m = temp.m;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * The load factor should never exceed 50% so make sure to resize correctly
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		// if number of keys exceed half of the table size
		// double table capacity
		if (n >= m/2) {
			resize(2*m);
		}

		// hash the key and find the next available entry
		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
			// if key already exists, update the value and return (we do not want to update the counter)
			if (keys[i].equals(key)) {
				vals[i] = val;
				return;
			}
		}

		// found an empty entry
		keys[i] = key;
		vals[i] = val;

		// update counter of keys in table
		n++;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key
     * @return the value associated with {@code key};
     * {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

        // hash the key to find the corresponding index in range [0,m-1]
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
			// check if the key is the one we're looking for
			// otherwise, it's a collision, go to the next entry
			if (keys[i].equals(key)) {
				return vals[i];
			}
		}

		// key not found
         return null;
    }

    public void delete(Key key) {
        // base case: key is not in the table
        if (!contains(key)) {
            return;
        }

        // hash the key to delete
        int i = hash(key);

        // look for the right entry in the table performing a key equality because there could be collision
        while(!key.equals(key)) {
            i = (i + 1) % m;
        }

        // remove it from the table
        keys[i] = null;
        vals[i] = null;

        // re-hash all the subsequent values
        // so they're shifted to the left
        // and we do not let an empty entry between 2 clusters

        for (i = (i + 1) % m; keys[i] != null; i = (i + 1) % m) {
            Key keyToShift = keys[i];
            Value valToShift = vals[i];

            // put method will perform the re-hashing
            // and place the key-value pair at the correct position
            put(keyToShift, valToShift);

            // clean the old entry
            keys[i] = null;
            vals[i] = null;

            // decrease counter because put increase it
            n--;
        }

        n--;

        // if number of keys in the table become less than 1/8 of the table size
        // divide table size by 2
        if (n > 0 && n <= m/8) {
            resize(m / 2);
        }
    }

    /**
     * Returns all keys in this symbol table as an array
     */
    public Object[] keys() {
        Object[] exportKeys = new Object[n];
        int j = 0;
        for (int i = 0; i < m; i++)
            if (keys[i] != null) exportKeys[j++] = keys[i];
        return exportKeys;
    }
}
