/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("Graph should contain vertex 'A'.", graph.vertices().contains("A"));
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        boolean added = graph.add("A");
        assertFalse("Graph should not allow duplicate vertex 'A'.", added);
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.remove("A");
        assertFalse("Graph should not contain vertex 'A' after removal.", graph.vertices().contains("A"));
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        assertTrue("Graph should have 'A' as a source for 'B'.", graph.sources("B").containsKey("A"));
        assertEquals("Edge weight from A to B should be 5.", 5, (int) graph.sources("B").get("A"));
        
        assertTrue("Graph should have 'B' as a target for 'A'.", graph.targets("A").containsKey("B"));
        assertEquals("Edge weight from A to B should be 5.", 5, (int) graph.targets("A").get("B"));
    }
    
}
