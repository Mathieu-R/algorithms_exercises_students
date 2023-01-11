package graphs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

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
			return new LinkedList<>();
		}

		// create a queue for the BFS
		Queue<Integer> queue = new ArrayDeque<>();

		// rows
		int n = maze.length;
		// columns
		int m = maze[0].length;

		// keep track of visited vertices
		boolean[] marked = new boolean[n * m];

		// last vertex on the path to a given vertex
		// useful to generate the path tree
		int[] edgeTo = new int[n * m];

		/* explore the maze with BFS */

		// mark the starting point
		marked[ind(x1, y1, m)] = true;

		// add the starting point to the queue
		queue.add(ind(x1, y1, m));

		boolean found = false;

		while(!queue.isEmpty()) {
			// early stop: destination found
			if (found) {
				break;
			}

			// dequeue the least recently added cell (i.e. current cell)
			int cellIndex = queue.poll();
			// get its coordinate
			int cellX = row(cellIndex, m);
			int cellY = col(cellIndex, m);

			// explore each adjacent vertex
			// (4 possibilities max: up, down, left, right)
			for (int i = 0; i < 4; i++) {
				int moveX = ALLOWED_MOVES[i][0];
				int moveY = ALLOWED_MOVES[i][1];

				int adjCellX = cellX + moveX;
				int adjCellY = cellY + moveY;

				// base case: cannot go out of the maze
				if ((adjCellX <= 0 || adjCellX >= n) || (adjCellY <= 0 || adjCellY >= m)) {
					continue;
				}

				// base case: cannot move into a wall
				if (maze[adjCellX][adjCellY] == 1) {
					continue;
				}

				int adjCellIndex = ind(adjCellX, adjCellY, m);

				// if this adjacent cell has not been visited yet
				if (!marked[adjCellIndex]) {
					// mark this adjacent cell as visited
					marked[adjCellIndex] = true;
					// add this adjacent cell to the queue
					queue.add(adjCellIndex);
					// keep tree of path
					edgeTo[adjCellIndex] = cellIndex;

					// check if we have reached the destination
					if (adjCellX == x2 && adjCellY == y2) {
						found = true;
					}
				}
			}
		}

		// need now to return the path
		int startIndex = ind(x1, y1, m);
		int destIndex = ind(x2, y2, m);

		// iterator on Stack is FIFO (instead of LIFO, design error...)
		// we use LinkedList instead
		LinkedList<Integer> path = new LinkedList<>();

		// if destination has not been found => return empty path
		if (!found) {
			return path;
		}

		// add to the path stack from dest to start
		while(destIndex != startIndex) {
			// add at the head
			path.addFirst(destIndex);
			destIndex = edgeTo[destIndex];
		}

		// add the starting point
		path.addFirst(startIndex);
		return path;
    }

	// some helpers are given to us
    public static int ind(int x, int y, int mCols) {
        return x * mCols + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }
}
