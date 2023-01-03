package sorting;

import static java.lang.Math.log;

/**
 * This class represent a MinMaxHeap (generic over a Key type) implement as an array-based array.
 * Such data structure is useful to store elements and retrieve their minimum/maximum in constant time.
 * In order to do so, the invariants that are usually used in minimum or maximum heap are adapted.
 * For example, the `BinaryHeap` class (that you can find in another exercise) implements a minimum heap
 * by keeping the following invariant true: "For any node in the tree, every of its descendant has a value
 * greater or equal than it".
 * A similar invariant can be expressed for maximum heap.
 *
 * In a MinMaxHeap, the tree is decomposed into layers based on the depth of the node:
 *  - Every node in a depth which is even (0, 2, 4, ...) is a *min layer*
 *  - Every node in a depth which is odd (1, 3, 5, ...) is a *max layer*
 *
 * and the following invariants hold:
 *  - For every node in a min layer, every of its descendant has a value greater than it
 *  - For every node in a max layer, every of its descendant has a value lower than it
 *
 * In this exercise you need implement three methods:
 *  1) The min() method which return the minimum of the heap
 *  2) The max() method which return the maximum of the heap
 *  3) The swim() method which is used after a call to `insert` for ensuring the invariants
 *      of the MinMaxHeap are respected
 *
 * To help you for the swim function, a `getNodeDepth` function is provided which returns the depth of a node.
 */
public class MinMaxHeap<Key extends Comparable<Key>> {

    private Key[] content; // priority queue (PQ)
    private int size; // #elements in the PQ

    @SuppressWarnings("unchecked")
    public MinMaxHeap(int initialSize) {
        this.content = (Key []) new Comparable[initialSize]; // PQ as an array of Comparable Keys
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private void increaseSize() {
		// create new array with double size
        Key [] newContent = (Key []) new Comparable[this.content.length*2];

		// copy content of old array into new array
        System.arraycopy(this.content, 0, newContent, 0, this.content.length);
        this.content = newContent;
    }

    /**
     * Returns the size of this heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the minimum of the heap.
     * Expected time complexity: O(1)
     */
    public Key min() {
		if (isEmpty()) {
			return null;
		}

		// depth 0 is even, so it is a min layer
		// note: min/max is at index 1 (nothing at index 0)
		return content[1];
    }

    /**
     * Returns the maximum of the heap.
     * Expected time complexity: O(1)
     */
    public Key max() {
		// max is at depth 1

		// if only min in the binary heap or binary heap is empty
		// then max is the min (could be null if empty)
		if (this.size() <= 1) {
			return min();
		}

		// if 3 elements, cannot compare so min is the third one
		if (this.size() == 2) {
			return content[2];
		}

		Key maxCandidate1 = content[2];
		Key maxCandidate2 = content[3];

		if (higherThan(maxCandidate1, maxCandidate2)) {
			return maxCandidate1;
		}

		return maxCandidate2;
    }

    /**
     * Swaps the elements at index i and j in the
     * array representing the tree
     *
     * @param i the first index to swap
     * @param j the second index to swap
     */
    private void swap(int i, int j) {
        Key tmp = this.content[i];
        this.content[i] = this.content[j];
        this.content[j] = tmp;
    }

    /**
     * Returns true if the first key is less than the second key
     *
     * @param key The base key for comparison
     * @param comparedTo The key compared to
     */
    private boolean lessThan(Key key, Key comparedTo) {
        return key.compareTo(comparedTo) < 0;
    }

    /**
     * Returns true if the first key is greater than the second key
     *
     * @param key The base key for comparison
     * @param comparedTo The key compared to
     */
    private boolean higherThan(Key key, Key comparedTo) {
        return key.compareTo(comparedTo) > 0;
    }

    /**
     * Returns the depth of the node at a given position
     *
     * @param position The index in the `content` array for which the depth must be computed
     */
    private int getNodeDepth(int position) {
        // There is no log2 function in java.lang.Math so we use this little
        // formula to compute the log2 of K (which give, when rounded to its
        // integer value, the depth of the index)
        return (int) (log(position) / log(2));
    }

    /**
     * Swim a node in the tree
     *
     * @param position The position of the node to swim in the `content` array
     */
    public void swim(int position) {
		// base case: we have reached the root
		if (position == 1) {
			return;
		}

		Key grandParent = content[position / 4];
		Key parent = content[position / 2];

		Key current = content[position];

		boolean hasGrandParent = position / 4 != 0 && position / 4 != position / 2;


		// get the depth of the Tree
		int depth = getNodeDepth(position);

		// if we are on even depth (min layer),
		// parent is on a max layer => we swap position if parent's Key is lower than current's Key
		// grandparent (if exists) is on min layer => we swap position if grandparent's Key is greater than current's Key
		if (depth % 2 == 0) {
			if (lessThan(parent, current)) {
				swap(position, position / 2);
				position = position / 2;
				swim(position);
			} else if (hasGrandParent && higherThan(grandParent, current)) {
				swap(position, position / 4);
				position = position / 4;
				swim(position);
			}
		} else {
			// if we are on odd depth (max layer),
			// parent is on a min layer => we swap position if parent's Key is higher than current's Key
			// grandparent (if exists) is on max layer => we swap position if grandparent's Key is lower than current's Key
			if (higherThan(parent, current)) {
				swap(position, position / 2);
				position = position / 2;
				swim(position);
			} else if (hasGrandParent && lessThan(grandParent, current)) {
				swap(position, position / 4);
				position = position / 4;
				swim(position);
			}
		}
    }

    /**
     * Inserts a new value in the heap
     *
     * @param k the value to insert
     */
    public void insert(Key k) {
        this.size += 1;
        if (this.size >= this.content.length) {
            this.increaseSize();
        }

		// insert the new node at the end
        this.content[this.size] = k;
		// swim it up
        this.swim(this.size);
    }

	public boolean isEmpty() {
		return size() == 0;
	}
}
