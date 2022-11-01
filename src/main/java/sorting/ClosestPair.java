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

		for (int v: a) {
			int elementToFind = v - x;
			int matching_element = binarySearchClosestElement(elementToFind, a, 0, a.length - 1);
		}

        return null;
    }

	public static int binarySearchClosestElement(int elementToFind, int [] arr, int lo, int hi) {
		int mid = (lo + hi) / 2;

		if (arr[mid] > elementToFind && lo != hi) {
			return binarySearchClosestElement(elementToFind, arr, 0, mid);
		} else if (arr[mid] < elementToFind && lo != hi) {
			return binarySearchClosestElement(elementToFind, arr, mid + 1, hi);
		} else {
			return arr[mid];
		}
	}
}
