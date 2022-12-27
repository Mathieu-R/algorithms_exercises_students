package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Consider this class, BreadthFirstShortestPaths, which computes the shortest path between
 * multiple node sources and any node in an undirected graph using a BFS path.
 * The Graph class is already implemented and here it is:
 * <p>
 * public class Graph {
 * // @return the number of vertices
 * public int V() { }
 * <p>
 * // @return the number of edges
 * public int E() { }
 * <p>
 * // Add edge v-w to this graph
 * public void addEdge(int v, int w) { }
 * <p>
 * // @return the vertices adjacent to v
 * public Iterable<Integer> adj(int v) { }
 * <p>
 * // @return a string representation
 * public String toString() { }
 * }
 * <p>
 * You are asked to implement all the TODOs of the BreadthFirstShortestPaths class.
 */
public class BreadthFirstShortestPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] distTo;     // distTo[v] = number of edges shortest s-v path

	private ArrayDeque<Integer> queue;

    /**
     * Computes the shortest path between any
     * one of the sources and very other vertex
     *
     * @param G       the graph
     * @param sources the source vertices
     */
    public BreadthFirstShortestPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];

		queue = new ArrayDeque<>();

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }

        bfs(G, sources);
    }

    // Breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        // to find shortest-path from multiple source vertices
		// we run a BFS but enqueue all source vertices first
		for (int s: sources) {
			// enqueue to source
			queue.add(s);
			// mark source as visited
			marked[s] = true;

			// indicate distance
			distTo[s] = 0;
		}

		while (!queue.isEmpty()) {
			// dequeue the oldest vertex
			int v = queue.pop();

			// for each unmarked vertex adjacent to "v"
			// enqueue them and mark them as visited
			for (int w: G.adj(v)) {
				if (!marked[w]) {
					queue.add(w);
					marked[w] = true;

					// indicate distance
					distTo[w] = distTo[v] + 1;
				}
			}
		}
    }

    /**
     * Is there a path between (at least one of) the sources and vertex v?
     *
     * @param v the vertex
     * @return true if there is a path, and false otherwise
     */
    public boolean hasPathTo(int v) {
        // there is path between one of the sources "s" and a vertex "v"
		// if this last one has been marked
		return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path
     * between one of the sources and vertex v?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes)
        {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }
}
