

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) throw new NullPointerException();
        if (hypernyms == null) throw new NullPointerException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();
        return false;
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
    }
}
