package sorting;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 *  Author: Pierre Schaus
 *
 *  Generic min priority queue implementation with a linked-data-structure
 *  The heap-tree is internally represented with Node's that store the keys
 *  Each node
 *
 *  - has a pointer its children and parent:
 *      - a null pointer indicates the absence of child/parent
 *  - a key (the values stored in the heap)
 *  - a size that is equal to the number of descendant nodes
 *
 *  A min heap is essentially an almost complete tree
 *  that satisfies the heap property:
 *  for any given node the key is less than the ones in the descendant nodes
 *  Here is an example of a heap:
 *
 *               3
 *           /      \
 *         5         4
 *        /  \      /  \
 *       6    7    8    5
 *      / \  / \  /
 *     7  8 8  9 9
 *
 *
 *
 *  The insert and min methods are already implemented maintaining the heap property
 *  Your task is to implement the delMin() method.
 *  This method should execute in O(log(n)) where n is the number of elements in the priority queue
 *
 *  You can add any method that you want but leave the instance variable
 *  and public API untouched since it used by the tests
 *
 * Hint: use the unit tests to debug your code, you might get some inspiration from the insert method
 *
 * @param <Key> the generic type of key on this priority queue
 */
public class MinPQLinked<Key> {

    // class used to implement the Nodes in the heap
    public class Node {
        public Key value;
        public Node left;
        public Node right;
        public Node parent;
        public int size;

        public Node(Node parent) {
            this.parent = parent;
            this.size = 1;
        }
    }

    public Node root; // number of items on priority queue
    public Comparator<Key> comparator;  // comparator used to compare the keys


    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param  comparator the order in which to compare the keys
     */
    public MinPQLinked(Comparator<Key> comparator) {
        this.comparator = comparator;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return root.size;
        }
    }

    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return root.value;
    }



    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the key to add to this priority queue
     */
    public void insert(Key x) {
        if (root == null) {
            root = new Node(null);
            root.value = x;
        } else {
            Node n = createNodeInLastLayer(); // create the new node in last layer
            n.value = x; // store x in this node
            swim(n); // restore the heap property from this leaf node to the root
        }
    }


    // maintains the heap invariant
    private void swim(Node n) {
        while (n != root && greater(n.parent, n)) {
            exch(n, n.parent);
            n = n.parent;
        }
    }

    // Creates a new empty node in last layer (ensuring it stay essentially an almost complete tree)
    // and returns the node
    private Node createNodeInLastLayer() {
        Node current = root;
        current.size++;
        while (current.left != null && current.right != null) {
            // both left and right are not null
            if (isPowerOfTwo(current.left.size+1) && current.right.size < current.left.size) {
                // left is complete and there is fewer in right subtree
                current = current.right; // follow right direction
            } else {
                current = current.left; // follow left direction
            }
            current.size++; // augment size since this node will have new descendant
        }
        // hook up the new node
        if (current.left == null) {
            current.left = new Node(current);
            return current.left;
        } else {
            assert (current.right == null);
            current.right = new Node(current);
            return current.right;
        }
    }


    /**
     * Removes and returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMin() {
		// base case: min heap is empty
		if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}

		// min is at the top of the Tree
		Key min = min();

		// base case: if only 1 element in the min heap
		if (size() == 1) {
			root = null;
		}

		// get the last node and remove it
		Key lastNode = getLastNodeAndRemoveIt();

		// exchange last node with the min (which is the root)
		// min is therefore deleted since we deleted the last node
		root.value = lastNode;

		// potentially root key > children key => sink the new root
		sink(root);
		return min;
    }

	private Key getLastNodeAndRemoveIt() {
		// get the height of the Tree
		int height = getTreeDepth();

		// get the number of nodes filled in the bottom row
		int nodesFilled = bottomRowFilled();
		// do "-1" because we want to remove the last node
		int nodesFilledMinusOne = nodesFilled - 1;


		Node current = root;
		// update current node size because we will remove the last node
		current.size -= 1;

		// convert the "number of nodes filled in the bottom row minus 1" to binary encoding
		// 0 => go left ; 1 => go right
		// ex: 5 => 101 => path: right, left, right
		for (int i = height - 2; i > 0; i--) {
			// i-th bit = 0 => go left
			if ((1 << i & nodesFilledMinusOne) == 0) {
				current = current.left;
			} else {
				current = current.right;
			}

			// update current node size because we will remove the last node
			current.size -= 1;
		}

		// we have reached the right spot
		Key key = current.value;
		current.value = null;

		return key;
	}

	private void sink(Node n) {
		if (n.left == null && n.right == null) {
			return;
		}

		Node minChild;

		if (n.left == null) {
			minChild = n.right;
		} else if (n.right == null) {
			minChild = n.left;
		} else {
			Node leftChild = n.left;
			Node rightChild = n.right;

			if (greater(leftChild, rightChild)) {
				minChild = rightChild;
			} else {
				minChild = leftChild;
			}
		}

		if (greater(n, minChild)) {
			exch(n, minChild);
			sink(minChild);
		}
	}

	private int getTreeDepth() {
		// height of the Tree: ceil( log_2 (n+1) )
		// "+1" because Tree is perfectly balanced except the last layer
		return (int) Math.ceil(log2(size() + 1));
	}

	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	// amount of space in the bottom row of a binary Tree
	private int bottomRowSpace(int height) {
		// 2^{h-1} = 1 << (h-1)
		return 1 << height - 1;
	}

	// amount of used nodes in the bottom row of a binary Tree
	private int bottomRowFilled() {
		// n - (2^{h-1} - 1)
		return size() - (bottomRowSpace(getTreeDepth()) - 1);
	}



    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/


    // check if x = 2^n for some x>0
    private boolean isPowerOfTwo(int x) {
        return (x & (x - 1)) == 0;
    }

    // Check if node i > j
    private boolean greater(Node i, Node j) {
        return comparator.compare(i.value, j.value) > 0;
    }

    // exchange the values in two nodes
    private void exch(Node i, Node j) {
        Key swap = i.value;
        i.value = j.value;
        j.value = swap;
    }


}
