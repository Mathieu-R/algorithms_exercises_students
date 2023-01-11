package sorting;

/**
 * Author Pierre Schaus
 *
 * We give you the API of a Vector class allowing to access,
 * modify and exchange two elements in constant time.
 * Your task is to implement a method to calculate the median of a Vector.
 *
 * public interface Vector {
 *     // size of the vector
 *     public int size();
 *     // set the value v to the index i of the vector
 *     public void set(int i, int v);
 *     // returns the value at index i of the vector
 *     public int get(int i);
 *     // exchanges the values at positions i and j
 *     public void swap(int i, int j);
 * }
 * You must implement a method that has the following signature:
 * public static int median(Vector a, int lo, int hi)
 *
 * This method calculates the median of vector "a" between the positions "lo" and "hi" (included).
 * You can consider that the vector "a" has always an odd size.
 * To help you, an IntelliJ project with a minimalist test to check if your code computes the right median value is given.
 * Indeed, it is not advised to debug your code via Inginious.
 * Warning It is not forbidden to modify or swap elements of the vector "a" during the calculation (with the get/set/swap methods).
 * It is forbidden to call other methods of the standard Java library. It is also forbidden to make a "new".
 *
 * The evaluation is based on 10 points:
 *  - good return value: 3 points,
 *  - good return value and complexity O(n log n): 5 points,
 *  - good return value and complexity O(n) expected (average case on a random uniform distribution): 10 points.
 *
 *  All the code you write in the text field will be substituted in the place indicated below.
 *  You are free to implement other methods to help you in this class, but the method "median" given above must at least be included.
 */
public class Median {

    public static class Vector {

        private int [] array;
        private int nOp = 0;


        public Vector(int n) {
            array = new int[n];
        }

        /**
         * Returns the size of the vector
         */
        public int size() {
            return array.length;
        }

        /**
         * Set the index in the vector to the given value
         *
         * @param i the index of the vector
         * @param v the value to set
         */
        public void set(int i, int v) {
            nOp++;
            array[i] = v;
        }

        /**
         * Returns the value at the given index
         *
         * @param i The index from which to retrieve the value
         */
        public int get(int i) {
            nOp++;
            return array[i];
        }

        /**
         * Exchanges elements in the array
         *
         * @param i the first index to swap
         * @param j the second index to swap
         */
        public void swap(int i, int j) {
            nOp++;
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        /**
         * Returns the number of operation that has been made
         */
        public int nOp() {
            return nOp;
        }
    }


    /**
     * Returns the median value of the vector between two indices
     *
     * @param vec the vector
     * @param lo the lowest index from which the median is computed
     * @param hi the highest index from which the median is computed
     */
    public static int median(Vector vec, int lo, int hi) {
        // we use quick-sort algorithm to sort our array fast: Theta(n log(n))
        int partitioningIndex = partition(vec, lo, hi);

        // check if the partitioning element is at the middle of the vector
        // if it is the case, we found the median because the partitioning element is
        // at its final position in the vector.
        // otherwise, we need to sort the left or right part of the vector.
		// NOTE: we always want the mid of the whole vector (not of a slice of it)
		// because we're looking for the median
		int mid = vec.size() / 2;

        if (partitioningIndex < mid) {
            // sort right part
            return median(vec, partitioningIndex + 1, hi);

        } else if (partitioningIndex > mid) {
            // sort left part
            return median(vec, lo, partitioningIndex - 1);
        } else {
			// the median has been sorted and is at the middle of the array
			return vec.get(partitioningIndex);
		}
    }

    /**
     * partition a vector into two parts based on a partitioning item (see quick-sort algorithm)
     * @param vec the vector
     * @param lo the lowest index of the vector
     * @param hi the highest index of the vector
     * @return the position of the partitioning item
     */
    private static int partition(Vector vec, int lo, int hi) {
        int i = lo;
        int j = hi + 1;

        // we arbitrarily choose the first element of vec as the partitioning item
        int partitioningItem = vec.get(lo);

        // swap elements until i and j cross
        while (true) {
			// increase "i" from "lo + 1"
			// until we find a greater number than the partitioning item
            while (vec.get(++i) < partitioningItem) {
                // stop if we reach the end of the list
                if (i == hi) {
                    break;
                }
            }

			// decrease "j" from "hi"
			// until we find a smaller number than the partitioning item
            while (vec.get(--j) > partitioningItem) {
                // stop if we reach the beginning of the list
                if (j == lo) {
                    break;
                }
            }

            // if i cross j, stop the infinite loop
            if (i >= j) {
                break;
            }

            // swap the two elements
            vec.swap(i, j);
        }

        // swap the partitioning item with the j-th element (i.e. where j pointer has stopped)
        vec.swap(lo, j);

        // return the position of the partitioning item
        return j;
    }
}
