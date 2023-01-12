package graphs;

import java.util.*;

/**
 * Author: Pierre Schaus
 *
 * The first pictures from the James-Webb telescope revealed hyperspace by-passes
 * connecting certain galaxies (much like in the book “The Hitchhiker's Guide to the Galaxy” by Douglas Adams).
 * The telescope is also able to detect certain galaxies that contain habitable planets (called abusively habitable galaxies).
 * NASA asks you to plan an escape route to reach a habitable galaxy if ever the earth were to no longer be.
 * However, each by-pass observed between two galaxies is functional only for a specific period of time (expressed in years)
 * and it is no longer usable once this period has passed.
 * In order to save humanity, we ask you to design an optimal escape plan,
 * to a habitable galaxy using only functional by-passes.
 * As you will have understood, this problem can be modeled as a graph problem,
 * where each node is a galaxy and the directed links are the hyper spatial by-passes.
 * Given a starting galaxy (source) and a set of habitable galaxies,
 * is it possible to reach a viable galaxy (any of them) and
 * how long would it take at least knowing that each by-pass takes 1 year to be crossed ?
 */
public class GalaxyPath {
	/**
	 * Hint: Read the first small unit test with 5 galaxies for a small example and its expected solution.
	 *       By reading this example, you will make sure you understand the problem to be solved.
	 *
	 * @param graph an n x n matrix,
	 *              each entry i,j represent the expiration date (in years) of the by-passe from galaxy i to j.
	 *              an entry = 0 means that the by-pass does not exist (no link since the beginning).
	 *              an entry = i > 0 means that the by-pass can be used but before time i, because it will be closed at time i
	 *              Time passes by a year each time you use by-pass to travel.
	 *
	 * @param source a galaxy id on [0..n-1]
	 * @param destinations a set of habitable galaxies (ids taken on on [0..n-1])
	 * @return the shortest (number of years) feasible path in years from the source to a habitable destination galaxy.
	 *         More formally if P = (n0, n1, ...nk) is a valid path of duration k
	 *            iff n1 = source, nk = destination and
	 *                for all (ni, ni+1) in P, graph[ni][ni+1] >= i+1
	 *         -1 if no path exists
	 *
	 */
	public static int findPath(int [][] graph, int source, Set<Integer> destinations) {
		// keep track of visited nodes (i.e. galaxies)
		boolean[] marked = new boolean[graph.length];

		// keep track of time (in years) taken to go to node "i"
		int[] distTo = new int[graph.length];

		/* BFS (because cost = 1) */

		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(source);

		marked[source] = true;

		while(!queue.isEmpty()) {
			// go from point a
			int current = queue.poll();

			// optimization: early stop if we reached the destination
			if (destinations.contains(current)) {
				return distTo[current];
			}

			// visit all other nodes (i.e. all other galaxies)
			// (go to point b)
			for (int next = 0; next < graph.length; next++) {
				// if galaxy has not already been visited yet
				// AND if by-pass is still open
				if (!marked[next] && distTo[current] < graph[current][next]) {
					marked[next] = true;
					queue.add(next);

					distTo[next] = distTo[current] + 1;
				}
			}

		}

		return -1;
	}
}
