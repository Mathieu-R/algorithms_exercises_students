package graphs;

import java.util.LinkedList;
import java.util.Stack;

/**
 * We are interested in solving a maze represented by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array. An entry equal to '1' means that there is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover the shortest path between two coordinates on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical or horizontal (not diagonal), one step at a time.
 * The result of the path is an Iterable of coordinates from the origin to the destination.
 * These coordinates are represented by integers between 0 and n * m-1, where an integer 'a' represents the position x =a/m and y=a%m.
 * If the start or end position is a wall or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path between the origin and the destination.
 */
public class Maze {
	static final int[][] ALLOWED_MOVES = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        // base case: if starting or ending position is a wall => return empty path
		if (maze[x1][y1] == 1 || maze[x2][y2] == 1) {
			return new LinkedList<Integer>();
		}

		// create a "queue" (a LL in fact but will act as a Queue) for the BFS
		// easier to generate the path tree
		LinkedList<Integer> queue = new LinkedList<>();

		// rows
		int n = maze.length;
		// columns
		int m = maze[0].length;

		// keep track of visited vertices
		boolean[] marked = new boolean[n * m];

		// last vertex on the path to a given vertex
		// useful to generate the path tree
		int[] edgeTo = new int[n * m];

		// mark the start
		marked[ind(x1, y1, m)] = true;
		// add the starting index to the queue
		queue.add(ind(x1, y1, m));

		// let's run a BFS to explore the maze
		boolean found = false;

		while(!queue.isEmpty()) {
			// early stop: destination found
			if (found) {
				break;
			}

			// dequeue the first element index (i.e. current cell)
			int cellIndex = queue.remove();
			// get its coordinate
			int cellX = row(cellIndex, m);
			int cellY = col(cellIndex, m);

			// check each adjacent vertex (4 possibilities max)
			for (int i = 0; i < 4; i++) {
				int moveX = ALLOWED_MOVES[i][0];
				int moveY = ALLOWED_MOVES[i][1];

				int nextCellX = cellX + moveX;
				int nextCellY = cellY + moveY;

				// base case: check that we're not going out of the maze neither hitting a wall
				if ((nextCellX >= 0 && nextCellX < n) && (nextCellY >= 0 && nextCellY < m)
					&& maze[nextCellX][nextCellY] != 1) {
					int nextCellIndex = ind(nextCellX, nextCellY, m);

					// if vertex has not been visited before
					if (!marked[nextCellIndex]) {
						// mark it
						marked[nextCellIndex] = true;
						// add it to the queue
						queue.add(nextCellIndex);

						// keep Tree of path
						edgeTo[nextCellIndex] = cellIndex;

						// check if we reached to destination
						if (nextCellX == x2 && nextCellY == y2) {
							found = true;
						}
					}
				}
			}
		}

		// need now to return the path
		int startIndex = ind(x1, y1, m);
		int destIndex = ind(x2, y2, m);

		// if destination has not been found => return empty path
		if (!found) {
			return new LinkedList<Integer>();
		}

		Stack<Integer> path = new Stack<>();
		// add to the path stack from dest to start
		while(destIndex != startIndex) {
			path.push(destIndex);
			destIndex = edgeTo[destIndex];
		}

		// add the starting point
		path.push(destIndex);

		return path;
    }

	// some helpers are given to us
    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }
}
