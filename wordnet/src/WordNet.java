import java.util.Iterator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;



public class WordNet {
    private final LinearProbingHashST<String, Bag<Integer>> nounsSynsets =
            new LinearProbingHashST<String, Bag<Integer>>();
    private final LinearProbingHashST<Integer, String> synsetsNouns =
            new LinearProbingHashST<Integer, String>();
    private final Digraph G;
    private final SAP sap;

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
            String words = tokens[1];
            String[] nouns = words.split(" ");

            for (String noun : nouns) {
                if (!nounsSynsets.contains(noun)) {
                    nounsSynsets.put(noun, new Bag<Integer>());
                }
                Bag<Integer> bag = nounsSynsets.get(noun);
                bag.add(id);
            }

            synsetsNouns.put(id, words);
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
        int nbRoots = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) nbRoots++;
        }
        boolean hasCycle = new DirectedCycle(G).hasCycle();
        if (nbRoots > 1 || hasCycle) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsSynsets.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();

        return nounsSynsets.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkNounExists(nounA);
        checkNounExists(nounB);

        Iterable<Integer> v = nounsSynsets.get(nounA);
        Iterable<Integer> w = nounsSynsets.get(nounB);

        return sap.length(v, w);
    }

    private void checkNounExists(String noun) {
        if (noun == null) throw new NullPointerException();
        if (!isNoun(noun)) throw new IllegalArgumentException();
    }

    // a synset (second field of synsets.txt) that is the common ancestor
    // of nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkNounExists(nounA);
        checkNounExists(nounB);

        Iterable<Integer> v = nounsSynsets.get(nounA);
        Iterable<Integer> w = nounsSynsets.get(nounB);

        int ancestor = sap.ancestor(v, w);
        return synsetsNouns.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];
        WordNet wordNet = new WordNet(synsets, hypernyms);
        int count = 0;
        Iterator<String> it = wordNet.nouns().iterator();
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assert count == 119188;

        int distance = wordNet.distance("white_marlin", "mileage");
        assert distance == 23;

        distance = wordNet.distance("Black_Plague", "black_marlin");
        assert distance == 33;

        distance = wordNet.distance("worm", "bird");
        assert distance == 5;

        String sap = wordNet.sap("worm",  "bird");
        assert "animal animate_being beast brute creature fauna".equals(sap);

        sap = wordNet.sap("individual", "edible_fruit");
        assert "physical_entity".equals(sap);

        StdOut.println("Everything is OK!");
    }
}
