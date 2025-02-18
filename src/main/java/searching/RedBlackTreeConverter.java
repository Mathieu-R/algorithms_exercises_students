package searching;

/**
 * In this exercise, you'll need to implement a class that convert a 2-3 tree into a red black tree.
 * You'll receive as an input a 2-3 tree and you'll need to return the equivalent red black tree.
 * Each of the tree are represented using the node classes at the end of this file. Read them carefully to understand their structure.
 *
 * As an example, let us consider the following tree (2-nodes are represented as |key,_| and 3-nodes as |keyLeft,keyRight|):
 *
 *                                              |10,_|
 *                                                 |
 *                                        --------------------
 *                                        |                  |
 *                                     |3,7|              |12,15|
 *                                       |                  |
 *                                 ------------      ---------------
 *                                 |     |    |      |       |     |
 *                              |1,_| |5,_| |8,_|  |11,_| |13,_| |17,_|
 *
 *
 * The horizontal red-black tree representation (red link are horizontal not vertical) is the following:
 *
 *                                               10
 *                                               |
 *                                       ----------------
 *                                       |              |
 *                                  3----7        12----15   (red links !)
 *                                  |    |        |     |
 *                                ---- ----     ----  ----
 *                                |  | |  |     |  |  |  |
 *                                1  5    8    11 13     17
 *
 *  The convert method takes as input a TwoThreeNode representing the 2-node root |10,_| and should output the black node 10, which
 *  is a RBNode.
 */
public class RedBlackTreeConverter {

    /**
     * Converts a 2-3 tree in its equivalent RedBlack tree
     *
     * @param twoThreeNode the root of the 2-3 tree
     * @return a RBNode which is the root of the equivalent RedBlackTRee
     */
    public static<Key extends Comparable<Key>> RBNode<Key> convert(TwoThreeNode<Key> twoThreeNode) {
		// base case: we arrive at null link
		if (twoThreeNode == null) {
			return null;
		}

		if (twoThreeNode.is2node()) {
			RBNode<Key> leftChild = convert(twoThreeNode.leftChild);
			RBNode<Key> rightChild = convert(twoThreeNode.centerChild);

			RBNode<Key> currentNode = new RBNode<Key>(
				twoThreeNode.leftKey,
				twoThreeNode.leftValue,
				Color.Black,
				sizeEvenIfNull(leftChild) + sizeEvenIfNull(rightChild) + 1
			);

			currentNode.leftChild = leftChild;
			currentNode.rightChild = rightChild;

			return currentNode;
		} else {
			RBNode<Key> leftChild = convert(twoThreeNode.leftChild);
			RBNode<Key> centerChild = convert(twoThreeNode.centerChild);
			RBNode<Key> rightChild = convert(twoThreeNode.rightChild);

			RBNode<Key> redNode = new RBNode<Key>(
				twoThreeNode.leftKey,
				twoThreeNode.leftValue,
				Color.Red,
				sizeEvenIfNull(leftChild) + sizeEvenIfNull(centerChild) + 1
			);

			redNode.leftChild = leftChild;
			redNode.rightChild = centerChild;

			RBNode<Key> blackNode = new RBNode<Key>(
				twoThreeNode.rightKey,
				twoThreeNode.rightValue,
				Color.Black,
				redNode.size + sizeEvenIfNull(rightChild) + 1
			);

			blackNode.leftChild = redNode;
			blackNode.rightChild = rightChild;

			return blackNode;
		}
    }

    public static enum Color {
        Red,
        Black
    }

    /**
     * A class that represents a node in a RedBlack tree
     */
    public static class RBNode<Key extends Comparable<Key>> {

        public Key key;
        public int value;
        public RBNode<Key> leftChild;
        public RBNode<Key> rightChild;
        public final Color color;
        public int size;

        public RBNode(Key key, int value, Color color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }

        /**
         * Returns true if and only if the node is a red node. That is its incoming edge
         * from its parent is a red link.
         */
        public boolean isRed() {
            return this.color == Color.Red;
        }

        /**
         * Returns true if and only if the node is a black node.
         */
        public boolean isBlack() {
            return this.color == Color.Black;
        }
    }

    /**
     * A class that represent a node in a 2-3 tree
     */
    public static class TwoThreeNode<Key extends Comparable<Key>> {

        public Key leftKey;
        public Key rightKey;
        public Integer leftValue;
        public Integer rightValue;
        public TwoThreeNode<Key> leftChild;
        public TwoThreeNode<Key> centerChild;
        public TwoThreeNode<Key> rightChild;

        public TwoThreeNode(Key leftKey, Key rightKey, Integer leftValue, Integer rightValue) {
            this.leftKey = leftKey;
            this.rightKey = rightKey;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
        }


        /**
         * Returns true if and only if the node is a 2-node. A 2-node only has two children
         * that are located at `leftChild` (key smaller than `leftKey`) and `centerChild` (key
         * higher than `leftKey`
         */
        public boolean is2node() {
            return this.rightKey == null;
        }

        /**
         * Returns true if and only if the node is a 3-node. A 3-node is a 2-node with `rightKey`
         * not null and greater than `leftKey`. Moreover the keys in `centerChild` are smaller than
         * `rightKey`.
         * `rightChild` might be not null and the keys in that subtree are greater than `rightKey`
         */
        public boolean is3node() {
            return !this.is2node();
        }

        /**
         * Returns true if this node is a leaf
         */
        public boolean isLeaf() {
            return this.leftChild == null;
        }
    }

    /**
     * Returns the size of `node` and 0 if `node` is null
     */
    private static<Key extends Comparable<Key>> int sizeEvenIfNull(RBNode<Key> node) {
        return node == null ? 0 : node.size;
    }
}
