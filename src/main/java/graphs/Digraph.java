package graphs;


import java.util.ArrayList;

/**
 *Implement the Digraph.java interface in the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {
	private int[] marked;
	private int[] edgeTo;
	// the book use an array of Bags, but it's more to underline
	// the fact that the order does not matter (because we use edgeTo)
	// for a DiGraph, we can use an ArrayList and then we do not have to use edgeTo
	private ArrayList<ArrayList<Integer>> adj;
	private int V = 0;
	private int E = 0;

    public Digraph(int V) {
		marked = new int[V];

		adj = new ArrayList<ArrayList<Integer>>(V);

		for (int v = 0; v < V; v++) {
			adj.add(v, new ArrayList<Integer>());
		}

		this.V = V;
    }

    /**
     * The number of vertices
     */
    public int V() {
        return V;
    }

    /**
     * The number of edges
     */
    public int E() {
        return E;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
		adj.get(v).add(w);
		E++;
		V++;
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        return adj.get(v);
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph dgCopy = new Digraph(V);
		for (int v = 0; v < V; v++) {
			for (int w : adj(v)) {
				dgCopy.addEdge(w, v);
			}
		}

		return dgCopy;
    }

}
