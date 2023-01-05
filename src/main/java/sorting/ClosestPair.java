package sorting;

import java.util.Arrays;

/**
 * Let a be an array of integers. In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
		// O(n log(n))
		Arrays.sort(a);

		int lo = 0;
		int hi = a.length - 1;

		int[] pair = new int[]{a[lo], a[hi]};

		while (lo < hi) {
			// current candidate
			int sum = a[lo] + a[hi];

			if (sum < x) {
				// increase the low number index (next candidate will be greater)
				lo += 1;
			} else if (sum > x) {
				// decrease the high number index (next candidate will be lower)
				hi -= 1;
			} else {
				// if perfect match, update the pair return value and exit the loop
				pair[0] = a[lo];
				pair[1] = a[hi];
				break;
			}

			// if the next candidate is closer to x than the current candidate
			if (Math.abs(x - (a[lo] + a[hi])) < Math.abs(x - sum)) {
				// update the pair return value
				pair[0] = a[lo];
				pair[1] = a[hi];
			}
		}

		return pair;
    }
}
