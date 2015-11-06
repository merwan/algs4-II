package wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(G, w);
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            int distToV = vpath.distTo(i);
            int distToW = wpath.distTo(i);
            if (distToV < length && distToW < length) {
                length = distToV + distToW;
            }
        }
        if (length == Integer.MAX_VALUE) {
            length = -1;
        }

        return length;
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= G.V()) throw new IndexOutOfBoundsException();
    }

    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(G, w);
        int ancestor = -1;
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            int distToV = vpath.distTo(i);
            int distToW = wpath.distTo(i);
            if (distToV < length && distToW < length) {
                ancestor = i;
                length = distToV + distToW;
            }
        }

        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new NullPointerException();
        if (w == null) throw new NullPointerException();
        return 0;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if
    // no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null) throw new NullPointerException();
        if (w == null) throw new NullPointerException();
        return 0;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        StdOut.printf("Finished!");
    }
}
