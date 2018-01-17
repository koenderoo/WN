import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WordNet {

    //datastructures
    private final List<Bag<Integer>> adjHypernyms;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(hypernyms);


        // create empty Digraph
        adjHypernyms = new ArrayList<Bag<Integer>>();

        // fill it up
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            // only add when exists of 2 parts (last line is not)
            if (parts.length < 2) break;
            // add new Bag when not exist
            Integer index = Integer.parseInt(parts[0]);
            try {
                adjHypernyms.get( index ).add(Integer.parseInt(parts[1]));
            } catch ( IndexOutOfBoundsException e ) {
                adjHypernyms.add( index, new Bag<Integer>() );
                adjHypernyms.get( index ).add(Integer.parseInt(parts[1]));
            }
        }

        System.out.print("debug break");
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
