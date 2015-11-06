

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
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        Pair pair = findAncestorSingleSource(v, w);

        return pair.getLength();
    }

    private Pair findAncestorSingleSource(int v, int w) {
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(G, w);
        return findAncestor(vpath, wpath);
    }

    private Pair findAncestor(BreadthFirstDirectedPaths vpath,
            BreadthFirstDirectedPaths wpath) {
        Pair pair = new Pair(Integer.MAX_VALUE, -1);
        for (int i = 0; i < G.V(); i++) {
            int distToV = vpath.distTo(i);
            int distToW = wpath.distTo(i);
            if (distToV < Integer.MAX_VALUE && distToW < Integer.MAX_VALUE) {
                int totalLength = distToV + distToW;
                if (totalLength < pair.getLength()) {
                    pair = new Pair(totalLength, i);
                }
            }
        }
        if (pair.getLength() == Integer.MAX_VALUE) {
            pair = new Pair(-1, pair.getAncestor());
        }
        return pair;
    }

    private class Pair {
        private final int length;
        private final int ancestor;

        Pair(int length, int ancestor) {
            this.length = length;
            this.ancestor = ancestor;
        }

        public int getLength() {
            return length;
        }

        public int getAncestor() {
            return ancestor;
        }
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= G.V()) throw new IndexOutOfBoundsException();
    }

    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        Pair pair = findAncestorSingleSource(v, w);
        return pair.getAncestor();
    }

    private void checkVertexList(Iterable<Integer> v) {
        if (v == null) throw new NullPointerException();
        for (int i : v) {
            checkVertex(i);
        }
    }

    private Pair findAncestorMultipleSources(Iterable<Integer> v,
            Iterable<Integer> w) {
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(G, w);
        return findAncestor(vpath, wpath);
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertexList(v);
        checkVertexList(w);

        Pair pair = findAncestorMultipleSources(v, w);
        return pair.length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if
    // no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertexList(v);
        checkVertexList(w);

        Pair pair = findAncestorMultipleSources(v, w);
        return pair.ancestor;
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
