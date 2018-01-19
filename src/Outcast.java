public class Outcast {

    private WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wn = wordnet;

    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDist = 0;
        for (String noun : nouns) {
            int distance = 0;
            for (String noun2 : nouns) {
                if (!noun.equals(noun2)) {
                    distance += wn.distance(noun, noun2);
                }
                if (distance > maxDist) {
                    maxDist = distance;
                    outcast = noun;
                }
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        Outcast out = new Outcast(new WordNet("synsets.txt", "hypernyms.txt"));
        String[] nouns = new String[]{"horse", "zebra", "cat", "bear", "table"};

        System.out.println(out.outcast(nouns));
    }
}
