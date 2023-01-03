package graphs;

//feel free to import anything here


import java.util.PriorityQueue;

/**
 * You just bought yourself the latest game from the Majong™ studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1 dimensional cube blocks, aligned on a grid, forming vertical columns of cubes.
 * There are no holes in the columns, so the state of the map can be represented as a matrix M of size n⋅m
 * where the entry Mi,j is the height of the cube column located at i,j (0≤i<n, 0≤j<m).
 * You can't delete or add cubes, but you do have climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west), but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j) to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * Given the map of the mine, an initial position and an end position,
 * what is the minimum time needed to reach the end position from the initial position?
 */
public class MineClimbing {
	static final int[][] ALLOWED_MOVES = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
		// n rows
		int n = map.length;
		// m columns
		int m = map[0].length;

		// keep track of the shortest path distance to go to i,j cube
		// note: matrix is [row][column]
		// => x and y are inverted with respect to the usual convention (check the small tests)
		int[][] distTo = new int[n][m];

		// fill matrix of distances with infinite values
		// i.e. cubes are unreachable by default
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				distTo[i][j] = Integer.MAX_VALUE;
			}
		}

		distTo[startX][startY] = 0;

		/* BFS */

		// priority queue in order to visit the shortest paths first
		PriorityQueue<Cube> pq = new PriorityQueue<Cube>();

		// add the starting point
		pq.add(new Cube(startX, startY, 0));

		while(!pq.isEmpty()) {
			// get the next cube whose path is the shortest
			Cube nextCube = pq.poll();

			// optimization: early stop if we have reached the destination
			if (nextCube.x == endX && nextCube.y == endY) {
				break;
			}

			// optimization: if the distance to go to the next cube is lower than its weight, don't explore
			if (distTo[nextCube.x][nextCube.y] < nextCube.weight) {
				continue;
			}

			for (int i = 0; i < 4; i++) {
				int x = ALLOWED_MOVES[i][0];
				int y = ALLOWED_MOVES[i][1];

				// use modulo because the map is round
				// modulo (%) of negative numbers produce negative numbers
				// use Math.floorMod instead of %
				int neighborX = Math.floorMod(nextCube.x + x, n);
				int neighborY = Math.floorMod(nextCube.y + y, m);


				int cost = Math.abs(map[nextCube.x][nextCube.y] - map[neighborX][neighborY]);
				int newDist = distTo[nextCube.x][nextCube.y] + cost;

				// if shorter path found
				if (newDist < distTo[neighborX][neighborY]) {
					// update with the shorter path
					distTo[neighborX][neighborY] = newDist;

					// add neighbor cube to priority queue
					pq.add(new Cube(neighborX, neighborY, newDist));
				}
			}
		}

		return distTo[endX][endY];
    }

	private static class Cube implements Comparable<Cube> {
		private int x;
		private int y;
		private int weight;

		public Cube(int x, int y, int weight) {
			this.x = x;
			this.y = y;
			this.weight = weight;
		}

		@Override
		public int compareTo(Cube o) {
			return this.weight - o.weight;
		}
	}
}
