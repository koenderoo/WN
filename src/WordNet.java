import edu.princeton.cs.algs4.In;

public class WordNet {

    //datastructures

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        readHypernums(hypernyms);
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);
        String[] lines = in.readAllLines();
        for( String l : lines) {
            String[] parts = l.split(",");
            Integer id = Integer.parseInt(parts[0]);
            String[] nouns = parts[1].split(" ");
            String gloss = parts[2];
            // todo add to some kind of list
        }

    }

    private void readHypernums(String hypernyms) {
        In in = new In(hypernyms);
        String[] lines = in.readAllLines();
        for(String l : lines) {
            String[] parts = l.split(",");
            Integer from = Integer.parseInt(parts[0]);
            Integer to = Integer.parseInt(parts[1]);
            // todo add to some kind of list
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns()

    // is the word a WordNet noun? (aka is the word in list of nouns
    public boolean isNoun(String word)

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)

    // do unit testing of this class
    public static void main(String[] args)
}
