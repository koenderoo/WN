import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {

    //datastructures
    private final Bag<Integer>[] adjHypernyms;
    private final Synset[] synsetList;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(hypernyms);
        String[] hyps = in.readAllLines();
        adjHypernyms = (Bag<Integer>[]) new Bag[hyps.length];
        // initialize Bag
        for (int i = 0; i < hyps.length; i++) { adjHypernyms[i] = new Bag<Integer>(); }
        // fill it up
        for (String h : hyps) {
            String[] parts = h.split(",");
            adjHypernyms[Integer.parseInt(parts[0])].add(Integer.parseInt(parts[1]));
        }

        in = new In(synsets);
        String[] syns = in.readAllLines();
        synsetList = new Synset[syns.length];
        for (String s : syns) {
            String[] parts = s.split(",");
            String[] nouns = parts[1].split(" ");
            synsetList[Integer.parseInt(parts[0])].nouns = nouns;
            synsetList[Integer.parseInt(parts[0])].gloss = parts[2];
        }

        System.out.print("debug break");
    }

    private class Synset {


        private String[] nouns;
        private String gloss;

        public Synset(String[] n, String g) {
            this.nouns = n;
            this.gloss = g;
        }

        public String[] getNouns() {
            return nouns;
        }

        public String getGloss() {
            return gloss;
        }

    }

    // returns all WordNet nouns
    //public Iterable<String> nouns()

    // is the word a WordNet noun? (aka is the word in list of nouns
    public boolean isNoun(String word) {return true;}

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) { return -1; }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) { return null; }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
    }
}
