package strings;

import java.util.PriorityQueue;

/**
 * This class is used to construct a Huffman trie from frequencies of letters (in unicode or ASCII).
 * As a reminder, in an Huffman trie nodes are weighted (see the `HuffmanNode` class) by
 * the frequencies of the character (if lead node) or the sum of the frequencies of its children
 * (if internal node).
 * For example, let us assume that we have the following letters with their associated frequencies:
 *  (t, 1), (m, 2), (z, 3), (a, 4), (g, 5)
 *
 *  The the following Huffman trie can be constructed
 *
 *
 *                      (_, 15)
 *                         |
 *         (_, 9) -------------------- (_, 6)
 *           |                           |
 *  (a, 4)------(g, 5)        (z, 3)----------(_, 3)
 *                                              |
 *                                     (t, 1)------(m, 2)
 *
 * In practice you are given an array of frequencies for each of the 256 ASCII code or 65536 unicode characters.
 * The goal is to construct the Huffman trie from this array of frequencies.
 */
public class Huffman {

    /**
     * Constructs an Huffman trie for the frequencies of the characters given in arguments.
     * The character are implicitly defined by the `freq` array (ranging from 0 to freq.length -1)
     *
     * @param freq the frequencies of the characters
     */
    public static HuffmanNode buildTrie(int [] freq) {
		// insert each character node in a (min) priority queue based on the frequency
		// they appear in the string we want to compress
		PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();

		// characters are implicitly defined by the freq array (0,...n-1)
		for (int i = 0; i < freq.length; i++) {
			// if char appear in the string
			HuffmanNode node = new HuffmanNode(i, freq[i], null, null);
			pq.add(node);
		}

		// repeat while pq has more than one element
		while(pq.size() > 1) {
			HuffmanNode left = pq.poll();
			HuffmanNode right = pq.poll();

			HuffmanNode parent = new HuffmanNode(-1, left.getFrequency() + right.getFrequency(), left, right);
			pq.add(parent);
		}

		// return the root
		return pq.poll();
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {

	// character
    private final int ch;
	// number of times the character appear in the string
    private final int freq;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(int ch, int freq, HuffmanNode left, HuffmanNode right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return this.left;
    }

    @SuppressWarnings("unchecked")
    public void setLeft(HuffmanNode node) {
        this.left = node;
    }

    public HuffmanNode getRight() {
        return this.right;
    }

    @SuppressWarnings("unchecked")
    public void setRight(HuffmanNode node) {
        this.right = node;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.freq - node.freq;
    }

    public int getFrequency() {
        return this.freq;
    }

    public int getChar() {
        return this.ch;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
