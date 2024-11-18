package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import graph.Graph;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    public Graph<String> getGraph(){
    	return this.graph;
    }
    // Representation invariant:
    // The graph should contain vertices as words and edges between words, with non-negative weights.
    // Safety from rep exposure:
    // The graph is encapsulated, and we do not expose internal structures to the outside.

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(corpus.toURI()));
        List<String> words = extractWords(lines);
        
        // Build the graph from the corpus
        for (int i = 0; i < words.size() - 1; i++) {
            String source = words.get(i);
            String target = words.get(i + 1);
            graph.set(source, target, graph.sources(target).getOrDefault(source, 0) + 1);
        }
    }

    /**
     * Extract words from the corpus, treating them as case-insensitive and split by spaces/newlines.
     */
    private List<String> extractWords(List<String> lines) {
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    words.add(token.toLowerCase());
                }
            }
        }
        return words;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] inputWords = input.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < inputWords.length - 1; i++) {
            String w1 = inputWords[i].toLowerCase();
            String w2 = inputWords[i + 1].toLowerCase();
            poem.append(inputWords[i]).append(" ");

            // Find a bridge word between w1 and w2 if possible
            String bridge = findBridgeWord(w1, w2);
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }

        // Add the last word of the input
        poem.append(inputWords[inputWords.length - 1]);
        return poem.toString();
    }

    /**
     * Find a bridge word between two words that maximizes the weight of w1 -> b -> w2 path.
     */
    private String findBridgeWord(String w1, String w2) {
        Map<String, Integer> targets = graph.targets(w1);
        Map<String, Integer> sources = graph.sources(w2);
        
        // Find a common word between the two, and pick the one with the maximum combined weight
        String bridge = null;
        int maxWeight = 0;
        
        for (String word : targets.keySet()) {
            if (sources.containsKey(word)) {
                int combinedWeight = targets.get(word) + sources.get(word);
                if (combinedWeight > maxWeight) {
                    maxWeight = combinedWeight;
                    bridge = word;
                }
            }
        }
        return bridge;
    }

    // Check representation invariants (used for debugging and ensuring correctness)
    private void checkRep() {
        // Ensure all edges have non-negative weights.
        for (String vertex : graph.vertices()) {
            for (int weight : graph.targets(vertex).values()) {
                assert weight >= 0;
            }
        }
    }
    
    @Override
    public String toString() {
        return "GraphPoet with graph: " + graph.toString();
    }
}
