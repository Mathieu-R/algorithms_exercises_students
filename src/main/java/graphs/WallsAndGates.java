package graphs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
You are given an m x n 2D grid initialized with these three possible values.

-1 - A wall or an obstacle.
0 - A gate.
INF - Infinity means an empty room.
We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.

Fill each empty room with the distance to its nearest gate.
If it is impossible to reach a Gate, that room should remain filled with INF
*/
public class WallsAndGates {
	/**
	 * @param rooms: m x n 2D grid
	 * @return rooms: rooms matrix where empty rooms are filled with the shortest distance to a gate.
	 */
	public static int[][] wallsAndGates(int[][] rooms) {
		// m rows
		int m = rooms.length;
		// n columns
		int n = rooms[0].length;

		int INF = 2147483647;

		Queue<Integer> queue = new ArrayDeque<>();

		boolean[] marked = new boolean[m * n];

		// no need of distance matrix because rooms act already like it
		// i.e. source (gate) are at distance 0 and empty room at distance INF
		//int[] distTo = new int[m, n];

		// enqueue all sources
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// if it is a gate, it is a source
				if (rooms[i][j] == 0) {
					queue.add(ind(i, j, n));
					marked[ind(i, j, n)] = true;
				}
			}
		}

		int[][] ALLOWED_MOVES = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};

		// BFS
		while(!queue.isEmpty()) {
			int nextCell = queue.poll();
			int nextCellX = row(nextCell, n);
			int nextCellY = col(nextCell, n);

			for (int[] move: ALLOWED_MOVES) {
				int moveX = move[0];
				int moveY = move[1];

				int neighborX = nextCellX + moveX;
				int neighborY = nextCellY + moveY;

				// if we go out of the grid
				if ((neighborX < 0 || neighborX >= n) || (neighborY < 0 || neighborY >= m)) {
					continue;
				}

				// if we reach a wall or an obstacle
				if (rooms[neighborX][neighborY] == -1) {
					continue;
				}

				if (marked[ind(neighborX, neighborY, n)]) {
					continue;
				}

				marked[ind(neighborX, neighborY, n)] = true;
				queue.add(ind(neighborX, neighborY, n));

				// compute the distance
				rooms[neighborX][neighborY] = rooms[nextCellX][nextCellY] + 1;
			}
		}

		return rooms;
	}

	public static int ind(int x, int y, int mCols) {
		return x * mCols + y;
	}

	public static int row(int pos, int mCols) {
		return pos / mCols;
	}

	public static int col(int pos, int mCols) {
		return pos % mCols;
	}

	public static void main(String[] args) {
		int[][] rooms = new int[][]{
			{2147483647,-1,0,2147483647},
			{2147483647,2147483647,2147483647,-1},
			{2147483647,-1,2147483647,-1},
			{0,-1,2147483647,2147483647}
		};

		// expected output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
		int[][] results = WallsAndGates.wallsAndGates(rooms);
		System.out.println(Arrays.deepToString(results));
	}
}
