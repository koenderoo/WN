import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.*;

public class WordNet {

    //datastructures
    private final List<Bag<Integer>> adjHypernyms;
    private final ST<String, Integer> allNouns;
    private SAP sap;

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

        in = new In(synsets);
        // create list of nouns
        allNouns = new ST<String, Integer>();
        // fill it up
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            // only add when exists of 2 parts (last line is not)
            if (parts.length < 2) break;

            allNouns.put(parts[1], Integer.parseInt(parts[0]));
        }
//        System.out.println("debug break");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return allNouns.keys();
    }

    // is the word a WordNet noun? (aka is the word in list of nouns
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return allNouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        // get index of nouns
        Integer idA = allNouns.get(nounA);
        Integer idB = allNouns.get(nounB);

        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) { return null; }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("power_plant is a noun? " + w.isNoun("power_plant"));
        System.out.println("abracadabra is not? " + w.isNoun("abracadabra"));

    }
}
