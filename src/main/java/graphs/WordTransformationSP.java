package graphs;

import java.util.*;


/**
 * You are asked to implement the WordTransformationSP class which allows to find the shortest path
 * from a string A to another string B (with the certainty that there is a path from A to B).
 * To do this, we define a rotation(x, y) operation that reverses the order of the letters between the x and y positions (not included).
 * For example, with A=``HAMBURGER``, if we do rotation(A, 4, 8), we get HAMBEGRUR. So you can see that the URGE sub-string
 * has been inverted to EGRU and the rest of the string has remained unchanged: HAMB + ECRU + R = HAMBEGRUR.
 * Let's say that a rotation(x, y) has a cost of y-x. For example going from HAMBURGER to HAMBEGRUR costs 8-4 = 4.
 * The question is what is the minimum cost to reach a string B from A?
 * So you need to implement a public static int minimalCost(String A, String B)
 * function that returns the minimum cost to reach String B from A using the rotation operation.
 */
public class WordTransformationSP {

    /**
     * Rotate the substring between start and end of a given string s
     * e.g. s = HAMBURGER, rotation(s,4,8) = HAMBEGRUR i.e. HAMB + EGRU + R
     *
     * @param s
     * @param start
     * @param end
     * @return rotated string
     */
    public static String rotation(String s, int start, int end) {
        return s.substring(0, start) + new StringBuilder(s.substring(start, end)).reverse().toString() + s.substring(end);
    }

	/*
	Helper to sort the strings based on their distance (i.e. number of permutations from string "from" to string "to")
	 */
	static class Entry implements Comparable<Entry> {
		String value;
		int dist;

		Entry(String value, int dist) {
			this.value = value;
			this.dist = dist;
		}


		@Override
		public int compareTo(Entry o) {
			return this.dist - o.dist;
		}
	}

    /**
     * Compute the minimal cost from string "from" to string "to" representing the shortest path
     *
     * @param from
     * @param to
     * @return
     */
    public static int minimalCost(String from, String to) {
		// keep track of graph nodes and the distances to reach them
		// note: no array this time because we are dealing with string and not integers
		// (where we could use array index)
        HashMap<String, Integer> distTo = new HashMap<>();

		// PQ for Dijkstra
		PriorityQueue<Entry> PQ = new PriorityQueue<>();

		// add the source
		PQ.add(new Entry(from, 0));
		distTo.put(from, 0);

		while(!PQ.isEmpty()) {
			Entry next = PQ.poll();

			// check the (implicit) neighbors
			for (int i = 0; i <= next.value.length() - 2; i++) {
				for (int j = i + 2; j <= next.value.length(); j++) {
					// j-th letter is not included in the rotation: rotate positions [i, j[
					String neighbor = rotation(next.value, i, j);

					if (!distTo.containsKey(neighbor)) {
						// cost is the number of permutations
						distTo.put(neighbor, distTo.get(next.value) + (j - i));
						PQ.add(new Entry(neighbor, distTo.get(next.value) + (j - i)));
					}

					// edge relaxation
					if (distTo.get(neighbor) > distTo.get(next.value) + (j - i)) {
						distTo.put(neighbor, distTo.get(next.value) + (j - i));
						PQ.add(new Entry(neighbor, distTo.get(next.value) + (j - i)));
					}
				}
			}
		}

		return distTo.get(to);
    }
}
