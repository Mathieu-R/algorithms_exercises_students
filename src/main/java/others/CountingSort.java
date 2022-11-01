package others;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Let arr be an array of 0s, 1s, 2s
 * Implement an algorithm to sort this array
 */
public class CountingSort {

	public static int[] sort(int[] arr) {
		int[] values = new int[]{0,0,0};
		ArrayList<Integer> sortedArr = new ArrayList<>();

		// O(n)
		for (int i = 0; i < arr.length; i++) {
			values[arr[i]] += 1;
		}

		// O(n)
		int j = 0;
		for (int i = 0; i < values.length; i++) {
			while (values[i] > 0) {
				arr[j] = i;

				values[i]--;
				j++;
			}
		}

		return arr;
	}


	public static void main(String[] args) {
		int[] arr = new int[]{0,1,2,1,1,0,2};
		int[] sortedArr = CountingSort.sort(arr);
		System.out.println(Arrays.toString(sortedArr));
	}
}

