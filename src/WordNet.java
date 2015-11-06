import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;



public class WordNet {
    private final LinearProbingHashST<String, Bag<Integer>> nouns =
            new LinearProbingHashST<String, Bag<Integer>>();
    private final Digraph G;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) throw new NullPointerException();
        if (hypernyms == null) throw new NullPointerException();

        In in = new In(synsets);
        int V = 0;
        while (in.hasNextLine()) {
            V++;
            String line = in.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String noun = tokens[1];
            if (!nouns.contains(noun)) {
                nouns.put(noun, new Bag<Integer>());
            }
            Bag<Integer> bag = nouns.get(noun);
            bag.add(id);
        }

        in = new In(hypernyms);
        G = new Digraph(V);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int synsetId = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                int w = Integer.parseInt(tokens[i]);
                G.addEdge(synsetId, w);
            }
        }
        StdOut.println(G.V());
        StdOut.println(G.E());
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();

        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null) throw new NullPointerException();
        if (nounB == null) throw new NullPointerException();
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor
    // of nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null) throw new NullPointerException();
        if (nounB == null) throw new NullPointerException();
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet wordNet = new WordNet(synsets, hypernyms);
        StdOut.println(wordNet.isNoun("bird"));
    }
}
