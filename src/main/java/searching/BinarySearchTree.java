package searching;

/**
 * You are given a binary search tree (BST) whose nodes implement the BinaryNode and KeyNode interfaces
 * available in the utils package.
 *
 * We ask you to complete the ceil method, which take as argument the root of a BST and an integer `value`.
 * This method finds a node `N` in the BST such that
 *  - Its value is greater or equal than `value`
 *  - No nodes whose value is greater than `value` has a value lower than `N`
 *  and returns its value.
 *  If no such node exists, the method returns null.
 *
 *  For example, consider the following tree
 *
 *                              12
 *                              |
 *                 8 ------------------------ 18
 *                  |                          |
 *           3 ------------ 11       14 -------------- 20
 *                          |        |
 *                     9 ---         --- 15
 *
 * - The ceiled valued of 11 is 11
 * - The ceiled valued of 4 is 8
 * - The ceiled valued of 21 is null
 */
public class BinarySearchTree {

    /**
     * Returns the ceiled value of `value` in the tree rooted at `root`
     *
     * @param root the root of the tree
     * @param value the value we want to ceil
     */
    public static Integer ceil(BSTNode<Integer> root, int value) {
		if (root == null) {
			return null;
		}

		// if value is equals the root key
		// return the value
		if (root.key.compareTo(value) == 0) {
			return root.key;
		}

		// if value is greater than root key
		if (root.key.compareTo(value) < 0) {
			return ceil(root.right, value);
		}

		// if value is less than root key
		// we are sandwiching the value
		Integer t = ceil(root.left, value);
		if (t != null) {
			// exact match
			return t;
		} else {
			// ceil value
			return root.key;
		}
    }

	/**
	 * Returns the floored value of `value` in the tree rooted at `root`
	 *
	 * @param root the root of the tree
	 * @param value the value we want to floor
	 */
	public static Integer floor(BSTNode<Integer> root, int value) {
		if (root == null) {
			return null;
		}

		// if value is equals the root key
		// return the value
		if (root.key.compareTo(value) == 0) {
			return root.key;
		}

		// if value is less than root key
		if (root.key.compareTo(value) > 0) {
			return floor(root.left, value);
		}

		// if value is greater than root key
		// we are sandwiching the value
		Integer t = floor(root.right, value);
		if (t != null) {
			// exact match
			return t;
		} else {
			// floor value
			return root.key;
		}
	}

    static class BSTNode<K extends Comparable<K>> {

        private BSTNode<K> left;
        private BSTNode<K> right;
        private K key;
        private int size;

        public BSTNode(K key) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = 0;
        }

        public BSTNode(K key, int size) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.size = size;
        }

        public BSTNode<K> getLeft() {
            return this.left;
        }

        @SuppressWarnings("unchecked")
        public void setLeft(BSTNode<K> node) {
            this.left = node;
        }

        public BSTNode<K> getRight() {
            return this.right;
        }

        @SuppressWarnings("unchecked")
        public void setRight(BSTNode<K> node) {
            this.right = node;
        }

        public K getKey() {
            return this.key;
        }

        public void setKey(K newKey) {
            this.key = newKey;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int newSize) {
            this.size = newSize;
        }

        /**
         * Adds a new value in the subtree rooted a this node
         */
        public void add(K key) {
            if (key.compareTo(this.key) > 0) {
                if (this.right == null) {
                    this.right = new BSTNode<>(key);
                } else {
                    this.right.add(key);
                }
            } else {
                if (this.left == null) {
                    this.left = new BSTNode<>(key);
                } else {
                    this.left.add(key);
                }
            }
        }
    }

}

