package graphs;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,5,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Given a global water level, all positions in the matrix
 * with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3,
 * all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4),(5),(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is
 * a method that find a safe-path between
 * two positions (row,column) on the matrix.
 * The path assume you only make horizontal or vertical moves
 * but not diagonal moves.
 *
 * For a water level of 4, the shortest path
 * between (1,0) and (1,3) is
 * (1,0) -> (2,0) -> (2,1) -> (2,2) -> (2,3) -> (1,3)
 *
 *
 * Complete the code below so that the {@code  shortestPath} method
 * works as expected
 */
public class GlobalWarmingPaths {

	int waterLevel;
	int [][] altitude;

	int n, m;

	public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
		this.altitude = altitude;
		this.waterLevel = waterLevel;

		this.n = altitude.length;
		this.m = altitude[0].length;
	}


	/**
	 * Computes the shortest path between point p1 and p2
	 * @param p1 the starting point
	 * @param p2 the ending point
	 * @return the list of the points starting
	 *         from p1 and ending in p2 that corresponds
	 *         the shortest path.
	 *         If no such path, an empty list.
	 */
	public List<Point> shortestPath(Point p1, Point p2) {
		// base case: path is just one point
		if (p1.equals(p2)) {
			if (altitude[p1.x][p1.y] > waterLevel) {
				LinkedList<Point> path = new LinkedList<>();
				path.add(p1);
				return path;
			} else {
				return null;
			}
		}

		// since every cost are 1 => we use a simple BFS and not Dijkstra

		// keep track of visited node to avoid cycling
		boolean[][] marked = new boolean[n][m];

		// keep tree of path
		Point[][] edgeTo = new Point[n][m];

		// implicit graph
		final int[][] ALLOWED_MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

		// queue for BFS
		Queue<Point> queue = new ArrayDeque<>();

		// mark the starting point and add it to the queue
		marked[p1.x][p1.y] = true;
		queue.add(p1);

		boolean found = false;

		while(!queue.isEmpty()) {
			// base case: we have reached the destination
			if (found) {
				break;
			}

			// dequeue the next cell
			Point currentCellPoint = queue.poll();

			// move on the implicit graph (adjacent cells)
			for (int[] move: ALLOWED_MOVES) {
				int dx = move[0];
				int dy = move[1];

				int nextMoveX = currentCellPoint.x + dx;
				int nextMoveY = currentCellPoint.y + dy;

				// if we go out the map
				if ((nextMoveX < 0 || nextMoveX >= n) || (nextMoveY < 0 || nextMoveY >= m)) {
					continue;
				}

				// if we try to reach an unsafe area
				if (altitude[nextMoveX][nextMoveY] <= waterLevel) {
					continue;
				}

				Point nextMovePoint = new Point(nextMoveX, nextMoveY);

				// if the neighbor cell has not been visited yet,
				// visit it, mark it, keep edge to it in memory and add it to the queue
				if (!marked[nextMoveX][nextMoveY]) {
					marked[nextMoveX][nextMoveY] = true;
					edgeTo[nextMoveX][nextMoveY] = currentCellPoint;
					queue.add(nextMovePoint);

					// is it the destination point ?
					if (nextMovePoint.equals(p2)) {
						found = true;
					}
				}
			}
		}

		ArrayList<Point> path = new ArrayList<>();

		if (!found) {
			return path;
		}

		for (Point p = p2; !p.equals(p1); p = edgeTo[p.x][p.y]) {
			path.add(p);
		}

		path.add(p1);

		Collections.reverse(path);
		return path;
	}

	/**
	 * This class represent a point in a 2-dimension discrete plane.
	 * This is used to identify the cells of a grid
	 * with X = row, Y = column
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
