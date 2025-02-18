package sorting;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union
 * operation.
 * This operation will return the minimal array of sorted intervals covering
 * exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

	/**
	 * A class representing an interval with two integers. Hence the interval is
	 * [min, max].
	 */
	public static class Interval implements Comparable<Union.Interval> {

		public final int min;
		public final int max;

		public Interval(int min, int max) {
			assert (min <= max);
			this.min = min;
			this.max = max;
		}

		@Override
		public boolean equals(Object obj) {
			return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
		}

		@Override
		public String toString() {
			return "[" + min + "," + max + "]";
		}

		@Override
		public int compareTo(Union.Interval o) {
			if (min < o.min)
				return -1;
			else if (min == o.min)
				return max - o.max;
			else
				return 1;
		}
	}

	/**
	 * Returns the union of the intervals given in parameters.
	 * This is the minimal array of (sorted) intervals covering
	 * exactly the same points than the intervals in parameter.
	 * e.g. [ [7,9], [5,8], [2,4] ] => [ [2,4], [5,9] ]
	 * @param intervals the intervals to unite.
	 */
	public static Interval[] union(Interval[] intervals) {
		// creating the array containing the union of the intervals
		// as for now, we don't know its final size,
		// so we use an ArrayList
		ArrayList<Interval> outIntervals = new ArrayList<>();

		// sorting the intervals array
		// [ [7,9], [5,8], [2,4] ] => [ [2,4], [5,8], [7,9] ]
		Arrays.sort(intervals);

		// union of the intervals
		int min = intervals[0].min;
		int max = intervals[0].max;

		// [ [2,4], [5,9] ]
		for (int i = 0; i < intervals.length - 1; i++) {
			// if min/max overlaps => union
			if (intervals[i+1].min <= max) {
				max = Math.max(max, intervals[i+1].max);
				//min = Math.min(min, intervals[i+1].min);
			} else {
				// add the last interval
				Interval interval = new Interval(min, max);
				outIntervals.add(interval);

				// updating min and max values
				// for the new interval to construct
				min = intervals[i+1].min;
				max = intervals[i+1].max;
			}
		}

		Interval interval = new Interval(min, max);
		outIntervals.add(interval);

		return outIntervals.toArray(new Interval[0]);
	}


	public static void main(String[] args) {
		Interval[] intervals = new Interval[]{new Interval(7, 9), new Interval(5, 8), new Interval(2, 4)};
		Interval[] res = union(intervals);

		System.out.println(Arrays.toString(res));
	}
}
