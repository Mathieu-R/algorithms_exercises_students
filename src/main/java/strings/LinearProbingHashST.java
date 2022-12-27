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
		LinearProbingHashST<Key, Value> lp = new LinearProbingHashST<>(capacity);

		for (int i = 0; i < m; i++) {
			if (keys[i] != null) {
				Key key = keys[i];
				Value val = vals[i];

				lp.put(key, val);
			}
		}

		lp.keys = keys;
		lp.vals = vals;
		lp.m = m;
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

		if (n > m/2) {
			resize(2*m);
		}

		// hash the key and find the next available entry
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
			// if key already exists, update the value
			if (keys[i].equals(key)) {
				vals[i] = val;
				return;
			}

			// if key does not exist, put the new key and associated value
			keys[i] = key;
			vals[i] = val;

			// update counter of keys in table
			n++;
		}
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
			// if any collision, go to the next entry
			if (keys[i].equals(key)) {
				return vals[i];
			}
		}

		// key not found
         return null;
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
