package sorting;

import java.util.Arrays;

/**
 * In this task, you must implement the `push` operation on a binary heap data structure.
 * As a reminder, a heap is a tree data structure such that the following invariant is respected
 *
 *      For any node in the tree, the value associated with the node is higher (for a maxHeap) or lower
 *      (for a minHeap) than the value of its children.
 *
 * As a consequence, the minimum/maximum value is located at the root and can be retrieved in constant time.
 * In particular, this invariant must be respected after a push (or remove) operation.
 *
 * In this exercise the tree is represented with an array. The parent-child relation is implicitly represented
 * by the indices. A node at index i has its two children at index 2i and 2i+1.
 * For this is it assumed that the root is located at index 1 in the array.
 */
public class BinaryHeap {

    private int [] content;
    private int size;

    public BinaryHeap(int initialSize) {
        this.content = new int[initialSize];
        this.size = 0;
    }

    /**
     * Doubles the available size of this binary heap
     */
    private void increaseSize() {
        int [] newContent = new int[this.content.length*2];
        System.arraycopy(this.content, 0, newContent, 0, this.content.length);
        this.content = newContent;
    }

    /**
     * Add a new value in this heap
     * The expected time complexity is O(log(n)) with n the size of the binary heap
     * @param value the added value
     */
    public void push(int value) {
		// if binary heap is half full
		// double size of it
		if (this.size >= getContent().length / 2) {
			increaseSize();
		}

		// add value at the end of the array
		this.size += 1;
		getContent()[this.size] = value;

		// swim this value up
		swim(this.size);
    }

	private void swim(int k) {
		// while we have not reached the root
		// and parent key is tinier than child key
		while (k > 1 && greater(k/2, k)) {
			// exchange parent <-> child
			exch(k, k/2);
			// bubble up to parent
			k = k / 2;
		}
	}

	public boolean greater(int i, int j) {
		return getContent()[i] > getContent()[j];
	}

	public boolean less(int i, int j) {
		return getContent()[i] < getContent()[j];
	}

	public void exch(int i, int j) {
		int temp = getContent()[i];
		getContent()[i] = getContent()[j];
		getContent()[j] = temp;
	}

    /**
     * Returns the content of this heap
     */
    public int[] getContent() {
        return this.content;
    }

    /**
     * Returns the size of this heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the root of the tree representing this heap
     */
    public int getRoot() {
        return this.content[1];
    }
}
