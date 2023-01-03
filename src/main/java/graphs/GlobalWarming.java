package graphs;


/**
 * In this exercise, we revisit the GlobalWarming class from the sorting package.
 * You are still given a matrix of altitude in parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to, the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 *
 * If we replace the submerged entries by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implements two methods that can answer the following questions:
 *      1) Are two entries on the same island?
 *      2) How many island is there
 *
 * Two entries above the water level are connected if they are next to each other on
 * the same row or the same column. They are **not** connected **in diagonal**.
 * Beware that the methods must run in O(1) time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {
	// matrix
	private int[][] altitude;

	// dimension of matrix
	private int n;

	// number of islands
	private int nbIslands ;

	private int waterLevel;

	// keep track of components
	private int[] components;

	public final int[][] ALLOWED_MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * Constructor. The run time of this method is expected to be in
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
		this.altitude = altitude;
		this.n = altitude.length;

		this.nbIslands = 0;
		this.waterLevel = waterLevel;

		this.components = new int[n * n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// at the beginning, set every area to "flooded"
				// area flooded have their root set to -1 (means "no part of a component")
				this.components[ind(i, j, n)] = -1;
			}
		}

		// explore the matrix with dfs
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dfs(i, j);

				// update the number of islands.
				// if the area has its delegate set to "#number of islands",
				// it means it is part of an island that has been discovered in the dfs call
				// (remember every area have their delegate set to -1 at the beginning).
				// therefore, we need to update the counter.
				// note: cannot do that in dfs since it looks for adjacent area
				// and therefore detect a whole island.
				if (this.components[ind(i, j, n)] == this.nbIslands) {
					this.nbIslands += 1;
				}
			}
		}
    }

	/* 	detect and explore a (possible) island
		and set each explored and non-flooded area to its delegate island
	*/
	public void dfs(int i, int j) {
		// base case: outside the matrix
		if (i < 0 || i >= n || j < 0 || j >= n) {
			return;
		}

		// base case: area already visited
		// note: same idea as "marked[]" array
		if (this.components[ind(i, j, n)] != -1) {
			return;
		}

		// base case: area is flooded
		if (altitude[i][j] <= waterLevel) {
			return;
		}

		// we are on an island
		// note: we use the number of currently detected islands as the root (aka delegate)
		this.components[ind(i, j, n)] = this.nbIslands;

		// visits adjacent areas
		for (int[] move: ALLOWED_MOVES) {
			int x = move[0];
			int y = move[1];

			dfs(i + x, j + y);
		}
	}

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
		return this.nbIslands;
    }

    /**
     * Return true if p1 is on the same island than p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
		return (
			this.components[ind(p1.getX(), p1.getY(), n)] == this.components[ind(p2.getX(), p2.getY(), n)]
				&& this.components[ind(p1.getX(), p1.getY(), n)] != -1
				&& this.components[ind(p2.getX(), p2.getY(), n)] != -1
		);
    }

	// return the position in the matrix as an index for a flatted array
	public int ind(int i, int j, int n) {
		return (i * n) + j;
	}


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
