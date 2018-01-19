import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

    // datastructures
    private final Map<String, ArrayList<Integer>> allNouns = new HashMap<>();
    private final Map<Integer, String> allKeys = new HashMap<>();
    private final Map<Integer, ArrayList<Integer>> edges = new HashMap<>(); // Edges between vertices
    private final SAP sap;
    private final Digraph digraph;
    private int graphSize = 0;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        loadSynsets(synsets);
        loadHypernyms(hypernyms);

        digraph = new Digraph(graphSize);
        // fill it up
        for (Map.Entry<Integer, ArrayList<Integer>> entry : edges.entrySet()) {
            for (Integer w : entry.getValue()) {
                this.digraph.addEdge(entry.getKey(), w);
            }
        }

        // check cycle
        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle()) {
            throw new IllegalArgumentException();
        }

        // Find all roots
        int roots = 0;
        for (int i =0; i < digraph.V(); i++) {
            if (!digraph.adj(i).iterator().hasNext()) roots++;
        }
        if (roots != 1) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.allNouns.keySet();
    }

    // is the word a WordNet noun? (aka is the word in list of nouns
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return this.allNouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return sap.length(allNouns.get(nounA), allNouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        int ancestor = sap.ancestor(allNouns.get(nounA), allNouns.get(nounB));
        return allKeys.get(ancestor);
    }

    // load synsets into two lists (one for lookup, one for index)
    private void loadSynsets(String synsets) {
        In in = new In(synsets);
        String line;

        ArrayList<Integer> currentNounsList;
        String currentSynsetNouns;

        while ((line = in.readLine()) != null) {
            if (line.equals("")) { continue; }

            String[] parts = line.split(",");
            int index = Integer.parseInt(parts[0]);

            for (String noun : parts[1].split(" ")) {
                // check if noun is already in allNouns
                if (this.allNouns.containsKey(noun)) {
                    currentNounsList = this.allNouns.get(noun);
                } else {
                    currentNounsList = new ArrayList<>();
                }

                currentNounsList.add(index);
                currentSynsetNouns = parts[1];

                this.allNouns.put(noun, currentNounsList);
                this.allKeys.put(index, currentSynsetNouns);
            }
            this.graphSize++;
        }
    }

    private void loadHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        String line;
        ArrayList<Integer> edgeList;
        while ((line = in.readLine()) != null) {
            if (line.equals("")) continue;
            String[] parts = line.split(",");
            Integer index = Integer.valueOf(parts[0]);
            // check if index exists
            if (edges.get(index) != null) {
                edgeList = edges.get(index);
            } else {
                edgeList = new ArrayList<>();
            }

            for (int i = 1; i < parts.length; i++) {
                edgeList.add(Integer.valueOf(parts[i]));
            }

            edges.put(index, edgeList);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println("power_plant is a noun? " + w.isNoun("power_plant"));
        System.out.println("abracadabra is too? " + w.isNoun("abracadabra"));
        System.out.println("azkaba is not? " + w.isNoun("azkaba"));

    }
}
