package graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * You are asked to implement the ConnectedComponent class given a graph.
 * The Graph class available in the code is the one of the Java class API.
 * <p>
 * public class ConnectedComponents {
 * <p>
 * public static int numberOfConnectedComponents(Graph g){
 * // TODO
 * return 0;
 * }
 * }
 * Hint: Feel free to add methods or even inner-class (private static class) to help you but don't change the class name or the signature of the numberOfConnectedComponents method.
 * Don't forget to add the imports at the beginning of your code if you use objects from the Java API.
 *
 *
 */
public class ConnectedComponents {
	private static boolean[] marked;
	// for each "index i", indicate to which component "index i" vertex belongs to
	private static int[] id;
	// keep track of the number of connected components
	private static int count;

    /**
     * @return the number of connected components in g
     */
    public static int numberOfConnectedComponents(Graph g) {
		// keep track of visited nodes
		marked = new boolean[g.V()];
		// keep track of delegate for each component
		id = new int[g.V()];
		// number of components
		count = 0;

		// run a DFS through every node to identify all the components
		// a non visited node correspond to a new component
		for (int v = 0; v < g.V(); v++) {
			if (!marked[v]) {
				dfs(g, v);
				count++;
			}
		}

		return count;
	}

	public static void dfs(Graph g, int v) {
		// mark node as visited
		marked[v] = true;

		// use the current number of component as the delegate
		// for the nodes that are part of the same component
		id[v] = count;

		// lookup every adjacent node (part of the component)
		for (int w: g.adj(v)) {
			if (!marked[w]) {
				dfs(g, w);
				// note: no need to keep track of path Tree
				// to look for connected components
			}
		}
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
