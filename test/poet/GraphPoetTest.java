package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class GraphPoetTest {
    
    private GraphPoet poet;
    
    @Before
    public void setUp() throws IOException {
        // Setup a simple corpus for testing
        File corpus = new File("corpus.txt"); // Assume this file exists in the testing directory.
        poet = new GraphPoet(corpus);
    }

    
    // Test case for poem generation with bridge words
    @Test
    public void testPoemWithBridgeWord() {
        String input = "hello goodbye";
        String expectedPoem = "hello of goodbye"; // assuming "of" is the bridge word with max weight.
        assertEquals(expectedPoem, poet.poem(input));
    }
    
    // Test case for poem generation without a bridge word
    @Test
    public void testPoemWithoutBridgeWord() {
        String input = "hello world"; // Let's assume there's no bridge word between hello and world in the graph.
        String expectedPoem = "hello world";
        assertEquals(expectedPoem, poet.poem(input)); // No bridge word should be added.
    }

    // Test case for handling an empty input
    @Test
    public void testEmptyInput() {
        String input = "";
        String expectedPoem = "";
        assertEquals(expectedPoem, poet.poem(input)); // Empty input should return an empty string.
    }
    
    // Test case for handling input with only one word
    @Test
    public void testSingleWordInput() {
        String input = "hello";
        String expectedPoem = "hello";
        assertEquals(expectedPoem, poet.poem(input)); // A single word should return the same word.
    }
    
    // Test case for handling edge cases like non-existent words in the graph
    @Test
    public void testPoemWithNonExistentWord() {
        String input = "nonexistent word";
        String expectedPoem = "nonexistent word"; // If words are not in the graph, they should appear as-is.
        assertEquals(expectedPoem, poet.poem(input));
    }

    // Test case for handling a corpus that cannot be read
    @Test(expected = IOException.class)
    public void testInvalidCorpusFile() throws IOException {
        File invalidCorpus = new File("invalid_corpus.txt");
        new GraphPoet(invalidCorpus); // This should throw an IOException.
    }

    // Test case for checking that assertions are enabled
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Ensure assertions are enabled in the test environment.
    }
    
    // Additional edge test cases if necessary
    @Test
    public void testPoemWithMultipleBridgeWords() {
        // Test the case where multiple valid bridge words exist between the words in the input
        String input = "the quick brown fox jumps";
        String expectedPoem = "the quick of brown fox of jumps";
        assertEquals(expectedPoem, poet.poem(input)); // "of" might be the bridge word between both pairs.
    }

    // Further tests could focus on graph behavior, edge cases in parsing, etc.
}
