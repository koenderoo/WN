import edu.princeton.cs.algs4.*;

import java.util.*;

public class WordNet {

    //datastructures
    private final ST<String, Integer> allNouns;
    private final ST<Integer, String> allKeys;
    private SAP sap;
    private Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in = new In(hypernyms);
        // create empty Digraph
        String[] allHypernyms = in.readAllLines();
        digraph = new Digraph(allHypernyms.length);
        // fill it up
        for(String h: allHypernyms) {
            String[] parts = h.split(",");
            // only add when exists of 2 parts (last line is not)
            if (parts.length < 2) break;
            // add new Bag when not exist
            Integer v = Integer.parseInt(parts[0]);
            digraph.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

        }

        sap = new SAP(digraph);

        in = new In(synsets);
        // create list of nouns
        allNouns = new ST<String, Integer>();
        allKeys = new ST<>();
        // fill it up
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(",");
            // only add when exists of 2 parts (last line is not)
            if (parts.length < 2) break;

            allNouns.put(parts[1], Integer.parseInt(parts[0]));
            allKeys.put(Integer.parseInt(parts[0]), parts[1]);
        }
        // check cycle
        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle()) {
            throw new IllegalArgumentException();
        }

        // Find all roots
        SET<Integer> roots = new SET<Integer>();
        for (int i = 0; i < digraph.V(); i++) {
            DirectedDFS dfs = new DirectedDFS(digraph, i);
            if (dfs.count() == 1) {
                roots.add(i);
            }
        }
        if (roots.size() > 1) {
            throw new IllegalArgumentException();
        }
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
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        // get index of nouns
        Integer idA = allNouns.get(nounA);
        Integer idB = allNouns.get(nounB);

        Integer ancestor = sap.ancestor(idA, idB);
        return allKeys.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("power_plant is a noun? " + w.isNoun("power_plant"));
        System.out.println("abracadabra is not? " + w.isNoun("abracadabra"));

    }
}
